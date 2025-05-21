package helpers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MenuNavigation {

    /**
     * Нажимает на ссылку "Great! Return to menu" и проверяет, что элемент с идентификатором "kiwiduckgithubio" видим на странице.
     *
     * @param driver экземпляр WebDriver, используемый для взаимодействия с веб-страницей.
     * @throws IllegalArgumentException если переданный WebDriver равен null.
     */
    public static void clickReturnToMenuAndVerifyElement(WebDriver driver) {
        Assertions.assertNotNull(driver, "WebDriver не должен быть null");
        driver.findElement(By.linkText("Great! Return to menu")).click();
        ResultOutput.log("Поиск элемента с идентификатором \"kiwiduckgithubio\"");
        WebElement element = driver.findElement(By.id("kiwiduckgithubio"));
        ResultOutput.log("Проверка, что элемент <h1> видим на странице");
        Assertions.assertTrue(element.isDisplayed(), "Элемент <h1> не видим!");
    }
}
