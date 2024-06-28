package mk.ukim.finki.wp.jan2024g2.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
public class Post {

    public Post() {
    }

    public Post(String title, String content, LocalDate dateCreated, PostType postType, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.postType = postType;
        this.tags = tags;
        this.likes = 0;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    private LocalDate dateCreated;

    @Enumerated(EnumType.ORDINAL)
    private PostType postType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    private Integer likes = 0;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
