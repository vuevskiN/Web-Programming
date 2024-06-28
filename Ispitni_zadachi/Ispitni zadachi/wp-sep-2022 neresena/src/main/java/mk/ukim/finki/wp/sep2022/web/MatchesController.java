package mk.ukim.finki.wp.sep2022.web;

import mk.ukim.finki.wp.sep2022.model.MatchType;
import mk.ukim.finki.wp.sep2022.service.MatchService;

public class MatchesController {

    private final MatchService matchService;

    public MatchesController(MatchService service) {
        this.matchService = service;
    }

    /**
     * This method should use the "list.html" template to display all matches.
     * The method should be mapped on paths '/' and '/matches'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all matches should be displayed.
     * If one, or both of the arguments are not 'null', the matches that are the result of the call
     * to the method 'listMatchesWithPriceLessThanAndType' from the MatchService should be displayed.
     *
     * @param price
     * @param type
     * @return The view "list.html".
     */
    public String showMatches(Double price, MatchType type) {
        if (price == null && type == null) {
            this.matchService.listAllMatches();
        } else {
            this.matchService.listMatchesWithPriceLessThanAndType(price, type);
        }
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/matches/add'.
     *
     * @return The view "form.html".
     */
    public String showAdd() {
        return "";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case all 'input' elements should be filled with the appropriate value for the entity that is updated.
     * The method should be mapped on path '/matches/[id]/edit'.
     *
     * @return The view "form.html".
     */
    public String showEdit(Long id) {
        this.matchService.findById(id);
        return "";
    }

    /**
     * This method should create a match given the arguments it takes.
     * The method should be mapped on path '/matches'.
     * After the entity is created, all matches should be displayed.
     *
     * @return The view "list.html".
     */
    public String create(String name, String description, Double price, MatchType type, Long location) {
        this.matchService.create(name, description, price, type, location);
        return "";
    }

    /**
     * This method should update a match given the arguments it takes.
     * The method should be mapped on path '/matches/[id]'.
     * After the entity is updated, all matches should be displayed.
     *
     * @return The view "list.html".
     */
    public String update(Long id, String name, String description, Double price, MatchType type, Long location) {
        this.matchService.update(id, name, description, price, type, location);
        return "";
    }

    /**
     * This method should delete the match that has the appropriate identifier.
     * The method should be mapped on path '/matches/[id]/delete'.
     * After the entity is deleted, all matches should be displayed.
     *
     * @return The view "list.html".
     */
    public String delete(Long id) {
        this.matchService.delete(id);
        return "";
    }

    /**
     * This method should increase the number of followers of the appropriate match by 1.
     * The method should be mapped on path '/matches/[id]/follow'.
     * After the operation, all matches should be displayed.
     *
     * @return The view "list.html".
     */
    public String follow(Long id) {
        this.matchService.follow(id);
        return "";
    }
}
