package com.example.autoHRM_backend.batch.service;

// 휴일 근무 추가 수당 추가 로직

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.allowance.AllowanceRepository;
import com.example.autoHRM_backend.domain.allowance.OverAllowance;
import com.example.autoHRM_backend.domain.commute.Commute;
import com.example.autoHRM_backend.domain.commute.CommuteBatchRepository;
import com.example.autoHRM_backend.domain.commute.CommuteRepository;
import com.example.autoHRM_backend.domain.commute.DayOffCommute;
import com.example.autoHRM_backend.domain.employee.Employee;
import com.example.autoHRM_backend.domain.salary.BaseSalary;
import com.example.autoHRM_backend.domain.salary.BaseSalaryRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

// DayOff 계산해야함. -> 휴무일 근로는 연장근무로 생각하므로, 매주월요일 오전 5시 기준으로 저번주 일요일에 OverAllowance 를 하나씩 더 생성해준다.
// 휴무일에 근로를 하더라도 주에 40시간이 안넘어가면, 연장근무가 되지가 않음. 40시간이 넘어가면, DayOff의 시간을 OverAllowance를 생성해주어야 함.
// 내가 지금 코드를 잘못 씀. holiday 가 아니라 dayoff 를 생각해야함. 다음부터 다시 작성하기
@Configuration
public class AllowanceBatchService {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final AllowanceRepository allowanceRepository;
    // 휴무일의 overtime 은 고려하지 않음.
    private final double dayOffAddition = 1.5;
    private final CommuteBatchRepository commuteBatchRepository;
    private final BaseSalaryRepository baseSalaryRepository;

    public AllowanceBatchService(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager, AllowanceRepository allowanceRepository, CommuteBatchRepository commuteBatchRepository, BaseSalaryRepository baseSalaryRepository) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.allowanceRepository = allowanceRepository;
        this.commuteBatchRepository = commuteBatchRepository;
        this.baseSalaryRepository = baseSalaryRepository;
    }

    @Bean
    public Job DayOffAllowanceJob() {
        System.out.println("AllowanceBatchService.DayOffAllowanceJob");

        return new JobBuilder("DayOffAllowanceJob", jobRepository)
                .start(createDayOffAllowance())
                .build();
    }

    @Bean
    public Step createDayOffAllowance() {
        System.out.println("AllowanceBatchService.createDayOffAllowance");

        return new StepBuilder("createDayOffAllowance", jobRepository)
                .<Commute, Allowance> chunk(10, platformTransactionManager)
                .reader(commuteReader())
                .processor(createDayOffAllowanceProcessor())
                .writer(allowanceWriter())
                .build();

    }

    // 월요일을 기준으로 저번주의 commute 기록들을 모두 가져옴.
    @Bean
    public RepositoryItemReader<Commute> commuteReader() {

        // 현재 시간을 기준으로 저번 주의 시작과 끝을 계산
//        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now = LocalDateTime.of(2024, 9, 23, 5, 0);
        // 매주 저번주 월요일 오전 5시 부터임
        LocalDateTime lastWeekStart = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime lastWeekEnd = lastWeekStart.plusDays(6).with(LocalTime.MAX);
        System.out.println("AllowanceBatchService.commuteReader");
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
    public ItemProcessor<Commute, Allowance> createDayOffAllowanceProcessor() {

        return new ItemProcessor<Commute, Allowance>() {

            private Long currentEmployeeId = null; // 현재 처리 중인 사원의 ID
            private long totalCommuteTime = 0;     // 현재 사원의 누적 Commute 시간
            private long totalDayOffTime = 0;      // DayOff 시간
            private Commute lastSundayCommute = null; // 마지막 Commute (저번 주 일요일)
            private Commute lastDayOffCommute = null; // 마지막 DayOffCommute
            private boolean hasDayOffCommute = false; // DayOffCommute 여부 체크

            @Override
            public Allowance process(Commute commute) throws Exception {
                Long employeeId = commute.getEmployee().getId(); // 사원 ID

                // 출퇴근 시간이 저번주 일요일인지 확인
                LocalDate commuteDate = commute.getStartTime().toLocalDate();
                DayOfWeek dayOfWeek = commuteDate.getDayOfWeek();

                System.out.println("AllowanceBatchService.process");
                System.out.println(commuteDate);

                // 사원이 변경된 경우
                if (!employeeId.equals(currentEmployeeId)) {
                    // 사원이 변경될 때 상태 초기화
                    currentEmployeeId = employeeId;
                    totalCommuteTime = commute.getTime(); // 현재 사원의 첫 Commute 시간
                    totalDayOffTime = 0; // 새로운 사원의 DayOff 시간 초기화
                    hasDayOffCommute = false; // 새로운 사원의 DayOffCommute 여부 초기화
                    lastSundayCommute = (dayOfWeek == DayOfWeek.SUNDAY) ? commute : null;
                    lastDayOffCommute = null;
                } else {
                    // 같은 사원의 경우
                    if (dayOfWeek == DayOfWeek.SUNDAY) {
                        lastSundayCommute = commute;
                    }

                    // DayOffCommute 확인 및 시간 누적
                    if (commute instanceof DayOffCommute) {
                        hasDayOffCommute = true;
                        lastDayOffCommute = commute;
                        totalDayOffTime += commute.getTime();
                        System.out.println(totalDayOffTime);
                    } else {
                        // 일반 Commute 시간 누적
                        totalCommuteTime += commute.getTime();
                    }
                }
                if (totalCommuteTime > 40 * 60 && hasDayOffCommute && lastSundayCommute != null) {
                    // 휴무일 근로 수당 생성
                    System.out.println("휴무일 근로 수당 생성됌");
                    return createDayOffAllowance(lastDayOffCommute, totalCommuteTime, totalDayOffTime);
                }

                return null; // Allowance 생성 조건이 충족되지 않으면 null 반환
            }

            // 추가 수당(OverAllowance) 생성 로직
            private Allowance createDayOffAllowance(Commute dayOffCommute, long totalTime, long dayOffTime) {
                Employee employee = dayOffCommute.getEmployee();
                BaseSalary baseSalary = baseSalaryRepository.findByEmployee(employee);
                Long minuteWage = baseSalary.getMinuteWage();
                long overTime = 0;

                System.out.println("AllowanceBatchService.createDayOffAllowance");

                // 40시간을 초과했을 경우 초과 시간을 계산
                if (totalTime > 40 * 60) {
                    overTime = dayOffTime;
                } else {
                    overTime = totalTime + dayOffTime - 40 * 60;
                }

                // 휴무일에 대한 시간이 overTime 임 .
                Long allowancePay = calculateDayOffAllowance(overTime, minuteWage);

                // Allowance 생성 및 반환
                return OverAllowance.builder()
                        .commute(dayOffCommute) // 저번주 dayOffCommute 에 대해서 생성
                        .time(overTime)
                        .allowancePay(allowancePay)
                        .build();
            }
        };
    }

    @Bean
    public RepositoryItemWriter<Allowance> allowanceWriter() {

        return new RepositoryItemWriterBuilder<Allowance>()
                .repository(allowanceRepository)
                .methodName("save")
                .build();
    }

    public Long calculateDayOffAllowance(Long time, Long minuteWage){

        long allowancePay = (long)((time/10)*minuteWage*dayOffAddition);

        return allowancePay;
    }


}