package mk.ukim.finki.wp.kol2021.restaurant.service;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuItemIdException;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuIdException;

import java.util.List;

public interface MenuService {

    /**
     * @return List of all menus in the database
     */
    List<Menu> listAll();

    /**
     * returns an optional of the menu with the given id, or an empty optional when there is no menu with this id
     *
     * @param id The id of the menu that we want to obtain
     * @return
     * @throws InvalidMenuIdException when there is no menu with the given id
     */
    Menu findById(Long id);

    /**
     * This method is used to create a new menu, and save it in the database.
     *
     * @param restaurantName
     * @param menuType
     * @param menuItems The list of menu items ids which the menu consists of.
     * @return The menu that is created. The id should be generated when the menu is created.
     * @throws InvalidMenuItemIdException when there is no category with the given id
     */
    Menu create(String restaurantName, MenuType menuType, List<Long> menuItems);

    /**
     * This method is used to create a new meu, and save it in the database.
     *
     * @param id
     * @param restaurantName
     * @param description
     * @param menuType
     * @param menuItems The list of menu items ids which the menu consists of.
     * @return The menu that is updated.
     * @throws InvalidMenuIdException  when there is no menu with the given id
     * @throws InvalidMenuItemIdException when there is no category with the given id
     */
    Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems);

    /**
     * Method that should delete a menu. If the id is invalid, it should throw InvalidMenuIdException.
     *
     * @param id
     * @return The menu that is deleted.
     * @throws InvalidMenuIdException when there is no menu with the given id
     */
    Menu delete(Long id);

    /**
     * The implementation of this method should use repository implementation for the filtering.
     *
     * @param restaurantName       String that is used to filter the menu that contain it in their name.
     *                   This param can be null, and is not used for filtering in this case.
     * @param menuType Used for filtering the menus that belong to the category with id=categoryId.
     *                   This param can be null, and is not used for filtering in this case.
     * @return The menus that meet the filtering criteria
     */
    List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType);

}
