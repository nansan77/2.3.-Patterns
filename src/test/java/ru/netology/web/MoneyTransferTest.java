package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {
    void dataInput(int days) {
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(DataUser.dataInput(days));
    }

    @BeforeEach
    void befor() {
        open("http://localhost:9999");
    }

    @Test
    void getTrueInputValidForm() {
        $("[placeholder=Город]").setValue(DataUser.cityForInput());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(DataUser.dataPhone());
        $("[data-test-id=name].input_type_text .input__control").setValue(DataUser.dataName());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + DataUser.dataInput(inDays)));
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + DataUser.dataInput(inDays)));
    }

    @Test
    void errorExpectedWhenInputIncorrectCity() {
        $("[placeholder=Город]").setValue(DataUser.cityNoVal());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(DataUser.dataPhone());
        $("[data-test-id=name].input_type_text .input__control").setValue(DataUser.dataName());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=city] .input__sub").shouldHave
                (exactTextCaseSensitive("Доставка в выбранный город недоступна"));
    }

    @Test
    void errorExpectedWhenEmptyFieldDate() {
        $("[placeholder=Город]").setValue(DataUser.cityForInput());
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=phone]").$("[name=phone]").setValue(DataUser.dataPhone());
        $("[data-test-id=name].input_type_text .input__control").setValue(DataUser.dataName());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input__sub").shouldHave
                (exactTextCaseSensitive("Неверно введена дата"));
    }

    @Test
    void getTrueWishLetterEBriefInName() {
        $("[placeholder=Город]").setValue(DataUser.cityForInput());
        int inDays = 4;
        dataInput(inDays);
        $("[data-test-id=phone]").$("[name=phone]").setValue(DataUser.dataPhone());
        $("[data-test-id=name].input_type_text .input__control").setValue(DataUser.dataNameWishLetterEBrief());
        $("[class=checkbox__box]").click();
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + DataUser.dataInput(inDays)));
        $$("[class=button__text]").find(exactText("Запланировать")).click();
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").$("[class=notification__content]")
                .shouldHave(textCaseSensitive("Встреча успешно запланирована на " + DataUser.dataInput(inDays)));
    }
}
