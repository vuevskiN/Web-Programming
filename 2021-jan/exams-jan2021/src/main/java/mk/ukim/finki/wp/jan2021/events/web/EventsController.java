package mk.ukim.finki.wp.jan2021.events.web;

import mk.ukim.finki.wp.jan2021.events.model.Event;
import mk.ukim.finki.wp.jan2021.events.model.EventType;
import mk.ukim.finki.wp.jan2021.events.service.EventLocationService;
import mk.ukim.finki.wp.jan2021.events.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EventsController {

    private final EventService eventService;
    private final EventLocationService eventLocationService;

    public EventsController(EventService eventService, EventLocationService eventLocationService) {
        this.eventService = eventService;
        this.eventLocationService = eventLocationService;
    }

    /**
     * This method should use the "list.html" template to display all events.
     * The method should be mapped on paths '/' and '/events'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all events should be displayed.
     * If one, or both of the arguments are not 'null', the events that are the result of the call
     * to the method 'listEventsWithPriceLessThanAndType' from the EventService should be displayed.
     *
     * @param price
     * @param type
     * @return The view "list.html".
     */
    @GetMapping({"/","/events"})
    public String showEvents(@RequestParam(required = false) Double price, @RequestParam(required = false) EventType type, Model model) {
        List<Event> eventList;
        if (price == null && type == null) {
           eventList = this.eventService.listAllEvents();
        } else {
         eventList =  this.eventService.listEventsWithPriceLessThanAndType(price, type);
        }
        model.addAttribute("events",eventList);
        model.addAttribute("types",EventType.values());
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/events/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping({"/events/add"})
    public String showAdd(Model model) {
        model.addAttribute("locations",this.eventLocationService.listAll());
        model.addAttribute("types",EventType.values());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case all 'input' elements should be filled with the appropriate value for the event that is updated.
     * The method should be mapped on path '/events/[id]/edit'.
     *
     * @return The view "form.html".
     */
    @GetMapping({"/events/{id}/edit"})
    public String showEdit(@PathVariable Long id, Model model) {
       model.addAttribute("event", this.eventService.findById(id));
        model.addAttribute("locations",this.eventLocationService.listAll());
        model.addAttribute("types",EventType.values());
        return "form";
    }

    /**
     * This method should create an event given the arguments it takes.
     * The method should be mapped on path '/events'.
     * After the event is created, all events should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/events"})
    public String create(@RequestParam String name,@RequestParam String description,@RequestParam Double price,@RequestParam EventType type,@RequestParam Long location) {
        this.eventService.create(name, description, price, type, location);
        return "redirect:/events";
    }

    /**
     * This method should update an event given the arguments it takes.
     * The method should be mapped on path '/events/[id]'.
     * After the event is updated, all events should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/events/{id}"})
    public String update(@PathVariable Long id,@RequestParam String name,@RequestParam String description,@RequestParam Double price,@RequestParam EventType type,@RequestParam Long location) {
        this.eventService.update(id, name, description, price, type, location);
        return "redirect:/events";
    }

    /**
     * This method should delete the event that has the appropriate identifier.
     * The method should be mapped on path '/events/[id]/delete'.
     * After the event is deleted, all events should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/events/{id}/delete"})
    public String delete(@PathVariable Long id) {
        this.eventService.delete(id);
        return "redirect:/events";
    }

    /**
     * This method should increase the number of likes of the appropriate event by 1.
     * The method should be mapped on path '/events/[id]/like'.
     * After the operation, all events should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/events/{id}/like"})
    public String like(@PathVariable Long id) {
        this.eventService.like(id);
        return "redirect:/events";
    }
}
