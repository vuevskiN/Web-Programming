package mk.ukim.finki.wp.kol2021.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class MenuItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    public MenuItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public MenuItem() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
