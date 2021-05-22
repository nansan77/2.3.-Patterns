package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;
import static ru.netology.web.DataGenerator.*;

public class CardDelivery {
    private SelenideElement form;
    private final String city = getRandomCity();
    private final String date = getRelevantDate(3);
    private final String anotherDate = getRelevantDate(15);
    private final String invalidDate = getIrrelevantDate();
    private final String name = getFakerName();
    private final String phone = getFakerPhone();

    @BeforeEach
    void setup(){
        open("http://localhost:9999");
        form = $("[action]");
        $(cssSelector("[data-test-id=city] input")).sendKeys(city);
        $(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);

    }

    @Test
    void shouldSubmitWithValidData() {
        $(cssSelector("[data-test-id=date] input")).sendKeys(date);
        $(cssSelector("[name=name]")).sendKeys(name);
       $(cssSelector("[name=phone]")).sendKeys(phone);
        $(cssSelector("[data-test-id=agreement]")).click();
       $(byText("Запланировать")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
    }

    @Test
    void shouldSuggestNewDateWithValidData() {
        form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        $(cssSelector("[name=name]")).sendKeys(name);
        $(cssSelector("[name=phone]")).sendKeys(phone);
        $(cssSelector("[data-test-id=agreement]")).click();
        $(byText("Запланировать")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        open("http://localhost:9999");
        $(cssSelector("[data-test-id=city] input")).sendKeys(city);
        $(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        $(cssSelector("[data-test-id=date] input")).sendKeys(anotherDate);
        $(cssSelector("[name=name]")).sendKeys(name);
        $(cssSelector("[name=phone]")).sendKeys(phone);
        $(cssSelector("[data-test-id=agreement]")).click();
        $(byText("Запланировать")).click();
        $(cssSelector(".notification_status_error .button")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(anotherDate));
    }

    @Test
    void shouldNotSuggestNewDateWithInvalidDate() {
        $(cssSelector("[data-test-id=date] input")).sendKeys(date);
        $(cssSelector("[name=name]")).sendKeys(name);
       $(cssSelector("[name=phone]")).sendKeys(phone);
        $(cssSelector("[data-test-id=agreement]")).click();
        $(byText("Запланировать")).click();
        $(cssSelector(".notification__content")).waitUntil(Condition.visible, 15000).shouldHave(text(date));
        open("http://localhost:9999");
        $(cssSelector("[data-test-id=city] input")).sendKeys(city);
        $(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
        $(cssSelector("[data-test-id=date] input")).sendKeys(invalidDate);
        $(cssSelector("[name=name]")).sendKeys(name);
        $(cssSelector("[name=phone]")).sendKeys(phone);
        $(cssSelector("[data-test-id=agreement]")).click();
       $(byText("Запланировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 15000);
    }
}
