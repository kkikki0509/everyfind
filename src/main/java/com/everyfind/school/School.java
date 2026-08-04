package com.everyfind.school;

import jakarta.persistence.*;

@Entity
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long id;

    @Column(name = "school_name")
    private String name;

    @Column(name = "email_domain")
    private String domain;

    protected School(){}
    protected School(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
