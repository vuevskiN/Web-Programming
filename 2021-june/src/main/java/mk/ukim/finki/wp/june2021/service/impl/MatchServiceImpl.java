package mk.ukim.finki.wp.june2021.service.impl;

import mk.ukim.finki.wp.june2021.model.Match;
import mk.ukim.finki.wp.june2021.model.MatchLocation;
import mk.ukim.finki.wp.june2021.model.MatchType;
import mk.ukim.finki.wp.june2021.model.exceptions.InvalidMatchIdException;
import mk.ukim.finki.wp.june2021.repository.MatchLocationRepository;
import mk.ukim.finki.wp.june2021.repository.MatchRepository;
import mk.ukim.finki.wp.june2021.service.MatchService;
import mk.ukim.finki.wp.june2021.model.MatchLocation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchLocationRepository matchLocationRepository;
    private final MatchRepository matchRepository;

    public MatchServiceImpl(MatchLocationRepository matchLocationRepository, MatchRepository matchRepository) {
        this.matchLocationRepository = matchLocationRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> listAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match findById(Long id) {
        return matchRepository.findById(id).orElseThrow(InvalidMatchIdException::new);
    }

    @Override
    public Match create(String name, String description, Double price, MatchType type, Long location) {
        MatchLocation matchLocation = matchLocationRepository.findById(location).orElseThrow(InvalidMatchIdException::new);
        return matchRepository.save(new Match(name,description,price,type,matchLocation));
    }

    @Override
    public Match update(Long id, String name, String description, Double price, MatchType type, Long location) {
        MatchLocation matchLocation = matchLocationRepository.findById(location).orElseThrow(InvalidMatchIdException::new);
        Match match = matchRepository.findById(id).orElseThrow(InvalidMatchIdException::new);
        match.setName(name);
        match.setDescription(description);
        match.setPrice(price);
        match.setType(type);
        match.setLocation(matchLocation);
        matchRepository.save(match);
        return match;
    }

    @Override
    public Match delete(Long id) {
        Match match = matchRepository.findById(id).orElseThrow(InvalidMatchIdException::new);
        matchRepository.delete(match);
        return match;
    }

    @Override
    public Match follow(Long id) {
        Match match = matchRepository.findById(id).orElseThrow(InvalidMatchIdException::new);
        match.setFollows(match.getFollows()+1);
        matchRepository.save(match);
        return match;
    }

    @Override
    public List<Match> listMatchesWithPriceLessThanAndType(Double price, MatchType type) {
        if(price != null && type != null){
           return matchRepository.findAllByPriceLessThanAndType(price,type);
        }
        else if(price == null && type == null){
            return matchRepository.findAll();
        }

        else if(price != null){
            return matchRepository.findAllByPriceLessThan(price);
        }

        else{
            return matchRepository.findAllByType(type);
        }
    }
}
