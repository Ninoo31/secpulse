package com.kubagent.secpulse.domain.entity;

import com.kubagent.secpulse.domain.enums.ScanStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scans")
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScanStatus status;

    @Column(name = "scanner_type", nullable = false)
    private String scannerType;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "risk_score")
    private Integer riskScore;

    protected Scan() {}

    public Scan(Project project, String scannerType) {
        this.project = project;
        this.scannerType = scannerType;
        this.status = ScanStatus.PENDING;
        this.startedAt = LocalDateTime.now();
    }
}
