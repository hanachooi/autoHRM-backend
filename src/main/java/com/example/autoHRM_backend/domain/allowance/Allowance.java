package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@DiscriminatorColumn
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class Allowance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="allowance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commute_id")
    private Commute commute;

    private Long time;

    private Long allowancePay;

    protected Allowance(Commute commute, Long time, Long allowancePay) {
        this.commute = commute;
        this.time = time;
        this.allowancePay = allowancePay;
    }

}
