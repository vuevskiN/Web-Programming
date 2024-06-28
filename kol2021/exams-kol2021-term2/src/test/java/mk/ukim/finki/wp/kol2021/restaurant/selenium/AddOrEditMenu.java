package mk.ukim.finki.wp.kol2021.restaurant.selenium;


import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditMenu extends AbstractPage {

    private WebElement restaurantName;
    private WebElement menuType;
    private WebElement menuItemsId;
    private WebElement submit;

    public AddOrEditMenu(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, MenuType menuType, String[] categories) {
        get(driver, addPath);
        AbstractPage.assertRelativeUrl(driver, addPath);
        AddOrEditMenu addOrEditMenu = PageFactory.initElements(driver, AddOrEditMenu.class);
        addOrEditMenu.restaurantName.sendKeys(name);
        addOrEditMenu.menuType.sendKeys(menuType.name());
        Select select = new Select(addOrEditMenu.menuItemsId);
        for (String c : categories) {
            select.selectByValue(c);
        }
        addOrEditMenu.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage update(WebDriver driver, WebElement editButton, String name, String menuType,  String[] menuItems) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        AbstractPage.assertAbsoluteUrl(driver, href);

        AddOrEditMenu addOrEditMenu = PageFactory.initElements(driver, AddOrEditMenu.class);
        addOrEditMenu.restaurantName.clear();
        addOrEditMenu.restaurantName.sendKeys(name);
        addOrEditMenu.menuType.sendKeys(menuType);

        Select categoriesSelect = new Select(addOrEditMenu.menuItemsId);
        categoriesSelect.deselectAll();


        for (String c : menuItems) {
            categoriesSelect.selectByValue(c);
        }
        addOrEditMenu.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }


}