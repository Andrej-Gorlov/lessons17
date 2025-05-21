package pages;

import helpers.MenuNavigation;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FormPage extends WebDriverManager {

    public FormPage(){
        checkDriverInitialization();
        ResultOutput.log("Загруска страницы /form");
        loadPage("https://kiwiduck.github.io/form");
    }

    public void submitFormAndCheckRequiredFields(){
        ResultOutput.log("Находим все элементы ввода и текстовые области в форме");
        List<WebElement> inputs = driver.findElements(By.cssSelector("#testform input, #testform textarea"));
        waitForElementVisible(By.cssSelector("#testform input[type='submit']"), 10);

        ResultOutput.log("Обрабатываем все обязательные поля");
        inputs.stream()
                .filter(input -> input.getAttribute("required") != null)
                .forEach(input -> {
                    clickSubmitButton();
                    boolean isLinkPresent = isElementPresent(By.id("back"));
                    assertFalse(isLinkPresent, "Ссылка 'Great! Return to menu' должна отсутствовать после отправки формы без заполнения обязательных полей.");

                    fillRequiredField(input);
                });

        ResultOutput.log("Повторно кликаем по кнопке отправки после заполнения всех обязательных полей");
        clickSubmitButton();

        waitForElementVisible(By.linkText("Great! Return to menu"), 10);
        WebElement link = driver.findElement(By.linkText("Great! Return to menu"));
        assertTrue(link.isDisplayed(), "Ссылка 'Great! Return to menu' должна появиться после успешной отправки формы.");

        MenuNavigation.clickReturnToMenuAndVerifyElement(driver);
    }

    private void clickSubmitButton(){
        driver.findElement(By.cssSelector("#testform input[type='submit']")).click();
    }

    /**
     * Заполняет обязательное поле формы в зависимости от его типа.
     *
     * @param input элемент формы, который необходимо заполнить.
     *              Может быть типа input (text, email, file, radio) или textarea.
     */
    private void fillRequiredField(WebElement input) {
        String tagName = input.getTagName();
        String type = input.getAttribute("type");

        if (tagName.equals("input")) {
            switch (type) {
                case "text":
                    input.sendKeys("object");
                    break;
                case "email":
                    input.sendKeys("example@example.com");
                    break;
                case "file":
                    String filePath = Paths.get("src", "test", "resources", "avatar.jpeg").toAbsolutePath().toString();
                    input.sendKeys(filePath);
                    break;
                case "radio":
                    input.click();
                    break;
            }
        } else if (tagName.equals("textarea")) {
            input.sendKeys("about me");
        }
    }

    /**
     * Проверяет, присутствует ли элемент на странице по заданному локатору.
     *
     * @param locator локатор элемента, который нужно найти.
     * @return true, если элемент найден, иначе false.
     */
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
