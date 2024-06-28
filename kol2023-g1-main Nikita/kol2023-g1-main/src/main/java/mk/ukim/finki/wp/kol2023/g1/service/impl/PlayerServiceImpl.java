package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.Team;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidTeamIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.repository.TeamRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1 = teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new);
        return playerRepository.save(new Player(name,bio,pointsPerGame,position,team1));
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1 = teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new);
        Player player = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        player.setName(name);
        player.setBio(bio);
        player.setPosition(position);
        player.setPointsPerGame(pointsPerGame);
        player.setTeam(team1);
        playerRepository.save(player);
        return player;
    }

    @Override
    public Player delete(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        playerRepository.delete(player);
        return player;
    }

    @Override
    public Player vote(Long id) {
        Player player = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        player.setVotes(player.getVotes()+1);
        playerRepository.save(player);
        return player;
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {
        if(pointsPerGame != null && position != null){
            return playerRepository.findAllByPointsPerGameLessThanAndPosition(pointsPerGame,position);
        }

        else if(pointsPerGame == null && position ==null){
            return playerRepository.findAll();
        }

        else if(pointsPerGame != null){
           return playerRepository.findAllByPointsPerGameLessThan(pointsPerGame);
        }

        else
            return playerRepository.findAllByPosition(position);
    }
}
