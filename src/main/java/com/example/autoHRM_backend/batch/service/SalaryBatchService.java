package com.example.autoHRM_backend.batch.service;

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceRepository;
import com.example.autoHRM_backend.domain.allowance.OverAllowance;
import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.CommuteBatchRepository;
import com.example.autoHRM_backend.domain.commute.DayOffCommute;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
import com.example.autoHRM_backend.domain.salary.Salary;
import com.example.autoHRM_backend.domain.salary.SalaryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Configuration
public class SalaryBatchService {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final AllowanceRepository allowanceRepository;
    private final CommuteBatchRepository commuteBatchRepository;
    private final BaseSalaryRepository baseSalaryRepository;
    private final SalaryRepository salaryRepository;

    public SalaryBatchService(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, CommuteBatchRepository commuteBatchRepository,
                              BaseSalaryRepository baseSalaryRepository, AllowanceRepository allowanceRepository, SalaryRepository salaryRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.allowanceRepository = allowanceRepository;
        this.commuteBatchRepository = commuteBatchRepository;
        this.baseSalaryRepository = baseSalaryRepository;
        this.salaryRepository = salaryRepository;
    }

    @Bean
    public Job SalaryJob() {

        System.out.println("SalaryBatchService.SalaryJob");

        return new JobBuilder("SalaryJob", jobRepository)
                .start(updateSalary())
                .build();
    }

    @Bean
    public Step updateSalary() {

        System.out.println("SalaryBatchService.updateSalary");

        return new StepBuilder("updateSalary", jobRepository)
                .<Commute, Salary>chunk(10, platformTransactionManager)
                .reader(salaryReader())
                .processor(updateSalaryProcessor())
                .writer(salaryWriter())
                .build();

    }

    // 월요일을 기준으로 저번주의 commute 기록들을 모두 가져옴.
    @Bean
    public RepositoryItemReader<Commute> salaryReader() {

        // 현재 시간을 기준으로 저번 주의 시작과 끝을 계산
//        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.of(2024, 10, 7, 5, 0);
        // 매주 저번주 월요일 오전 5시 부터임
        LocalDateTime lastWeekStart = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime lastWeekEnd = lastWeekStart.plusDays(6).with(LocalTime.MAX);
        System.out.println("SalaryBatchService.allowanceReader");
        System.out.println(now);
        System.out.println(lastWeekStart);
        System.out.println(lastWeekEnd);

        // 저번주의 근무기록을 사원별로 긁어옴.
        return new RepositoryItemReaderBuilder<Commute>()
                .name("commuteReader")
                .pageSize(10)
                .methodName("findCommutesLastWeek")
                .arguments(List.of(lastWeekStart, lastWeekEnd)) // PageRequest 추가
                .repository(commuteBatchRepository)
                .sorts(Map.of("employee.id", Sort.Direction.ASC, "id", Sort.Direction.ASC)) // 사원별로 정렬 후 시간순
                .build();

    }


    @Bean
    public ItemProcessor<Commute, Salary> updateSalaryProcessor() {
        return new ItemProcessor<Commute, Salary>() {
            private Long currentEmployeeId = null; // 현재 처리 중인 사원의 ID
            private long totalAllowancePay = 0;    // 현재 사원의 누적 Allowance Pay
            private boolean isFirstCommute = true;  // 첫 번째 Commute 여부 체크
            private int year = 0;                   // 첫 번째 Commute의 년도
            private int month = 0;                  // 첫 번째 Commute의 월

            @Override
            public Salary process(Commute commute) throws Exception {
                Long employeeId = commute.getEmployee().getId(); // 사원 ID

                // 사원이 변경된 경우
                if (!employeeId.equals(currentEmployeeId)) {
                    // 사원이 변경될 때 상태 초기화
                    currentEmployeeId = employeeId;
                    totalAllowancePay = 0; // 새로운 사원의 Allowance Pay 초기화
                    isFirstCommute = true;  // 첫 번째 Commute 플래그 초기화
                }

                // 첫 번째 Commute 처리
                if (isFirstCommute) {
                    year = commute.getStartTime().getYear(); // Commute의 년도 저장
                    month = commute.getStartTime().getMonthValue(); // Commute의 월 저장
                    isFirstCommute = false; // 첫 번째 Commute 처리 완료
                }

                // 현재 commute에 연결된 allowance 가져오기
                List<Allowance> allowances = allowanceRepository.findByCommute(commute);

                // allowancePay 합산
                for (Allowance allowance : allowances) {
                    totalAllowancePay += allowance.getAllowancePay();
                }

                // 첫 번째 Commute의 년과 월에 해당하는 Salary 가져오기
                Salary salary = salaryRepository.findByEmployeeAndYearAndMonth(
                        commute.getEmployee(),
                        year,
                        month
                );

                // Salary 업데이트
                if (salary != null && totalAllowancePay > 0) {
                    long updatedSalary = salary.getSalary() + totalAllowancePay;
                    salary.setSalary(updatedSalary);
                    return salary; // 업데이트된 Salary 객체 반환
                }

                return null; // 조건이 충족되지 않으면 null 반환
            }
        };
    }



    @Bean
    public RepositoryItemWriter<Salary> salaryWriter() {

        return new RepositoryItemWriterBuilder<Salary>()
                .repository(salaryRepository)
                .methodName("save")
                .build();
    }


}
