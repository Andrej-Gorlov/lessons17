package pages;

import helpers.MenuNavigation;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import helpers.enums.ProgrammingLanguage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectPage extends WebDriverManager {

    public SelectPage(){
        checkDriverInitialization();
        ResultOutput.log("Загруска страницы /select");
        loadPage("https://kiwiduck.github.io/select");
    }

    public void selectValuesAndGetResult(){
        waitForElementVisible(By.xpath("//select[@name='hero']"), 10);
        waitForElementVisible(By.xpath("//select[@name='languages']"), 10);

        Select dropdown = new Select(driver.findElement(By.name("hero")));

        ResultOutput.log("Обработка всех доступных опций в выпадающем списке");
        dropdown.getOptions().stream()
                .filter(option -> !option.getAttribute("value").isEmpty())
                .forEach(option -> {
                    dropdown.selectByVisibleText(option.getText());
                    selectRandomLanguages();
                    // Нажатие на кнопку "Get Results"
                    driver.findElement(By.id("go")).click();
                    assertResults(option.getText());
                });

        MenuNavigation.clickReturnToMenuAndVerifyElement(driver);
    }

    /**
     * Выбирает случайные языки программирования из выпадающего списка.
     * Метод случайным образом перемешивает доступные языки и выбирает три из них.
     */
    private void selectRandomLanguages() {

        List<String> languages = new ArrayList<>();
        for (ProgrammingLanguage lang : ProgrammingLanguage.values()) {
            languages.add(lang.getValue());
        }

        Collections.shuffle(languages);

        WebElement multiSelect = driver.findElement(By.name("languages"));
        Select multiSelectDropdown = new Select(multiSelect);
        multiSelectDropdown.deselectAll();

        languages.stream()
                .limit(3)
                .forEach(multiSelectDropdown::selectByVisibleText);
    }

    /**
     * Проверяет, что выбранный элемент и все языки программирования присутствуют на странице.
     *
     * @param selectedHero имя выбранного героя, которое должно быть найдено на странице.
     */
    private void assertResults(String selectedHero) {
        assertTrue(driver.getPageSource().contains(selectedHero));

        List<String> selectedLanguages = Arrays.stream(ProgrammingLanguage.values())
                .map(ProgrammingLanguage::getValue)
                .toList();

        selectedLanguages.forEach(language -> assertTrue(driver.getPageSource().contains(language)));
    }
}
