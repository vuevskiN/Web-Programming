package mk.ukim.finki.wp.kol2021.restaurant.service.impl;

import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuItemIdException;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuItemRepository;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id).orElseThrow(InvalidMenuItemIdException::new);
    }

    @Override
    public List<MenuItem> listAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem create(String name, String description) {
        return menuItemRepository.save(new MenuItem(name,description));
    }
}
