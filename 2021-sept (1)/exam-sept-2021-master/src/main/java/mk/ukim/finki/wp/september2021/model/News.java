package mk.ukim.finki.wp.september2021.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class News {

    public News() {
    }

    public News(String name, String description, Double price, NewsType type, NewsCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.category = category;
        this.likes = 0;
    }
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private NewsType type;

    @ManyToOne
    private NewsCategory category;

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

    public void setType(NewsType type) {
        this.type = type;
    }

    public void setCategory(NewsCategory location) {
        this.category = location;
    }

    public void setLikes(Integer follows) {
        this.likes = follows;
    }
}