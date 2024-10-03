package com.example.autoHRM_backend.domain.calendar;

import com.example.autoHRM_backend.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Long> {

    WeeklySchedule findByDayOfWeekAndEmployee(DayOfWeek dayOfWeek, Employee employee);

}
