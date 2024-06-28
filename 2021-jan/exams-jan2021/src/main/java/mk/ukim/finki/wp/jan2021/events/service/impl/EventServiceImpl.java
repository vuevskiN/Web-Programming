package mk.ukim.finki.wp.jan2021.events.service.impl;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventLocation;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventIdException;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;
import mk.ukim.finki.wp.jan2021.events.repository.EventLocationRepository;
import mk.ukim.finki.wp.jan2021.events.repository.EventRepository;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    public EventServiceImpl(EventRepository eventRepository, EventLocationRepository eventLocationRepository) {
        this.eventRepository = eventRepository;
        this.eventLocationRepository = eventLocationRepository;
    }

    @Override
    public List<Event> listAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
    }

    @Override
    public Event create(String name, String description, Double price, EventType type, Long location) {
        EventLocation eventLocation = eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
        return eventRepository.save(new Event(name,description,price,type,eventLocation));
    }

    @Override
    public Event update(Long id, String name, String description, Double price, EventType type, Long location) {
        EventLocation eventLocation = eventLocationRepository.findById(location).orElseThrow(InvalidEventLocationIdException::new);
        Event event = eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        event.setName(name);
        event.setDescription(description);
        event.setPrice(price);
        event.setType(type);
        event.setLocation(eventLocation);
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event delete(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        eventRepository.delete(event);
        return event;
    }

    @Override
    public Event like(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(InvalidEventIdException::new);
        event.setLikes(event.getLikes()+1);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> listEventsWithPriceLessThanAndType(Double price, EventType type) {
        if(price != null && type != null){
            return eventRepository.findAllByPriceLessThanAndType(price,type);
        }

        else if(price == null && type == null){
            return eventRepository.findAll();
        }

        else if(price != null){
            return eventRepository.findAllByPriceLessThan(price);
        }

        else{
            return eventRepository.findAllByType(type);
        }
    }
}
