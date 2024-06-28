package mk.ukim.finki.wp.kol2021.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.wp.exam.util.CodeExtractor;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import mk.ukim.finki.wp.exam.util.SubmissionHelper;
import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuItem;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.selenium.AbstractPage;
import mk.ukim.finki.wp.kol2021.restaurant.selenium.AddOrEditMenu;
import mk.ukim.finki.wp.kol2021.restaurant.selenium.ItemsPage;
import mk.ukim.finki.wp.kol2021.restaurant.selenium.LoginPage;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuItemService;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    static {
        SubmissionHelper.exam = "wp-kol";
        SubmissionHelper.index = "196141";
    }

    @Autowired
    MenuItemService menuItemService;


    @Autowired
    MenuService menuService;


    @Test
    public void test1_list() {
        SubmissionHelper.startTest("test-list-20");
        List<Menu> items = this.menuService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");
        listPage.assertItems(itemNum);

        SubmissionHelper.endTest();
    }

    @Test
    public void test2_filter() {
        SubmissionHelper.startTest("test-filter-20");
        ExamAssert.assertEquals("without filter", 10, this.menuService.listMenuItemsByRestaurantNameAndMenuType(null, null).size());
        ExamAssert.assertEquals("by name only", 2, this.menuService.listMenuItemsByRestaurantNameAndMenuType("Restaurant 1", null).size());
        ExamAssert.assertEquals("by category only", 3, this.menuService.listMenuItemsByRestaurantNameAndMenuType(null, MenuType.COFFEE).size());
        ExamAssert.assertEquals("by name and category", 2, this.menuService.listMenuItemsByRestaurantNameAndMenuType("Restaurant 1", MenuType.COOKIE).size());
        SubmissionHelper.endTest();
    }

    @Test
    public void test3_create() {
        SubmissionHelper.startTest("test-create-20");
        List<MenuItem> menuItems = this.menuItemService.listAll();
        List<Menu> items = this.menuService.listAll();

        int itemNum = items.size();

        String[] itemsIds = new String[]{
                menuItems.get(0).getId().toString(),
                menuItems.get(menuItems.size() - 1).getId().toString()
        };

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
        }
        listPage = AddOrEditMenu.add(this.driver, ADD_URL, "test", MenuType.COFFEE, itemsIds);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        listPage.assertItems(itemNum + 1);

        SubmissionHelper.endTest();
    }

    @Test
    public void test4_edit() throws Exception {
        SubmissionHelper.startTest("test-edit-20");
        List<MenuItem> menuItems = this.menuItemService.listAll();
        List<Menu> items = this.menuService.listAll();

        int itemNum = items.size();

        String[] itemsIds = new String[]{
                menuItems.get(0).getId().toString(),
                menuItems.get(menuItems.size() - 1).getId().toString()
        };

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
            listPage = AddOrEditMenu.update(this.driver, listPage.getEditButtons().get(itemNum - 1), "test1", MenuType.COOKIE.name(), itemsIds);
            AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
            ExamAssert.assertEquals("The updated name is not as expected.", "test1", listPage.getItemRows().get(itemNum).findElements(By.tagName("td")).get(0).getText().trim());
            listPage.assertItems(itemNum + 1);
        } catch (Exception e) {
            MockHttpServletRequestBuilder editRequest = MockMvcRequestBuilders
                    .post("/menu/" + items.get(itemNum - 1).getId())
                    .param("name", "test1")
                    .param("menuType", MenuType.COFFEE.name());
            for (MenuItem c : menuItems) {
                editRequest = editRequest.param("menuItemIds", c.getId().toString());
            }

            this.mockMvc.perform(editRequest)
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));
            items = this.menuService.listAll();
            ExamAssert.assertEquals("Number of items", itemNum, items.size());
            ExamAssert.assertEquals("The updated name is not as expected.", "test1", items.get(itemNum - 1).getRestaurantName());
        }

        SubmissionHelper.endTest();
    }

    @Test
    public void test5_delete() throws Exception {
        SubmissionHelper.startTest("test-delete-10");
        List<Menu> items = this.menuService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
            listPage.getDeleteButtons().get(itemNum - 1).click();
            AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
            listPage.assertItems(itemNum - 1);
        } catch (Exception e) {
            MockHttpServletRequestBuilder deleteRequest = MockMvcRequestBuilders
                    .post("/menu/" + items.get(itemNum - 1).getId() + "/delete");

            this.mockMvc.perform(deleteRequest)
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));
            items = this.menuService.listAll();
            ExamAssert.assertEquals("Number of items", itemNum - 1, items.size());
        }
        SubmissionHelper.endTest();
    }

    @Test
    public void test6_security_urls() {
        SubmissionHelper.startTest("test-security-urls-10");
        List<Menu> items = this.menuService.listAll();
        int itemNum = items.size();
        String editUrl = "/menu/" + items.get(0).getId() + "/edit";

        ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, "/random");
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        LoginPage.doLogin(this.driver, loginPage, admin, admin);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);


        AbstractPage.get(this.driver, LIST_URL);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);

        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, ADD_URL);

        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, editUrl);

        LoginPage.logout(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        SubmissionHelper.endTest();
    }

    @Test
    public void test7_security_buttons() {
        SubmissionHelper.startTest("test-security-buttons-10");
        List<Menu> items = this.menuService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");
        listPage.assertButtons(0, 0, 0);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        listPage.assertButtons(itemNum, itemNum, 1);
        SubmissionHelper.endTest();
    }


    private HtmlUnitDriver driver;
    private MockMvc mockMvc;

    private static String admin = "admin";

    private static boolean dataInitialized = false;


    @BeforeEach
    private void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }


    private void initData() {
        if (!dataInitialized) {

            dataInitialized = true;
        }
    }


    @AfterAll
    public static void finializeAndSubmit() throws JsonProcessingException {
        CodeExtractor.submitSourcesAndLogs();
    }


    public static final String LIST_URL = "/menu";
    public static final String ADD_URL = "/menu/add";
    public static final String LOGIN_URL = "/login";
}
