package tests;

import com.codeborne.selenide.Configuration;
import drivers.SelenoidMobileDriver;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;

public class AppiumTestBase {

    @BeforeAll
    public static void beforeAll() {
        addListener("AllureSelenide", new AllureSelenide());
        Configuration.browser = SelenoidMobileDriver.class.getName();
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 10000;

        System.setProperty("selenoid.url", "https://user1:1234@selenoid.autotests.cloud:4444/wd/hub");
        System.setProperty("video.url", "https://selenoid.autotests.cloud/video/");
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

    @BeforeEach
    void startDriver() {
        step("Open application", () -> open());
    }

    @AfterEach
    public void afterEach() {
        String sessionId = getSessionId();

        attachScreenshot("Last screenshot");
        attachPageSource();
        if (System.getProperty("video.url") != "")
            attachVideo(sessionId);
        closeWebDriver();
    }
}
