package mk.ukim.finki.wp.kol2021.restaurant.service.impl;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuIdException;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuItemRepository;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuRepository;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuItemRepository menuItemRepository, MenuRepository menuRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> listAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
    }

    @Override
    public Menu create(String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItems1 = menuItemRepository.findAllById(menuItems);
        return menuRepository.save(new Menu(restaurantName,menuType,menuItems1));
    }

    @Override
    public Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems) {
        List<MenuItem> menuItems1 = menuItemRepository.findAllById(menuItems);
        Menu menu = menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
        menu.setRestaurantName(restaurantName);
        menu.setMenuType(menuType);
        menu.setMenuItems(menuItems1);
        menuRepository.save(menu);
        return menu;
    }

    @Override
    public Menu delete(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
        menuRepository.delete(menu);
        return menu;
    }

    @Override
    public List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType) {
        return null;
    }
}
