package mk.ukim.finki.wp.jan2024g2.repository;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByTagsContains(Tag tag);
    List<Post> findAllByPostType(PostType type);
    List<Post> findAllByTagsContainsAndPostType(Tag tag, PostType type);
}
