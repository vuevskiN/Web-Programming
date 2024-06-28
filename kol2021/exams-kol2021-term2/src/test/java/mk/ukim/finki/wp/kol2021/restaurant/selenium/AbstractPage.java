package mk.ukim.finki.wp.kol2021.restaurant.selenium;

import lombok.Getter;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;

@Getter
public class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        driver.get(url);
    }

    public static void assertRelativeUrl(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        String current = driver.getCurrentUrl();
        ExamAssert.assertEquals("Current url is not " + relativeUrl, url, current);
    }

    public static void assertAbsoluteUrl(WebDriver driver, String url) {

        String current = driver.getCurrentUrl();
        ExamAssert.assertEquals("Current url is not " + url, url, current);
    }

}
