package mk.ukim.finki.wp.june2021.repository;

import mk.ukim.finki.wp.june2021.model.MatchLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchLocationRepository extends JpaRepository<MatchLocation,Long> {
}
