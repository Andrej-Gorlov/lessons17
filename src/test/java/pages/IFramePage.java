package pages;

import helpers.MenuNavigation;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class IFramePage extends WebDriverManager {

    public IFramePage(){
        checkDriverInitialization();
        ResultOutput.log("Загруска страницы /iframe");
        loadPage("https://kiwiduck.github.io/iframe");
    }

    public void verifyCodeAndReturnToMenu(){
        WebElement codeInput = waitForElementAndFind(By.name("code"));
        codeInput.sendKeys(getCodeFromIframe());

        WebElement verifyButton = waitForElementAndFind(By.name("ok"));
        ResultOutput.log("Нажатие на кнопку \"Verify\"");
        verifyButton.click();

        ResultOutput.log("Проверка наличия ссылки с текстом \"Great! Return to menu");
        waitForElementVisible(By.linkText("Great! Return to menu"), 10);
        MenuNavigation.clickReturnToMenuAndVerifyElement(driver);
    }

    /**
     * Ожидает, пока элемент станет видимым, и находит его на странице.
     *
     * @param locator локатор элемента, который нужно найти (например, By.id, By.xpath и т.д.)
     * @return найденный элемент WebElement
     */
    private WebElement waitForElementAndFind(By locator) {
        waitForElementVisible(locator, 10);
        return driver.findElement(locator);
    }

    /**
     * Получает код из IFrame на странице.
     *
     * @return строка, содержащая код, извлеченный из IFrame
     * @throws AssertionError если код равен null или пустой строке
     */
    private String getCodeFromIframe() {
        ResultOutput.log("Переключение на фрейм");
        waitForElementVisible(By.id("code-frame"), 10);
        driver.switchTo().frame("code-frame");

        WebElement codeLabel = driver.findElement(By.id("code"));
        String code = codeLabel.getText().replace("Your code is: ", "");

        Assertions.assertNotNull(code, "Код не должен быть null");
        Assertions.assertFalse(code.isEmpty(), "Код не должен быть пустым");

        ResultOutput.log("Возврат к основному контенту");
        driver.switchTo().defaultContent();
        return code;
    }
}
