package mk.ukim.finki.wp.kol2022.g3.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
public class ForumUser {

    public ForumUser() {
    }

    public ForumUser(String name, String email, String password, ForumUserType type, List<Interest> interests, LocalDate birthday) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.interests = interests;
        this.birthday = birthday;
    }

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate birthday;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ForumUserType type;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Interest> interests;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(ForumUserType type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
