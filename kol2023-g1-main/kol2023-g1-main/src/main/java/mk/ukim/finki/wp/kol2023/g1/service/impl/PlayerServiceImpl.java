package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.Team;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import mk.ukim.finki.wp.kol2023.g1.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public List<Player> listAllPlayers() {
        return this.playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return this.playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1=this.teamService.findById(team);
        Player player=new Player(name,bio,pointsPerGame,position,team1);
        return  this.playerRepository.save(player);
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1=this.teamService.findById(team);
        Player player=findById(id);
        player.setName(name);
        player.setBio(bio);
        player.setPointsPerGame(pointsPerGame);
        player.setPosition(position);
        player.setTeam(team1);
        return this.playerRepository.save(player);
    }

    @Override
    public Player delete(Long id) {
        Player player=findById(id);
        this.playerRepository.delete(player);
        return player;
    }

    @Override
    public Player vote(Long id) {
        Player player=findById(id);
        player.setVotes(player.getVotes()+1);
        return this.playerRepository.save(player);
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {
        if(pointsPerGame==null&& position==null){
            return listAllPlayers();
        }else if(pointsPerGame!=null && position!=null){
            return this.playerRepository.findByPointsPerGameLessThanAndPosition(pointsPerGame,position);
        }else if(pointsPerGame!=null){
            return this.playerRepository.findByPointsPerGameLessThan(pointsPerGame);
        }else {
            return this.playerRepository.findByPosition(position);
        }
    }
}