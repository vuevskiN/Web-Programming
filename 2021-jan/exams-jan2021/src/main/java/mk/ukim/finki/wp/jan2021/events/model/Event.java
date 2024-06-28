package mk.ukim.finki.wp.jan2021.events.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Event {

    public Event() {
    }

    public Event(String name, String description, Double price, EventType type, EventLocation location) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.likes = 0;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne
    private EventLocation location;

    private Integer likes = 0;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setLocation(EventLocation location) {
        this.location = location;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
