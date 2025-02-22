package mk.ukim.finki.wp.kol2022.g1.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
public class Employee {

    public Employee() {
    }

    public Employee(String name, String email, String password, EmployeeType type, List<Skill> skills, LocalDate employmentDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.skills = skills;
        this.employmentDate = employmentDate;
    }

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate employmentDate;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Skill> skills;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }
}
