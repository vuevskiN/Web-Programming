package mk.ukim.finki.wp.june2021.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.june2021.model.MatchType;
import mk.ukim.finki.wp.june2021.service.MatchLocationService;
import mk.ukim.finki.wp.june2021.service.MatchService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final MatchLocationService matchLocationService;

    private final MatchService matchService;

    public DataInitializer(MatchLocationService matchLocationService, MatchService matchService) {
        this.matchLocationService = matchLocationService;
        this.matchService = matchService;
    }

    private MatchType randomizeEventType(int i) {
        if(i % 3 == 0) return MatchType.FRIENDLY;
        else if(i % 3 == 1) return MatchType.CHARITY;
        return MatchType.COMPETITIVE;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.matchLocationService.create("Match Location: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.matchService.create("Match: " + i, "Match description: " + i , 20.9 * i, this.randomizeEventType(i), this.matchLocationService.listAll().get((i-1)%5).getId());
        }
    }
}
