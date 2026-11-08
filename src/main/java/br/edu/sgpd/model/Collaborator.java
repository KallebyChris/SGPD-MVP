package br.edu.sgpd.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "collaborator")
public class Collaborator {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String name;
    @Column(nullable = false, unique = true, length = 180)
    private String email;
    @Column(length = 30)
    private String phone;
    @Column(name = "job_title", nullable = false, length = 120)
    private String jobTitle;
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    @Column(nullable = false)
    private boolean active = true;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private AppUser createdBy;

    protected Collaborator() {}
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getJobTitle() { return jobTitle; }
    public LocalDate getHireDate() { return hireDate; }
    public boolean isActive() { return active; }

    public void update(String name, String email, String phone, String jobTitle, LocalDate hireDate, boolean active) {
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.phone = phone == null || phone.isBlank() ? null : phone.trim();
        this.jobTitle = jobTitle.trim();
        this.hireDate = hireDate;
        this.active = active;
    }
    public static Collaborator create(String name, String email, String phone, String jobTitle,
            LocalDate hireDate, boolean active, AppUser creator) {
        Collaborator item = new Collaborator();
        item.update(name, email, phone, jobTitle, hireDate, active);
        item.createdBy = creator;
        return item;
    }
}
