package com.example.autoHRM_backend.domain.allowance;

import com.example.autoHRM_backend.domain.commute.Commute;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class Allowance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="allowance_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "commute_id")
    private Commute commute;

    private Long time;

    protected Allowance(Commute commute, Long time) {
        this.commute = commute;
        this.time = time;
    }

}
