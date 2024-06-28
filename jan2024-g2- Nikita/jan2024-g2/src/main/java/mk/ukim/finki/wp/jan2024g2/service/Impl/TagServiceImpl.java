package mk.ukim.finki.wp.jan2024g2.service.Impl;


import mk.ukim.finki.wp.jan2024g2.model.Tag;
import mk.ukim.finki.wp.jan2024g2.model.exceptions.InvalidTagIdException;
import mk.ukim.finki.wp.jan2024g2.repository.TagRepository;
import mk.ukim.finki.wp.jan2024g2.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(InvalidTagIdException::new);
    }

    @Override
    public List<Tag> listAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag create(String name) {
        return tagRepository.save(new Tag(name));
    }
}
