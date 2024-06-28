package mk.ukim.finki.wp.kol2021.restaurant.service;

import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;

import java.util.List;

public interface MenuItemService {

    MenuItem findById(Long id);

    List<MenuItem> listAll();

    MenuItem create(String name, String description);

}
