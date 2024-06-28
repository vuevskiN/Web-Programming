package mk.ukim.finki.wp.jan2024g2.service;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidTagIdException;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidPostIdException;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    /**
     * @return List of all posts in the database
     */
    List<Post> listAll();

    /**
     * @param id The id of the post that we want to obtain
     * @return The post with the appropriate id
     * @throws InvalidPostIdException when there is no post with the given id
     */
    Post findById(Long id);

    /**
     * This method is used to create a new post, and save it in the database.
     *
     * @param title
     * @param content
     * @param dateCreated
     * @param postType
     * @param tags The list of tag ids which the post is tagged with
     * @return The post that is created. The id should be generated when the post is created.
     * @throws InvalidTagIdException when there is no tag with the given id
     */
    Post create(String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags);

    /**
     * This method is used to update a post, and save it in the database.
     *
     * @param id          The id of the post that is being updated
     * @param title
     * @param content
     * @param dateCreated
     * @param postType
     * @param tags
     * @return The post that is updated.
     * @throws InvalidPostIdException   when there is no post with the given id
     * @throws InvalidTagIdException when there is no tag with the given id
     */
    Post update(Long id, String title, String content, LocalDate dateCreated, PostType postType, List<Long> tags);

    /**
     * @param id
     * @return The post that is deleted.
     * @throws InvalidPostIdException when there is no post with the given id
     */
    Post delete(Long id);

    /**
     * This method should implement the like logic, i.e. enable liking a post.
     *
     * @param id
     * @return The post that is liked.
     * @throws InvalidPostIdException when there is no post with the given id
     */
    Post like(Long id);

    /**
     * This method should use repository implementation for the filtering using the appropriate parameters
     *
     * @param tagId The id of the tag. Used to filter the posts that contain a specific tag.
     *                      This param can be null, and is not used for filtering in such case.
     * @param postType         Used for filtering the posts by their postType.
     *                                This param can be null, and is not used for filtering in such case.
     * @return The post that meet the filtering criteria
     */
    List<Post> filterPosts(Long tagId, PostType postType);
}
