package mk.ukim.finki.wp.kol2021.restaurant.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Menu {

    @Id
    @GeneratedValue
    private Long id;

    private String restaurantName;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<MenuItem> menuItems;


    public Menu() {
    }

    public Menu(String restaurantName, MenuType menuType, List<MenuItem> menuItems) {
        this.restaurantName = restaurantName;
        this.menuType = menuType;
        this.menuItems = menuItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
