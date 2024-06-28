package mk.ukim.finki.wp.jan2021.events.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.service.EventLocationService;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class DataInitializer {

    private final EventLocationService eventLocationService;

    private final EventService eventService;

    public DataInitializer(EventLocationService eventLocationService, EventService eventService) {
        this.eventLocationService = eventLocationService;
        this.eventService = eventService;
    }

    private EventType randomizeEventType(int i) {
        if(i % 3 == 0) return EventType.CONCERT;
        else if(i % 3 == 1) return EventType.SEMINAR;
        return EventType.SPORT;
    }

    @PostConstruct
    public void initData() {
        for (int i = 1; i < 6; i++) {
            this.eventLocationService.create("Event Location: " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.eventService.create("Event: " + i, "Event description: " + i , 20.9 * i, this.randomizeEventType(i), this.eventLocationService.listAll().get((i-1)%5).getId());
        }
    }
}
