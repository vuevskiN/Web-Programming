package mk.ukim.finki.wp.kol2023.g1.repository;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    //listPlayersWithPointsLessThanAndPosition
    List<Player> findAllByPosition(PlayerPosition position);
    List<Player> findAllByPointsPerGameLessThan(double points);
    List<Player> findAllByPointsPerGameLessThanAndPosition( double points, PlayerPosition position);
}
