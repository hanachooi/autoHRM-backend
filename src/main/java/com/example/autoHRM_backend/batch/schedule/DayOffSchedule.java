package com.example.autoHRM_backend.batch.schedule;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DayOffSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public DayOffSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    // 매주 월요일 오전 5시
    @Scheduled(cron = "0 0 5 * * MON", zone = "Asia/Seoul")
    public void runDayOffAllowanceJob() throws Exception {

        System.out.println("Scheduler -----------------------------");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("DayOffAllowanceJob"), jobParameters);
    }
}
