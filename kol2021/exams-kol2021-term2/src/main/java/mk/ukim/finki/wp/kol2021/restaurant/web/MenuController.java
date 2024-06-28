package mk.ukim.finki.wp.kol2021.restaurant.web;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MenuController {

    private final MenuService service;
    private final MenuItemService menuItemService;

    public MenuController(MenuService service, MenuItemService menuItemService) {
        this.service = service;
        this.menuItemService = menuItemService;
    }


    @GetMapping
    public String showMenus(String nameSearch,  MenuType menuType) {
        List<Menu> menus;
        if (nameSearch == null && menuType == null) {
           menus = service.listAll();
        } else {
           menus = this.service.listMenuItemsByRestaurantNameAndMenuType(nameSearch,  menuType);
        }
        return "";
    }

    @GetMapping
    public String showAdd() {
        return "";
    }

    public String showEdit(Long id) {
        this.service.findById(id);
        return "";
    }

    @GetMapping
    public String create(String name, MenuType menuType, List<Long> menuItemIds) {
        this.service.create(name, menuType, menuItemIds);
        return "";
    }

    @PostMapping
    public String update(Long id, String name, MenuType menuType, List<Long> menuItemIds) {
        this.service.update(id, name, menuType, menuItemIds);
        return "";
    }

    @PostMapping
    public String delete(Long id) {
        this.service.delete(id);
        return "";
    }
}
