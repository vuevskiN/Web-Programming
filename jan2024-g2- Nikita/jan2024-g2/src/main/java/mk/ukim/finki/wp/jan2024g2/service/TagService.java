package mk.ukim.finki.wp.jan2024g2.service;

import mk.ukim.finki.wp.jan2024g2.model.Tag;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidTagIdException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TagService {

    /**
     * returns the tag with the given id
     *
     * @param id The id of the tag that we want to obtain
     * @return The tag with the appropriate id
     * @throws InvalidTagIdException when there is no tag with the given id
     */
    Tag findById(Long id);

    /**
     * @return List of all tags in the database
     */
    List<Tag> listAll();

    /**
     * This method is used to create a new tag, and save it in the database.
     *
     * @param name
     * @return The tag that is created. The id should be generated when the tag is created.
     */
    Tag create(String name);
}
