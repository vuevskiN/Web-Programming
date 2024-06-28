package mk.ukim.finki.wp.jan2024g2;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ItemsPage extends AbstractPage {

    private WebElement tags;

    private WebElement postType;

    private WebElement filter;

    @FindBy(css = "tr[class=item]")
    private List<WebElement> rows;

    @FindBy(css = ".delete-item")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-item")
    private List<WebElement> editButtons;

    @FindBy(css = ".like-item")
    private List<WebElement> voteButtons;

    @FindBy(css = ".add-item")
    private List<WebElement> addButton;

    public ItemsPage(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage to(WebDriver driver) {
        get(driver, "/");
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public ItemsPage filter(String postType, String tagId) {
        System.out.println(driver.getCurrentUrl());

        Select selectTag = new Select(this.tags);
        selectTag.selectByValue(tagId);

        Select select = new Select(this.postType);
        select.selectByValue(postType);

        this.filter.click();
        String url = "/?tagId=" + tagId + "&postType=" + postType;
        assertRelativeUrl(this.driver, url.replaceAll(" ", "+"));
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertButtons(int deleteButtonsCount, int editButtonsCount, int addButtonsCount, int voteButtonsCount) {
        ExamAssert.assertEquals("delete btn count", deleteButtonsCount, this.getDeleteButtons().size());
        ExamAssert.assertEquals("edit btn count", editButtonsCount, this.getEditButtons().size());
        ExamAssert.assertEquals("add btn count", addButtonsCount, this.getAddButton().size());
        ExamAssert.assertEquals("vote btn count", voteButtonsCount, this.getVoteButtons().size());
    }

    public boolean assertItems(int expectedItemsNumber) {
        return ExamAssert.assertEquals("Number of items", expectedItemsNumber, this.getRows().size());
    }

    public WebElement getFilter() {
        return filter;
    }

    public WebElement getPostType() {
        return postType;
    }

    public WebElement getTag() {
        return tags;
    }

    public List<WebElement> getRows() {
        return rows;
    }

    public List<WebElement> getDeleteButtons() {
        return deleteButtons;
    }

    public List<WebElement> getEditButtons() {
        return editButtons;
    }

    public List<WebElement> getVoteButtons() {
        return voteButtons;
    }

    public List<WebElement> getAddButton() {
        return addButton;
    }
}
