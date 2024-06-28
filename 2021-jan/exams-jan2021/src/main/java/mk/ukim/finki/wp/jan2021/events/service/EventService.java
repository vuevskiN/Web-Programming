package mk.ukim.finki.wp.jan2021.events.service;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventIdException;
import mk.ukim.finki.wp.jan2021.events.model.exceptions.InvalidEventLocationIdException;

import java.util.List;

public interface EventService {

    /**
     * @return List of all events in the database
     */
    List<Event> listAllEvents();

    /**
     * returns the event with the given id
     *
     * @param id The id of the event that we want to obtain
     * @return
     * @throws InvalidEventIdException when there is no event with the given id
     */
    Event findById(Long id);

    /**
     * This method is used to create a new event, and save it in the database.
     *
     * @param name
     * @param description
     * @param price
     * @param type
     * @param location
     * @return The event that is created. The id should be generated when the event is created.
     * @throws InvalidEventLocationIdException when there is no event location with the given id
     */
    Event create(String name, String description, Double price, EventType type, Long location);

    /**
     * This method is used to create a new event, and save it in the database.
     *
     * @param id The id of the event that is being edited
     * @param name
     * @param description
     * @param price
     * @param type
     * @param location
     * @return The event that is updated.
     * @throws InvalidEventIdException  when there is no event with the given id
     * @throws InvalidEventLocationIdException when there is no event location with the given id
     */
    Event update(Long id, String name, String description, Double price, EventType type, Long location);

    /**
     * Method that should delete an event. If the id is invalid, it should throw InvalidEventIdException.
     *
     * @param id
     * @return The event that is deleted.
     * @throws InvalidEventIdException when there is no event with the given id
     */
    Event delete(Long id);

    /**
     * Method that should like an event. If the id is invalid, it should throw InvalidEventIdException.
     *
     * @param id
     * @return The event that is deleted.
     * @throws InvalidEventIdException when there is no event with the given id
     */
    Event like(Long id);

    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param price       Double that is used to filter the events that have a price less than it.
     *                    This param can be null, and is not used for filtering in this case.
     * @param type   Used for filtering the events that belong to that type.
     *                    This param can be null, and is not used for filtering in this case.
     * @return The events that meet the filtering criteria
     */
    List<Event> listEventsWithPriceLessThanAndType(Double price, EventType type);
}
