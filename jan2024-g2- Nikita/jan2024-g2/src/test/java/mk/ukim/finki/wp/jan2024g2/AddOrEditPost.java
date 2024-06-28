package mk.ukim.finki.wp.jan2024g2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditPost extends AbstractPage {
    private WebElement title;
    private WebElement content;
    private WebElement dateCreated;
    private WebElement postType;
    private WebElement tags;
    private WebElement submit;

    public AddOrEditPost(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String title, String content, String dateCreated, String postType, String[] tags) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditPost addOrEditPost = PageFactory.initElements(driver, AddOrEditPost.class);
        addOrEditPost.assertNoError();
        addOrEditPost.title.sendKeys(title);
        addOrEditPost.content.sendKeys(content);
        addOrEditPost.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditPost.postType);
        selectType.selectByValue(postType);

        Select selectTag = new Select(addOrEditPost.tags);
        for (String i: tags) {
            selectTag.selectByValue(i);
        }

        addOrEditPost.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage update(WebDriver driver, WebElement editButton, String title, String content, String dateCreated, String postType, String[] tags) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        AddOrEditPost addOrEditPost = PageFactory.initElements(driver, AddOrEditPost.class);
        addOrEditPost.title.clear();
        addOrEditPost.title.sendKeys(title);
        addOrEditPost.content.clear();
        addOrEditPost.content.sendKeys(content);
        addOrEditPost.dateCreated.clear();
        addOrEditPost.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditPost.postType);
        selectType.selectByValue(postType);

        Select selectTags = new Select(addOrEditPost.tags);
        selectTags.deselectAll();
        for (String i: tags) {
            selectTags.selectByValue(i);
        }

        addOrEditPost.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }
}