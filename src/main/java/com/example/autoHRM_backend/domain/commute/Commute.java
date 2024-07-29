package com.example.autoHRM_backend.domain.commute;

import com.example.autoHRM_backend.domain.allowance.Allowance;
import com.example.autoHRM_backend.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Commute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commute_id")
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "commute")
    private List<Allowance> allowance;

    protected Commute(LocalDateTime startTime, Employee employee) {
        this.startTime = startTime;
        this.employee = employee;
    }

    public void checkOut() {
        if (endTime != null) {
            throw new IllegalArgumentException("이미 퇴근처리가 되었습니다");
        }
        //this.endTime = LocalDateTime.now();
        this.endTime = LocalDateTime.of(2024, 7, 29, 6, 0);
    }

    public abstract void setTime(Long overtime, Long nighttime, Long time);

    public abstract Long getNightTime();

    public abstract Long getOverTime();

    public abstract Long getTime();
}
