package com.kubagent.secpulse.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name= "repository_url", nullable = false)
    private String repositoryUrl;

    @Column(nullable = false)
    private String branch;

    @Column(name= "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Project() {}

    public Project(String name, String repositoryUrl, String branch) {
        this.name = name;
        this.repositoryUrl = repositoryUrl;
        this.branch = branch;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public String getBranch() {
        return branch;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
