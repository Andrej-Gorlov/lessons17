import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import pages.FormPage;
import pages.IFramePage;
import pages.SelectPage;

public class KiwiDuckTest {
    private String nameMethod;

    @BeforeEach
    public void setUp() {
        ResultOutput.log("выполнение тестов класса KiwiDuckTest");
    }

    @Test
    public void testSelect() {
        runTest("testSelect", () -> {
            SelectPage page = new SelectPage();
            page.selectValuesAndGetResult();
        });
    }

    @Test
    public void testForm() {
        runTest("testForm", () -> {
            FormPage page = new FormPage();
            page.submitFormAndCheckRequiredFields();
        });
    }

    @Test
    public void testIFrame() {
        runTest("testIFrame", () -> {
            IFramePage page = new IFramePage();
            page.verifyCodeAndReturnToMenu();
        });
    }

    private void runTest(String methodName, Runnable test) {
        nameMethod = methodName;
        ResultOutput.printTestStart(nameMethod);
        test.run();
    }

    @AfterEach
    public void tearDown() {
        ResultOutput.printTestEnd(nameMethod);
        WebDriverManager.closeDriver();
    }
}
