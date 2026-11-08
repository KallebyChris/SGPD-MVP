package br.edu.sgpd.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class CollaboratorForm {
    @NotBlank @Size(max = 120) private String name;
    @NotBlank @Email @Size(max = 180) private String email;
    @Size(max = 30) private String phone;
    @NotBlank @Size(max = 120) private String jobTitle;
    @NotNull private LocalDate hireDate;
    private boolean active = true;
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    public String getEmail() { return email; }
    public void setEmail(String value) { email = value; }
    public String getPhone() { return phone; }
    public void setPhone(String value) { phone = value; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String value) { jobTitle = value; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate value) { hireDate = value; }
    public boolean isActive() { return active; }
    public void setActive(boolean value) { active = value; }
}
