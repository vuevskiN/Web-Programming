package mk.ukim.finki.wp.june2021.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Match {

    public Match() {
    }

    public Match(String name, String description, Double price, MatchType type, MatchLocation location) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.follows = 0;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private MatchType type;

    @ManyToOne
    private MatchLocation location;

    private Integer follows = 0;

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

    public void setType(MatchType type) {
        this.type = type;
    }

    public void setLocation(MatchLocation location) {
        this.location = location;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }
}
