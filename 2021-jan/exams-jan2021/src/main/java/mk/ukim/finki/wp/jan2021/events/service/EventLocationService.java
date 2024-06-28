package mk.ukim.finki.wp.jan2021.events.service;

import mk.ukim.finki.wp.jan2021.events.model.EventLocation;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;

import java.util.List;

public interface EventLocationService {

    /**
     * returns the event location with the given id
     *
     * @param id The id of the event location that we want to obtain
     * @return
     * @throws InvalidEventLocationIdException when there is no event location with the given id
     */
    EventLocation findById(Long id);

    /**
     * @return List of all event locations in the database
     */
    List<EventLocation> listAll();

    /**
     * This method is used to create a new event location, and save it in the database.
     *
     * @param name
     * @return The event location that is created. The id should be generated when the event location is created.
     */
    EventLocation create(String name);
}
