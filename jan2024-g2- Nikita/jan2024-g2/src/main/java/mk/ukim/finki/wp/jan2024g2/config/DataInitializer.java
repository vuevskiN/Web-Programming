package mk.ukim.finki.wp.jan2024g2.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.service.PostService;
import mk.ukim.finki.wp.jan2024g2.service.TagService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataInitializer {

    private final TagService tagService;
    private final PostService postService;

    public DataInitializer(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    private PostType randomize(int i) {
        if (i % 3 == 0) return PostType.ARTICLE;
        if (i % 3 == 1) return PostType.PAPER;
        return PostType.POSTER;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.tagService.create("Tag: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.postService.create("Post: " + i, "Post content: " + i, LocalDate.now().minusDays(i), this.randomize(i),
                    Arrays.asList(
                            this.tagService.listAll().get((i - 1) % 5).getId(),
                            this.tagService.listAll().get((i) % 5).getId()
                    )
            );
        }
    }
}
