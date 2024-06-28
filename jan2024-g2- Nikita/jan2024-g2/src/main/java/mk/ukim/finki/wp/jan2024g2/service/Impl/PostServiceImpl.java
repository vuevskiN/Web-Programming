package mk.ukim.finki.wp.jan2024g2.service.Impl;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.model.Tag;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidPostIdException;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidTagIdException;
import mk.ukim.finki.wp.jan2024g2.repository.PostRepository;
import mk.ukim.finki.wp.jan2024g2.repository.TagRepository;
import mk.ukim.finki.wp.jan2024g2.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostServiceImpl(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Post> listAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
    }

    @Override
    public Post create(String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        List<Tag> tags1 = tagRepository.findAllById(tags);
        return postRepository.save(new Post(title,content,dateCreated,postType,tags1));
    }

    @Override
    public Post update(Long id, String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags) {
        List<Tag> tags1 = tagRepository.findAllById(tags);
        Post post = postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        post.setTitle(title);
        post.setContent(content);
        post.setDateCreated(dateCreated);
        post.setPostType(postType);
        post.setTags(tags1);
        return postRepository.save(post);
    }

    @Override
    public Post delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        postRepository.delete(post);
        return post;
    }

    @Override
    public Post like(Long id) {
        Post post = postRepository.findById(id).orElseThrow(InvalidPostIdException::new);
        post.setLikes(post.getLikes()+1);
        return postRepository.save(post);
    }

    @Override
    public List<Post> filterPosts(Long tagId, PostType postType) {
        if(tagId == null && postType == null)
        {
            return postRepository.findAll();
        }

        else if(tagId != null && postType != null){
            Tag tag = tagRepository.findById(tagId).orElseThrow(InvalidTagIdException::new);
            return postRepository.findAllByTagsContainsAndPostType(tag,postType);
        }

        else if(tagId==null){
            return postRepository.findAllByPostType(postType);
        }

        else{
            Tag tag = tagRepository.findById(tagId).orElseThrow(InvalidTagIdException::new);
            return postRepository.findAllByTagsContains(tag);
        }
    }
}
