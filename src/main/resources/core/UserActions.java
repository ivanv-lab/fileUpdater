package ru.wsoft.tests.ui.adm.core;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebElement;
import ru.wsoft.tests.ui.adm.BaseConsoleTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class UserActions extends BaseConsoleTest {

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка фильтрации по объекту {0} #({index})")
    @MethodSource({"objectAcuiList"})
    @Tag("Log0")
    public void acuiLoggerObject(String object) {
        Allure.description(
                "1. Зайти на страницу \"Действия пользователей\" \n\n" +
                        "2. Ожидается: фильтр \"Тип объекта\" содержит " + object + ". Объект может использоваться в фильтрации \n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "anaista@wsoft.ru")
                .inputSet("Тип объекта", object)
                .buttonClick("a")
                .confirmIfExists()
                .tagExists("Тип объекта")
                .tagContains("Тип объекта", object)
                .tagClear("Тип объекта")
                .tagNotExists("Тип объекта")
                .inputSet("Тип объекта", object)
                .buttonClick("a")
                .confirmIfExists()
                .tagContains("Тип объекта", object)
                .buttonClick("Очистить фильтры")
                .tagNotExists("Тип объекта")
        ;
    }

    public static Stream<Arguments> tarifZoneList() {
        return Stream.of(
                Arguments.of("Push"),
                Arguments.of("SMS"),
                Arguments.of("Электронная почта"),
                Arguments.of( "Viber"),
                Arguments.of("WhatsApp"),
                Arguments.of("Mail Notify"),
                Arguments.of("Custom")
        );
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка логирования редактирования Шаблонов в ЛК {0}")
    @MethodSource({"tarifZoneList"})
    @Tag("Log0")
    public void MSG_4742(String transport) {
        ui
                .loginLkui("log@test.ru", "qwe123")
                .subSectionClick("Шаблоны", transport = (transport.equals("Электронная почта")) ? "Email" : transport)
                .tableHrefCellClick("patternLog")
                .buttonClick("s")

                .loginAcui()
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")

                .tableRowExists("log@test.ru", "Шаблоны " + transport, "Шаблон", "Редактирование");
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка логирования отправки на модерацию Шаблонов в ЛК {0}")
    @MethodSource({"tarifZoneList"})
    @Tag("Log2")
    public void MSG_4742_1(String transport) {
        ui
                .loginLkui("clientModerationBlock@qwe.ru", "qwe123")
                .subSectionClick("Шаблоны", transport = (transport.equals("Электронная почта")) ? "Email" : transport)
                .tableHrefCellClick("blockTempMod")
                .buttonClick("~Создать черновик");
        if (transport.equals("Email")) {
            ui.inputSet("Тема письма", "Qgen1234");
        } else if (transport.equals("Mail Notify")) {
            ui
                    .inputSet("Список соц.сетей для отправки", "vk")
                    .inputSet("Текст сообщения для VK", "abcd123");
        } else {
            ui.inputSet("Сообщение", "Log delete1234");
        }

        if (transport.equals("Push")) {
            ui.clickOn("Приложение")
                    .inputSet("Приложения", "BlockApp");
        }
        ui.buttonClick("Отправить на модерацию")
                .buttonClick("Да")

                .loginAcui()
                .subSectionClick("Управление пользователями", "Модерация шаблонов")
                .tableHrefCellClick("blockTempMod")
                .buttonClick("Взять на модерацию")
                .tableHrefCellClick("blockTempMod")
                .buttonClick("Разрешить")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Действие", "Модерация")
                .clickOn("5 мин")
                .buttonClick("Применить")

                .tableRowExists("admin@admin.com", "Модерация шаблонов", "Шаблон", "Модерация");
    }

//    @Epic("Действия пользователей")
//    @ParameterizedTest(name = "Проверка логирования редактирования Шаблонов в ЛК")
//    @MethodSource({"tarifZoneList"})
//    @Tag("Log0")
//    public void MSG_4742_1(String transport, int num) {
//        ui
//                .subSectionClick("Настройки", "Клиенты")
//                .tableHrefCellClick("Wings")
//                .radioButtonOn("Модерация", num)
//                .buttonClick("s")
//                .loginLkui();
//        if (transport.equals("Электронная почта")) transport = "Email";
//        ui
//                .subSectionClick("Шаблоны", transport)
//                .tableHrefCellClick()
//                .buttonClick("s")
//                .loginAcui()
//                .subSectionClick("Управление пользователями", "Действия пользователей")
//                .confirmIfExists()
//                .inputSet("Интерфейс платформы", "Личный кабинет")
//                .inputSet("Пользователь", "WingsRule@example.com")
//                .inputSet("Действие", "Редактирование")
//                .buttonClick("a")
//                .tableRowExists("WingsRule@example.com", "~Шаблоны", "Редактирование");
//    }


    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка логирования редактирования Тарифной зоны. Транспорт {0}")
    @MethodSource({"tarifZoneList"})
    @Tag("Log0")
    public void MSG_4650_0(String transport) {

        String page = "";
        switch (transport) {
            case "Push":
                page = "Тарификация Push клиентов";
                break;
            case "SMS":
                page = "Маршрутизация и тарификация клиентов";
                break;
            case "Электронная почта":
                page = "Тарификация Email клиентов";
                break;
            case "Viber":
                page = "Тарификация Viber клиентов";
                break;
            case "WhatsApp":
                page = "Тарификация WhatsApp клиентов";
                break;
            case "Mail Notify":
                page = "Тарификация Mail Notify клиентов";
                break;
            case "Custom":
                page = "Тарификация Custom клиентов";
                break;
        }
        ui.subSectionClick(transport, page);
        if (transport.equals("SMS")) {
            ui
                    .inputSet("Клиент", "Wings");
        }
        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .tableHrefCellClick("~" + columnValues.get(0))
                .buttonClick("s")
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Страница", transport.equals("SMS")?"Маршрутизация и тарификация SMS клиентов":page)
                .buttonClick("a")
                .tableRowExists("admin@admin.com", transport.equals("SMS")?"Маршрутизация и тарификация SMS клиентов":page, "Редактирование");

        if (!transport.equals("Push")) {

            switch (transport) {
                case "SMS":
                    page = "Маршрутизация и тарификация поставщиков услуг";
                    break;
                case "Электронная почта":
                    page = "Тарификация Email поставщиков услуг";
                    break;
                case "Viber":
                    page = "Тарификация Viber поставщиков услуг";
                    break;
                case "WhatsApp":
                    page = "Тарификация WhatsApp поставщиков услуг";
                    break;
                case "Mail Notify":
                    page = "Тарификация Mail Notify поставщиков услуг";
                    break;
                case "Custom":
                    page = "Тарификация Custom поставщиков услуг";
                    break;
            }
            ui.subSectionClick(transport, page);
            if (transport.equals("SMS")) {
                ui
                        .inputSet("Поставщик услуг", "providerLog");
            }
            table = $(byXpath(".//table"));

            columnValues =
                    table.$$(byXpath(".//tr/td[1]"))
                            .shouldHave(CollectionCondition
                                    .sizeGreaterThan(0))
                            .asFixedIterable()
                            .stream().map(SelenideElement::getText)
                            .collect(Collectors.toList());

            ui
                    .tableHrefCellClick("~" + columnValues.get(0))

                    .buttonClick("s")
                    .subSectionClick("Управление пользователями", "Действия пользователей")
                    .confirmIfExists()
                    .inputSet("Интерфейс платформы", "Интерфейс администратора")
                    .inputSet("Пользователь", "admin@admin.com")
                    .inputSet("Страница", transport.equals("SMS")?"Маршрутизация и тарификация SMS поставщиков услуг":page)
                    .buttonClick("a")
                    .tableRowExists("admin@admin.com", transport.equals("SMS")?"Маршрутизация и тарификация SMS поставщиков услуг":page, "Редактирование");
        }
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия логирования действия \"{1}\" на странице {0} для пользователя #({index})")
    @MethodSource({"acuiLoggerList"})
    @Tag("Log0")
    public void acuiNoLogger(String page, String action) {
        Allure.description("1. Зайти на страницу " + page + "\n\n" +
                "2. Выполнить действие " + action + "\n\n" +
                "3. Зайти на страницу \"Действия пользователей\" \n\n" +
                "4. Ожидается: действие " + action + " на странице " + page + " отсутствует в таблице \n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputSet("Страница", page)
                .buttonClick("a")
                .tableRowNotExists("areyouserios@black.com", "~" + page, action)
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия логирования действия \"Просмотр\" на странице {0}:{1} для пользователя Serios #({index})")
    @MethodSource({"subSectionList", "statisticList", "onlyView", "onlySettings", "acuiLog"})
    @Tag("Log5")
    public void acuiNoLoggerWithActionV(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Отключить логирование действия \"Просмотр\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Просмотр\" на странице " + page + " отсутствует в таблице \n\n");

        ui
                .logout()
                .loginAcui("areyouserios@black.com", "RandomPass")
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Просмотр")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .clickOn("5 мин")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputSet("Страница", alterName)
                .buttonClick("a")
                .tableRowNotExists("areyouserios@black.com", alterName, "Просмотр")
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка логирования объекта \"{0}\" для пользователя #({index})")
    @MethodSource({"objectAllAction", "objectBalance", "objectStatList"})
    @Tag("Log2")
    public void loggerObject(String object) {
        Allure.description("1. Зайти на страницу \"Действия пользователей\" \n\n" +
                "2. Ожидается: Объект \"{0}\"  присутствует в таблице \n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Тип объекта", object)
                .buttonClick("a")
                .tableRowExists(object)
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Просмотр\" на странице {0}:{1} для пользователя Admin #({index})")
    @MethodSource({"subSectionList", "statisticList", "onlyView", "onlySettings", "acuiLog"})
    @Tag("Log1")
    public void acuiLoggerView(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Просмотр\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Просмотр\" на странице " + page + " присутсвует в таблице \n\n");

        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Просмотр")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-4)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Страница", alterName)
                .inputSet("Действие", "Просмотр")
                .buttonClick("a")
                .tableRowExists("admin@admin.com", alterName, "Просмотр")
        ;
    }

    public static Stream<Arguments> dataForMSG_4541() {
        return Stream.of(
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Изменение адреса отправителя SMS"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Ограничение адресов отправителей"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Операторские шаблоны Mail Notify"),
                Arguments.of("Настройки", "Типы сообщений", "Типы сообщений"),
                Arguments.of("Push", "Управление приложениями", "Управление приложениями"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Подключения к операторам по SMPP"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Маршрутизация входящих SMS сообщений"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Маршрутизация входящих Viber сообщений"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Маршрутизация входящих WhatsApp сообщений"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Операторские сервисы Mail Notify"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Ограничение количества отправляемых сообщений"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Блокировка одинаковых сообщений")
        );
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Редактирование\" " +
            "на странице {0}:{1} #({index})")
    @MethodSource({"dataForMSG_4541"})
    @Tag("Log2")
    public void MSG_4541(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Редактирование\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: на странице " + page + " присутствует строка Редактирование в таблице\n\n");

        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page);

        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .tableHrefCellClick("~" + columnValues.get(0))
                .buttonClick("s");
        if (page.equals("Управление приложениями"))
            ui.waiting(30);
        ui.subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", alterName, "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования Изменения приоритета SMS")
    @Test
    @Tag("Log1")
    public void MSG_4541_1() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Настройки", "Изменение приоритета")

                .tableHrefCellClick("SMS")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Изменение приоритета", "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования Блокировка одинаковых сообщений SMS")
    @Test
    @Tag("Log1")
    public void MSG_4541_2() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Настройки", "Блокировка одинаковых сообщений")

                .tableHrefCellClick("SMS")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Блокировка одинаковых сообщений", "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования редактирования Атрибутов приложения")
    @Test
    @Tag("Log1")
    public void MSG_4541_3() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Push", "Атрибуты приложений")

                .inputSet("Приложение", "SampleApp");

        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .tableHrefCellClick(columnValues.get(0))
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Атрибуты приложений", "Редактирование");
    }

    public static Stream<Arguments> dataForMSG_4543() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Шаблоны SMS"),
                Arguments.of("Электронная почта", "Шаблоны", "Шаблоны Email"),
                Arguments.of("Push", "Шаблоны", "Шаблоны Push"),
                Arguments.of("Viber", "Шаблоны", "Шаблоны Viber"),
                Arguments.of("WhatsApp", "Шаблоны", "Шаблоны WhatsApp"),
                Arguments.of("Mail Notify", "Шаблоны", "Шаблоны Mail Notify"),
                Arguments.of("Custom", "Шаблоны", "Шаблоны Custom"),
                Arguments.of("Настройки", "Поставщики услуг", "Поставщики услуг"),
                Arguments.of("Настройки", "Клиенты", "Клиенты"),
                Arguments.of("Настройки", "Аккаунты", "Аккаунты"),
                Arguments.of("Настройки", "Адреса отправителей", "Адреса отправителей")
        );
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Редактирование\" " +
            "на странице {0}:{1} #({index})")
    @MethodSource({"dataForMSG_4543"})
    @Tag("Log2")
    public void MSG_4543(String section, String page, String alterName) {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page);

        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .tableHrefCellClick("~" + columnValues.get(0))
                .buttonClick("s")
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", alterName, "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования редактирования Черных списков")
    @Test
    @Tag("Log1")
    public void MSG_4605() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Настройки", "Черные списки");

        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .tableHrefCellClick(columnValues.get(0))
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Черные списки", "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования редактирования Контактов Черных списков")
    @Test
    @Tag("Log1")
    public void MSG_4606() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Настройки", "Черные списки");

        SelenideElement table = $(byXpath(".//table"));

        List<String> columnValues =
                table.$$(byXpath(".//tr[td[5]/div/a[text()>0]]/td[1]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        ui
                .filterSet("Название", columnValues.get(0))
                .tableCellClick("Количество контактов", columnValues.get(0));

        List<String> cHSValues =
                table.$$(byXpath(".//tr/td[2]"))
                        .shouldHave(CollectionCondition
                                .sizeGreaterThan(0))
                        .asFixedIterable()
                        .stream().map(SelenideElement::getText)
                        .collect(Collectors.toList());

        SelenideElement element = $(byXpath(".//tr/td/div/a[contains(text(),'" + cHSValues.get(0) + "')]"));
        element.click();
        ui
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Черные списки", "Контакт", "Редактирование");
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Просмотр\" на странице {0}:{1} для пользователя Serios #({index})")
    @MethodSource({"subSectionList"})
    @Tag("Log1")
    public void acuiLoggerOnlyTwoView(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Просмотр\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: на странице " + page + " присутствует 2 строки в таблице\n\n");

        ui
                .logout()
                .loginAcui("anaista@wsoft.ru", "AlexQA")
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Просмотр")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "anaista@wsoft.ru")
                .inputSet("Страница", alterName)
                .inputSet("Действие", "Просмотр")
                .clickOn("5 мин")
                .buttonClick("a")
                .tableRowExists("anaista@wsoft.ru", alterName, "Просмотр")
                .tableRowCountIs(2)
        ;
    }


    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Просмотр\" на странице {0}:{1} для пользователя Serios #({index})")
    @MethodSource({"acuiLog", "statisticList", "onlyView"})
    @Tag("Log1")
    public void acuiLoggerOnlyOneView(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Просмотр\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Просмотр\" на странице " + page + " присутствует в таблице В единственном числе\n\n");

        ui
                .logout()
                .loginAcui("anaista@wsoft.ru", "AlexQA")
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Просмотр")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "anaista@wsoft.ru")
                .inputSet("Страница", alterName)
                .inputSet("Действие", "Просмотр")
                .clickOn("5 мин")
                .buttonClick("a")
                .tableRowExists("anaista@wsoft.ru", alterName, "Просмотр")
                .tableRowCountIs(1)
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Удаление\" на странице {0}:{1} для пользователя с ролью #({index})")
    @MethodSource({"loggerDeleteList"})
    @Tag("Del4")
    public void acuiLoggerDelete(String section, String page, String alterName, String variableName, String filterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Удаление\"\n\n" +
                "3. Зайти на страницу " + page + "\n\n и удалить запись с пометкой LD" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Удаление\" на странице " + page + " присутсвует в таблице \n\n");

//        if (page.contains("Тарификация Custom поставщиков услуг")) ui.setNotDefinedStatus("Такого функционала для транспорта Custom нет!");
        if (alterName.contains("Шаблоны Call")) ui.setNotDefinedStatus("Звонки появятся в 1.x");


        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Удаление")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page);

        if (page.equals("Атрибуты приложений")) ui.inputSet("Приложение", "LogApp");

        ui

                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet(filterName, variableName)
                .buttonClick("a")

                .delete("~" + variableName)
                .buttonClick("Очистить фильтры")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-4)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Страница", alterName)
                .inputSet("Действие", "Удаление")
                .buttonClick("a")
                .tableRowExists("admin@admin.com", alterName, "Удаление")
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка работы логирования действия \"Редактирование\" на странице {0}:{1} для пользователя Admin #({index})")
    @MethodSource({"loggerEditList"})
    @Tag("Log2")
    public void acuiLoggerEdit(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Включить логирование действия \"Редактирование\"\n\n" +

                "3. Зайти на страницу \"Действия пользователей\" \n\n" +
                "4. Ожидается: действие \"Редактирование\" на странице " + page + " присутсвует в таблице \n\n");

//        if (page.contains("Тарификация Custom поставщиков услуг")) ui.setNotDefinedStatus("Такого функционала для транспорта Custom нет!");

        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-4)
                .calendarSave()
                //.calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Страница", alterName)
                .inputSet("Действие", "Редактирование")
                .buttonClick("a")
                .tableRowExists("admin@admin.com", alterName, "Редактирование")
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия логирования действия \"Удаление\" на странице {0}:{1} для пользователя #({index})")
    @MethodSource({"subSectionList", "acuiLog"})
    @Tag("Log5")
    public void acuiNoLoggerWithActionD(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Отключить логирование действия \"Удаление\"\n\n" +
                "3. Удалить запись на странице " + page + "\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Удаление\" на странице " + page + " отсутствует в таблице \n\n");

        ui
                .logout()
                .loginAcui("areyouserios@black.com", "RandomPass")
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Удаление")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .clickOn("5 мин")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputSet("Страница", alterName)
                .buttonClick("a")
                .tableRowNotExists("areyouserios@black.com", alterName, "Удаление")
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка наличия действия {0} для платформы ИА #({index})")
    @MethodSource({"actionListADM", "actionListBoth"})
    @Tag("Log0")
    public void acuiADMRequiredAction(String action) {
        Allure.description(
                "1. Зайти на страницу \"Действия пользователей\" \n\n" +
                        "2. Выбрать в качестве платформы интерфейс администратора \n\n" +
                        "3. Ожидается: в фильтре действия есть действие для ИА\n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputClick("Действие")
                .dropDownListOptionExists(action)
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия действия {0} для платформы ЛК #({index})")
    @MethodSource({"actionListADM"})
    @Tag("Log0")
    public void acuiLKNORequiredAction(String action) {
        Allure.description(
                "1. Зайти на страницу \"Действия пользователей\" \n\n" +
                        "2. Выбрать в качестве платформы Личный Кабинет \n\n" +
                        "3. Ожидается: в фильтре действия есть действие для ЛК\n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Пользователь", "WingsRule@example.com")
                .inputClick("Действие")
                .dropDownListOptionNotExists(action)
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия действия {0} для платформы ИА #({index})")
    @MethodSource({"actionListLK"})
    @Tag("Log0")
    public void acuiADMNonRequiredAction(String action) {
        Allure.description(
                "1. Зайти на страницу \"Действия пользователей\" \n\n" +
                        "2. Выбрать в качестве платформы интерфейс администратора \n\n" +
                        "3. Ожидается: в фильтре действия нет действия для ЛК\n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputClick("Действие")
                .dropDownListOptionNotExists(action)
        ;
    }

    @Epic("Действия пользователей")
    @Test
    @Description("Проверка логирование редактирования Панели администратора")
    @Tag("Log0")
    public void MSG_4616() {
        ui
                .sectionClick("Панель администратора")
                .buttonClick("Добавить");

        SelenideElement element = $(byXpath(".//ul[@class='dropdown-menu']/li[1]"));
        element.click();
        element = $(byXpath(".//button//i[contains(text(),'done')]"));
        element.click();
        element = $(byXpath(".//button//i[contains(text(),'settings')]"));
        element.click();
        element = $(byXpath(".//button//i[contains(text(),'done')]"));
        element.click();

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .buttonClick("Применить")
                .clickOn("5 мин")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Панель администратора",
                        "Виджет", "Редактирование");
    }


    @Epic("Действия пользователей")
    @Test
    @Tag("Log2")
    public void MSG_4649() {
        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Редактирование")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("SMS", "~тарификация")
                .inputSet("Клиент", "~clientLog")

                .tableHrefCellClick("~clientLog")
                .buttonClick("s")

                .buttonClick("Настройки группы")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "admin@admin.com")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Маршрутизация и тарификация SMS клиентов", "Редактирование")
                .tableRowExists("Интерфейс администратора", "admin@admin.com", "Маршрутизация и тарификация клиентов", "Редактирование");
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка наличия действия {0} для платформы ЛК #({index})")
    @MethodSource({"actionListLK", "actionListBoth"})
    @Tag("Log0")
    public void acuiLKNonRequiredAction(String action) {
        Allure.description(
                "1. Зайти на страницу \"Действия пользователей\" \n\n" +
                        "2. Выбрать в качестве платформы Личный Кабинет \n\n" +
                        "3. Ожидается: в фильтре действия нет действия для ИА\n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Пользователь", "WingsRule@example.com")
                .inputClick("Действие")
                .dropDownListOptionExists(action)
        ;
    }


    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка отсутствия логирования действия \"Создание\" на странице {0}:{1} для пользователя #({index})")
    @MethodSource({"subSectionList", "acuiLog"})
    @Tag("Log5")
    public void acuiNoLoggerWithActionC(String section, String page, String alterName) {
        Allure.description("1. Зайти на страницу Настройки логирования действий пользователей\n\n" +
                "2. Отключить логирование действия \"Создание\"\n\n" +
                "3. Зайти на страницу " + page + " и создать объект\n\n" +
                "4. Зайти на страницу \"Действия пользователей\" \n\n" +
                "5. Ожидается: действие \"Создание\" на странице " + page + " отсутствует в таблице \n\n");

        ui
                .logout()
                .loginAcui("areyouserios@black.com", "RandomPass")
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick(section, page)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .clickOn("5 мин")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", "areyouserios@black.com")
                .inputSet("Страница", alterName)
                .buttonClick("a")
                .tableRowNotExists("areyouserios@black.com", alterName, "Создание")
        ;
    }

    @Epic("Действия пользователей")
    @ParameterizedTest(name = "Проверка логирования действия \"{1}\" на странице {0} для пользователя ЛК #({index})")
    @MethodSource({"lkuiLoggerList"})
    @Tag("Log2")
    public void lkuiacuiLogger(String page, String action) {
        Allure.description("1. Зайти на страницу " + page + "\n\n" +
                "2. Выполнить действие " + action + "\n\n" +
                "3. Зайти на страницу \"Действия пользователей\" \n\n" +
                "4. Ожидается: действие " + action + " на странице " + page + " записано в таблице \n\n");

        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Пользователь", "WingsRule@example.com")
                .buttonClick("a")
                .tableRowExists("WingsRule@example.com", page, action)
        ;
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования действия \"Смена пароля\" на странице \"Личные данные\" для пользователя ЛК")
    @Test
    @Tag("Main")
    @Tag("Log5")
    public void lkuiacuiChangePasswordLogger() {

        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Смена пароля")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .logout()

                .loginLkui("WingsRule@example.com", "123456q")
                .subSectionClick("Профиль", "Личные данные")
                .inputSet("Новый пароль", "q123456")
                .inputSet("Подтвердите новый пароль", "q123456")
                .buttonClick("Сохранить")
                .logout()

                .loginAcui()
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Пользователь", "WingsRule@example.com")
                .buttonClick("a")
                .tableRowExists("WingsRule@example.com", "Личные данные", "Смена пароля")
        ;
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования действия \"Авторизация\" на странице \"Cтраница авторизации\" для пользователя ЛК")
    @Test
    @Tag("Main")
    @Tag("Log2")
    public void lkuiacuiAuthorizationLogger() {

        ui
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Авторизация")
                .radioButtonLogOn("Статус", "Разрешено")
                .buttonClick("s")
                .logout()

                .loginLkui("WingsRule@example.com", "123456q")
                .logout()

                .loginAcui()
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Личный кабинет")
                .inputSet("Пользователь", "WingsRule@example.com")
                .buttonClick("a")
                .tableRowExists("WingsRule@example.com", "Страница авторизации", "Авторизация")
        ;
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования Редактирования Маршрутизации клиентов и поставщиков SMS")
    @Test
    @Tag("Log1")
    public void MSG_4650_1() {

        ui
                .subSectionClick("SMS", "Маршрутизация и тарификация клиентов")
                .inputSet("Клиент", "clientLog")
                .tableHrefCellClick("clientLogTariff")
                .buttonClick("s")

                .subSectionClick("SMS", "Маршрутизация и тарификация поставщиков услуг")
                .inputSet("Поставщик услуг", "ProviderSMS")
                .tableHrefCellClick("ProviderSMSTariff")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")

                .tableRowExists("admin@admin.com", "Маршрутизация и тарификация SMS клиентов", "Тарифная зона", "Редактирование")
                .tableRowExists("admin@admin.com", "Маршрутизация и тарификация SMS поставщиков услуг", "Тарифная зона", "Редактирование");
    }

    public static Stream<Arguments> dataForMSG_4650() {
        return Stream.of(
                Arguments.of("Email", "clientModerationBlock", "providerForBlocks"),
                Arguments.of("Viber", "clientModerationBlock", "providerForBlocks"),
                Arguments.of("WhatsApp", "clientModerationBlock", "providerForBlocks"),
                Arguments.of("Mail Notify", "clientModerationBlock", "providerForBlocks"),
                Arguments.of("Custom", "ClientGEditCustom", "ProviderCustom")
        );
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования Редактирования Маршрутизации клиентов и поставщиков Email")
    @ParameterizedTest
    @MethodSource({"dataForMSG_4650"})
    @Tag("Log1")
    public void MSG_4650_2(String transport, String client, String provider) {
        String transport1 = transport.equals("Email") ? "Электронная почта" : transport;

        ui
                .subSectionClick(transport1, "Тарификация " + transport + " клиентов")
                .tableHrefCellClick(client)
                .buttonClick("s")

                .subSectionClick(transport1, "Тарификация " + transport + " поставщиков услуг")
                .tableHrefCellClick(provider)
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")

                .tableRowExists("admin@admin.com", "Тарификация " + transport + " клиентов", "Тариф", "Редактирование")
                .tableRowExists("admin@admin.com", "Тарификация " + transport + " поставщиков услуг", "Тариф", "Редактирование");
    }

    @Epic("Действия пользователей")
    @Description("Проверка логирования Редактирования Тарификации Push клиентов")
    @Test
    @Tag("Log1")
    public void MSG_4650_3() {

        ui
                .subSectionClick("Push", "Тарификация Push клиентов")
                .tableHrefCellClick("clientLog")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Действие", "Редактирование")
                .clickOn("5 мин")
                .buttonClick("Применить")

                .tableRowExists("admin@admin.com", "Тарификация Push клиентов", "Тариф", "Редактирование");
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogProvider(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createProvider("LogProvider" + name, "SMS", "SMPP")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание");

    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogMessageCategories(String name, String login, String password) {
        String[] array = {"SMS"};
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createMessageCategory("logCat" + name, array)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание");
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogClient(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")
                .subSectionClick("Настройки", "Клиенты")
                .deleteIfExists("clientLog" + name)
                .buttonClick("+")
                .inputSet("Название", "clientLog" + name)
                .radioButtonOn("SMS")
                .buttonClick("s")
                .tableRowExists("clientLog" + name)
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание");
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogSettings(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .subSectionClick("Настройки", "Настройки системы")
                .deleteIfExists("LogSett" + name)

                .buttonClick("+")
                .inputSet("Название", "LogSett" + name)
                .inputSet("Значение", "LogSett" + name)
                .inputSet("Описание", "LogSett" + name)
                .buttonClick("s")

                .tableRowExists("LogSett" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogMessageType(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Типы сообщений")
                .deleteIfExists("LogType" + name)
                .buttonClick("+")
                .inputSet("Название", "LogType" + name)
                .inputSet("Описание", "LogType" + name)
                .buttonClick("s")

                .tableRowExists("LogType" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogUserLk(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Управление пользователями", "Пользователи личного кабинета")
                .deleteIfExists("logUser" + name)
                //Создание
                .buttonClick("+")
                .titleExists("Создание нового пользователя")
                //Заполнение данных
                .inputSet("Имя", "logUser" + name)
                .inputSet("Клиент", "Wings")
                .inputSet("Почта", "log@mail.ru")
                .inputSet("Пароль", "1password")

                .buttonClick("Сохранить")

                .tableRowExists("logUser" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void LogSystemUser(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Управление пользователями", "Пользователи системы")
                .deleteIfExists("logSuser" + name)
                //Создание
                .buttonClick("+")
                .titleExists("Создание пользователя системы")
                //Заполнение данных
                .inputSet("Название", "logSuser" + name)
                .inputSet("Электронная почта", "log2@mail.ru")
                .inputSet("Пароль", "1password")
                .inputSet("Подтвердить пароль", "1password")
                .buttonClick("Сохранить")
                .tableRowExists("logSuser" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogSenderAddress(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Адреса отправителей")
                .deleteIfExists("LogSend" + name, "SMS")
                //Заполнение данных
                .buttonClick("+")
                .inputSet("~Адрес", "LogSend" + name)
                .inputSet("Клиент", "Wings")
                .inputSet("Транспорт", "SMS")
                .radioButtonClick("Разрешен")
                .buttonClick("s")
                .tableRowExists("LogSend" + name, "Wings", "SMS")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogAccaunt(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Аккаунты")
                .deleteIfExists("LogAcc" + name)
                //Заполнение данных
                .waiting(3)
                .buttonClick("+")
                .inputSet("Логин аккаунта",login)
                .inputSet("Протокол", "HTTP")
                .inputSet("Клиент", "Wings")
                .inputSet("Имя аккаунта", "LogAcc" + name)
                .inputSet("Пароль", "1password")
                .inputSet("~Подтвердить", "1password")
                .buttonClick("s")
                .tableRowExists("LogAcc" + name, "Wings", "HTTP")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log4")
    public void acuiTestC9364(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Блокировка одинаковых сообщений")
                .titleExists("Блокировка одинаковых сообщений")
                .deleteIfExists("Wings")
                //Заполнение данных
                .buttonClick("+")
                .inputSet("Транспорт", "SMS")
                .inputSet("Клиент", "Wings")
                .buttonClick("s")

                .tableRowExists("Wings", "SMS")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void C18074(String name, String login, String password) {

        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Черные списки")
                .deleteIfExists("LogBL" + name)
                //Создание
                .buttonClick("+")
                .inputSet("Название", "LogBL" + name)
                .inputSet("Клиент", "Wings")
                .inputSet("Пользователь", "wingsUser")
                .inputSet("Комментарий", "descriprion")
                .buttonClick("s")

                .tableRowExists("LogBL" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log4")
    public void acuiTestC9368(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //Категория
                .subSectionClick("Настройки", "Дополнительные правила")
                .deleteIfExists("LogRule" + name)
                .buttonClick("+")

                .inputSet("Правило", "LogRule" + name)
                .inputSet("Положение","Препроцессинг")
                .buttonClick("s")
                .isAlreadyExists()
                .tableRowExists("LogRule" + name)
                .deleteIfExists("LogRule" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .inputSet("Страница","Препроцессинг")
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void LogSMSProviderCreationTariffTest(String name, String login, String password) {

        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .subSectionClick("SMS", "Маршрутизация и тарификация поставщиков услуг")
                .inputSet("Поставщик услуг", "ProviderAll")
                .buttonClick("~Настройки")
                .inputFill("Тарификация")
                .inputFill("Маршрутизация")
                .buttonClick("s")

                .deleteIfExists("ProviderAll" + "-123")
                .buttonClick("+")
                .inputSet("Название", "ProviderAll" + "-123")
                .inputSet("MSISDN", "123")
                .inputSet("MCC", "123")
                .inputSet("MNC", "123")
                .inputSet("~Цена", "0.11")
                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void LogCreationTariffTest(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .subSectionClick("SMS", "Маршрутизация и тарификация клиентов")
                .inputSet("Клиент", "Wings")
                .buttonClick("~Настройки")
                .inputSet("Тарификация","За отправку")
                .inputSet("Маршрутизация","По префиксу MSISDN")
                .buttonClick("s")

                .deleteIfExists("Wings" + "-123")
                .buttonClick("+")
                .inputSet("Название", "Wings" + "-123")
                .inputSet("MSISDN", "123")
                .inputSet("MCC", "123")
                .inputSet("MNC", "123")
                .inputSet("~Цена", "0.11")
                .buttonClick("s")


                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log4")
    public void LogSMSPriorityRules(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createPriorityRules("SMS", "Wings", "13000", "LKPlan", "Normal")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void LogSMSOriginatorRules(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                //.useCompositeStep().createOriginatorRules("SMS", "LogProvider" + name, "Wings", "wSender2", "wSender3")
                .subSectionClick("SMS","Изменение адреса отправителя SMS")
                .deleteIfExists("LogProvider"+name,"Wings","wSender2 -> 098")
                .buttonClick("Создать")
                .inputSet("Поставщик услуг","LogProvider"+name)
                .inputSet("Клиент", "Wings")
                .inputSet("Адрес отправителя","wSender2")
                .inputSet("Новый адрес отправителя","098")
                .buttonClick("Сохранить")
                .tableRowExists("LogProvider"+name,"Wings","wSender2 -> 098")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogSMSFilters(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createSMSFilters("Wings", "Равно", "LogValue" + name)

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogSMSRestriction(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createSMSRestrictions("LogProvider" + name, "Только числовые")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogPartnersTariffsForProvider(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .subSectionClick("SMS","Маршрутизация и тарификация поставщиков услуг")
                .inputSet("Поставщик услуг","LogProvider"+name)
                .buttonClick("Настройки группы")
                .inputSet("Тарификация","За отправку")
                .inputSet("Маршрутизация","По префиксу MSISDN")
                .buttonClick("Сохранить")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log1")
    public void LogPushApp(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createPushApp("logApp" + name, "Wabaj", "aokgdk2", new String[]{"FCM"}, new String[]{"Wings"})

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log4")
    public void LogPattern(String name, String login, String password) {

        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createPattern("SMS", "LogPat + name", "Wings", "Публичный", "wingsUser", "wSender1", "LogMessage")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log4")
    public void patternExtendBlackList(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createTemplate("SMS", "LKPlan" + name, "ClientSMS", "userSMS","SMSSender","checkcheck");
        ui
                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .clickOn("5 мин")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .inputSet("Страница", "Шаблоны SMS")
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log3")
    public void LogBlackListContacts(String name, String login, String password) {

        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createBlackList("ClientWS","WingsTest","BlackList"+name,"BlackList"+name)
                .useCompositeStep().createBlackListContact("BlackList"+name, "913", "log@mail.ru", "Log")

                .subSectionClick("Настройки", "Черные списки")

                .deleteIfExists("LogBLforTest")

                .buttonClick("+")

                .inputSet("Клиент", "Wings")
                .inputSet("Пользователь", "wingsUser")
                .inputSet("Название", "LogBLforTest")

                .buttonClick("s")

                .filterSet("Название", "LogBLforTest")
                .tableCellClick("Количество контактов", "LogBLforTest")
                .confirmIfExists()
                //.deleteIfExists(msisdn,email)
                .buttonClick("+")

                .inputSet("MSISDN", "913")
                .inputSet("Email", "log@mail.ru")
                .inputSet("Комментарий", "Log")

                .buttonClick("s")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogLkuiRoles(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createLkuiRole("logName" + name, "222", new String[]{"Шаблоны"})

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }


    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogRoles(String name, String login, String password) {
        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createRole("logName" + name, "222", "Да", new String[]{"Шаблоны"})

                .subSectionClick("Управление пользователями", "Роли")
                .deleteIfExists("logName")

                .buttonClick("+")
                .inputSet("Название", "logName")
                .inputSet("Группа пользователей Active Directory", "222")
                .radioButtonOn("Показывать финансовую информацию")

                .buttonClick("s")
                .tableRowExists("logName")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Создание записи для проверки действий пользователя {0}")
    @ParameterizedTest
    @MethodSource("usersForCreateLog")
    @Tag("Main")
    @Tag("Log0")
    public void LogGeneralBlackList(String name, String login, String password) {

        ui
                .logout()
                .loginAcui(login, password)
                .subSectionClick("Управление пользователями", "Настройки логирования действий пользователей")
                .tableHrefCellClick("Создание")
                .radioButtonLogOff("Статус", "Разрешено")
                .buttonClick("s")

                .useCompositeStep().createGeneralBlackList("Глобальный", "913", login, "Log")

                .subSectionClick("Управление пользователями", "Действия пользователей")
                .confirmIfExists()
                .inputClick("Дата с")
                .calendarSetDate(-1)
                .calendarSetTime("00:00")
                .inputSet("Интерфейс платформы", "Интерфейс администратора")
                .inputSet("Пользователь", login)
                .buttonClick("a")
                .tableRowNotExists(login, "Создание")
        ;
    }

    public static Stream<Arguments> usersForCreateLog() {
        return Stream.of(
                Arguments.of("Admin", "admin@admin.com", "Admin"),
                Arguments.of("User", "areyouserios@black.com", "RandomPass")
        );
    }

    public static Stream<Arguments> statisticList() {
        return Stream.of(
                Arguments.of("SMS", "Детальная статистика", "Детальная статистика SMS"),
                Arguments.of("SMS", "Групповая статистика", "Групповая статистика SMS"),

                Arguments.of("SMS", "Детальная статистика входящих SMS", "Детальная статистика входящих SMS"),
                Arguments.of("SMS", "Групповая статистика входящих SMS", "Групповая статистика входящих SMS"),
                Arguments.of("Звонки", "Детальная статистика", "Детальная статистика звонков"),
                Arguments.of("Звонки", "Групповая статистика", "Групповая статистика звонков"),
                Arguments.of("Звонки", "Детальная статистика входящих звонков", "Детальная статистика входящих звонков"),
                Arguments.of("Звонки", "Групповая статистика входящих звонков", "Групповая статистика входящих звонков"),

                Arguments.of("Электронная почта", "Детальная статистика", "Детальная статистика Email"),
                Arguments.of("Электронная почта", "Групповая статистика", "Групповая статистика Email"),

                Arguments.of("Push", "Детальная статистика", "Детальная статистика Push"),
                Arguments.of("Push", "Групповая статистика", "Групповая статистика Push"),

                Arguments.of("Viber", "Детальная статистика", "Детальная статистика Viber"),
                Arguments.of("Viber", "Групповая статистика", "Групповая статистика Viber"),
                Arguments.of("Viber", "Детальная статистика входящих Viber", "Детальная статистика входящих Viber"),
                Arguments.of("Viber", "Групповая статистика входящих Viber", "Групповая статистика входящих Viber"),

                Arguments.of("WhatsApp", "Детальная статистика", "Детальная статистика WhatsApp"),
                Arguments.of("WhatsApp", "Групповая статистика", "Групповая статистика WhatsApp"),
                Arguments.of("WhatsApp", "Детальная статистика входящих WhatsApp", "Детальная статистика входящих WhatsApp"),
                Arguments.of("WhatsApp", "Групповая статистика входящих WhatsApp", "Групповая статистика входящих WhatsApp"),

                Arguments.of("Mail Notify", "Детальная статистика", "Детальная статистика Mail Notify"),
                Arguments.of("Mail Notify", "Групповая статистика", "Групповая статистика Mail Notify"),
                Arguments.of("Custom", "Детальная статистика", "Детальная статистика Custom"),
                Arguments.of("Custom", "Групповая статистика", "Групповая статистика Custom"));

    }


    public static Stream<Arguments> subSectionList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Шаблоны SMS"),
                Arguments.of("SMS", "Маршрутизация и тарификация клиентов", "Маршрутизация и тарификация SMS клиентов"),
                Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг", "Маршрутизация и тарификация SMS поставщиков услуг"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Изменение адреса отправителя SMS"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Ограничение адресов отправителей"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Маршрутизация входящих SMS"),

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Изменение адреса отправителя звонков"),

                Arguments.of("Электронная почта", "Шаблоны", "Шаблоны Email"),

                Arguments.of("Push", "Шаблоны", "Шаблоны Push"),
                Arguments.of("Push", "Управление приложениями", "Управление приложениями"),
                Arguments.of("Push", "Атрибуты приложений", "Атрибуты приложений"),

                Arguments.of("Viber", "Шаблоны", "Шаблоны Viber"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Маршрутизация входящих Viber сообщений"),

                Arguments.of("WhatsApp", "Шаблоны", "Шаблоны WhatsApp"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Маршрутизация входящих WhatsApp сообщений"),

                Arguments.of("Mail Notify", "Шаблоны", "Шаблоны Mail Notify"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Операторские сервисы Mail Notify"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Операторские шаблоны Mail Notify"),

                Arguments.of("Custom", "Шаблоны", "Шаблоны Custom"),

                Arguments.of("Настройки", "Поставщики услуг", "Поставщики услуг"),
                Arguments.of("Настройки", "Клиенты", "Клиенты"),
                Arguments.of("Настройки", "Аккаунты", "Аккаунты"),
                Arguments.of("Настройки", "Адреса отправителей", "Адреса отправителей"),
                Arguments.of("Настройки", "Настройки системы", "Настройки системы"),
                Arguments.of("Настройки", "Отчеты по экспорту", "Отчеты по экспорту"),
                Arguments.of("Настройки", "Типы сообщений", "Типы сообщений"),
                Arguments.of("Настройки", "Категории сообщений", "Категории сообщений"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Блокировка одинаковых сообщений"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Ограничение количества отправляемых сообщений"),
                Arguments.of("Настройки", "Подключения поставщиков услуг", "Подключения поставщиков услуг"),
                Arguments.of("Настройки", "Подключения клиентов", "Подключения клиентов"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Подключения к операторам по SMPP"),
                Arguments.of("Настройки", "Дополнительные правила", "Препроцессинг"),
                Arguments.of("Настройки", "Черные списки", "Черные списки"),
                Arguments.of("Настройки", "Белый список", "Белый список"),
                Arguments.of("Настройки", "Изменение приоритета", "Изменение приоритета"),
                Arguments.of("Настройки", "Блокировка по тексту", "Блокировка по тексту"),

                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Пользователи личного кабинета"),
                Arguments.of("Управление пользователями", "Пользователи колл-центра", "Пользователи колл-центра"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Пользователи системы"),
                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Роли пользователей личного кабинета"),
                Arguments.of("Управление пользователями", "Роли", "Роли"),

                Arguments.of("Управление финансами", "Управление балансами", "Управление балансами"),
                Arguments.of("Управление финансами", "История пополнений", "История пополнений")
        );
    }

    public static Stream<Arguments> listForCreate() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Шаблоны SMS"),

                Arguments.of("SMS", "Маршрутизация и тарификация клиентов", "Маршрутизация и тарификация SMS клиентов"),
                Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг", "Маршрутизация и тарификация SMS поставщиков услуг"),
                Arguments.of("SMS", "Изменение приоритета SMS", "Изменение приоритета SMS"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Изменение адреса отправителя SMS"),
                Arguments.of("SMS", "Блокировка по тексту SMS", "Блокировка по тексту SMS"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Ограничение адресов отправителей"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Маршрутизация входящих SMS"),

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Изменение адреса отправителя звонков"),
                Arguments.of("Электронная почта", "Шаблоны", "Шаблоны Email"),

                Arguments.of("Push", "Шаблоны", "Шаблоны Push"),

                Arguments.of("Push", "Управление приложениями", "Управление приложениями"),
                Arguments.of("Viber", "Шаблоны", "Шаблоны Viber"),

                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Маршрутизация входящих Viber сообщений"),

                Arguments.of("WhatsApp", "Шаблоны", "Шаблоны WhatsApp"),

                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Маршрутизация входящих WhatsApp сообщений"),

                Arguments.of("Mail Notify", "Шаблоны", "Шаблоны Mail Notify"),

                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Операторские сервисы Mail Notify"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Операторские шаблоны Mail Notify"),
                Arguments.of("Custom", "Шаблоны", "Шаблоны Custom"),

                Arguments.of("Настройки", "Поставщики услуг", "Поставщики услуг"),
                Arguments.of("Настройки", "Клиенты", "Клиенты"),
                Arguments.of("Настройки", "Аккаунты", "Аккаунты"),
                Arguments.of("Настройки", "Адреса отправителей", "Адреса отправителей"),
                Arguments.of("Настройки", "Настройки системы", "Настройки системы"),
                Arguments.of("Настройки", "Типы сообщений", "Типы сообщений"),
                Arguments.of("Настройки", "Категории сообщений", "Категории сообщений"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Блокировка одинаковых сообщений"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Ограничение количества отправляемых сообщений"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Подключения к операторам по SMPP"),
                Arguments.of("Настройки", "Дополнительные правила", "Препроцессинг"),
                Arguments.of("Настройки", "Черные списки", "Черные списки"),
                Arguments.of("Настройки", "Общий чёрный список", "Общие чёрные списки"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Пользователи личного кабинета"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Пользователи системы"),
                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Роли пользователей личного кабинета"),
                Arguments.of("Управление пользователями", "Роли", "Роли"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Тарификация Email клиентов"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Тарификация Email поставщиков услуг"),

                Arguments.of("Push", "Атрибуты приложений", "Атрибуты приложений"),
                Arguments.of("Push", "Тарификация Push клиентов", "Тарификация Push клиентов"),

                Arguments.of("Viber", "Тарификация Viber клиентов", "Тарификация Viber клиентов"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Тарификация Viber поставщиков услуг"),

                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Тарификация WhatsApp клиентов"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Тарификация WhatsApp поставщиков услуг"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Тарификация Mail Notify клиентов"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Тарификация Mail Notify поставщиков услуг"),

                Arguments.of("Custom", "Тарификация Custom клиентов", "Тарификация Custom клиентов"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Тарификация Custom поставщиков услуг")
        );
    }

    public static Stream<Arguments> onlyView() {
        return Stream.of(
                Arguments.of("Управление пользователями", "Модерация рассылок", "Модерация рассылок"),
                Arguments.of("Управление пользователями", "Модерация шаблонов", "Модерация шаблонов"),
                Arguments.of("Настройки", "Общие черные списки", "Общие чёрные списки"),
                Arguments.of("Настройки", "Прокси-серверы", "Прокси серверы")
        );
    }

    public static Stream<Arguments> onlySettings() {
        return Stream.of(
                Arguments.of("Управление пользователями", "Действия пользователей", "Действия пользователей"),
                Arguments.of("Управление пользователями", "Настройки логирования действий пользователей", "Настройки логирования действий пользователей")
        );
    }

    public static Stream<Arguments> acuiLog() {
        return Stream.of(
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Тарификация Email клиентов"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Тарификация Email поставщиков услуг"),

                Arguments.of("Push", "Атрибуты приложений", "Атрибуты приложений"),
                Arguments.of("Push", "Количество подписчиков", "Количество подписчиков"),
                Arguments.of("Push", "История подписок", "История подписок"),
                Arguments.of("Push", "Тарификация Push клиентов", "Тарификация Push клиентов"),

                Arguments.of("Viber", "Тарификация Viber клиентов", "Тарификация Viber клиентов"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Тарификация Viber поставщиков услуг"),

                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Тарификация WhatsApp клиентов"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Тарификация WhatsApp поставщиков услуг"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Тарификация Mail Notify клиентов"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Тарификация Mail Notify поставщиков услуг"),

                Arguments.of("Custom", "Тарификация Custom клиентов", "Тарификация Custom клиентов"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Тарификация Custom поставщиков услуг")
        );
    }

    public static Stream<Arguments> acuiLoggerList() {
        return Stream.of(
                Arguments.of("Панель администратора", "Удаление"),
                Arguments.of("Панель администратора", "Создание"),
                Arguments.of("Панель администратора", "Просмотр"),
                Arguments.of("Панель администратора", "Редактирование"),
//                Arguments.of("Страница авторизации","Авторизация"),
                Arguments.of("Пользователи системы", "Смена пароля"),
                Arguments.of("Шаблоны Viber", "Экспорт"),
                Arguments.of("Шаблоны WhatsApp", "Экспорт"),
                Arguments.of("Шаблоны Push", "Экспорт"),
                Arguments.of("Шаблоны Custom", "Экспорт"),
                Arguments.of("Шаблоны Email", "Экспорт"),
                Arguments.of("Шаблоны Mail Notify", "Экспорт"),
                Arguments.of("Шаблоны звонков", "Экспорт"),
                Arguments.of("Детальная статистика звонков", "Экспорт"),
                Arguments.of("Групповая статистика звонков", "Экспорт"),
                Arguments.of("Детальная статистика SMS", "Экспорт"),
                Arguments.of("Групповая статистика SMS", "Экспорт"),
                Arguments.of("Детальная статистика Email", "Экспорт"),
                Arguments.of("Детальная статистика Email", "Переотправка"),
                Arguments.of("Групповая статистика Email", "Экспорт"),
                Arguments.of("Детальная статистика Push", "Переотправка"),
                Arguments.of("Групповая статистика Push", "Экспорт"),
                Arguments.of("Детальная статистика Viber", "Экспорт"),
                Arguments.of("Детальная статистика Viber", "Переотправка"),
                Arguments.of("Групповая статистика Viber", "Экспорт"),
                Arguments.of("Детальная статистика WhatsApp", "Экспорт"),
                Arguments.of("Детальная статистика WhatsApp", "Переотправка"),
                Arguments.of("Групповая статистика WhatsApp", "Экспорт"),
                Arguments.of("Детальная статистика Custom", "Экспорт"),
                Arguments.of("Детальная статистика Custom", "Переотправка"),
                Arguments.of("Групповая статистика Custom", "Экспорт"),
                Arguments.of("Детальная статистика Mail Notify", "Экспорт"),
                Arguments.of("Детальная статистика Mail Notify", "Переотправка"),
                Arguments.of("Групповая статистика Mail Notify", "Экспорт"),
                Arguments.of("Маршрутизация и тарификация SMS клиентов", "Импорт"),
                Arguments.of("Маршрутизация и тарификация SMS поставщиков услуг", "Импорт"),
                Arguments.of("Поставщики услуг", "Экспорт"),
                Arguments.of("Клиенты", "Экспорт"),
                Arguments.of("Аккаунты", "Экспорт"),
                Arguments.of("Адреса отправителей", "Экспорт"),
                Arguments.of("Отчеты по экспорту", "Скачивание"),
                Arguments.of("Пользователи личного кабинета", "смена пароля"),
                Arguments.of("Пользователи личного кабинета", "Изменение прав доступа"),
                Arguments.of("Роли", "Изменение прав доступа"),
                Arguments.of("Пользователи личного кабинета", "Экспорт"),
                Arguments.of("Пользователи системы", "Экспорт"),
                Arguments.of("Роли пользователей личного кабинета", "Экспорт"),
                Arguments.of("Роли", "Экспорт"),
                Arguments.of("Модерация рассылок", "Модерация"),
                Arguments.of("Действия пользователей", "Экспорт"),
                Arguments.of("Управление балансами", "Пополнение баланса")

        );
    }

    public static Stream<Arguments> loggerEditList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Шаблоны SMS"),
                Arguments.of("SMS", "Маршрутизация и тарификация клиентов", "Маршрутизация и тарификация SMS клиентов"),
                Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг", "Маршрутизация и тарификация SMS поставщиков услуг"),
                Arguments.of("SMS", "Изменение приоритета SMS", "Изменение приоритета SMS"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Изменение адреса отправителя SMS"),
                Arguments.of("SMS", "Блокировка по тексту SMS", "Блокировка по тексту SMS"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Ограничение адресов отправителей"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Маршрутизация входящих SMS"),

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Изменение адреса отправителя звонков"),

                Arguments.of("Электронная почта", "Шаблоны", "Шаблоны Email"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Тарификация Email клиентов"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Тарификация Email поставщиков услуг"),

                Arguments.of("Push", "Шаблоны", "Шаблоны Push"),
                Arguments.of("Push", "Управление приложениями", "Управление приложениями"),
                Arguments.of("Push", "Атрибуты приложений", "Атрибуты приложений"),
                Arguments.of("Push", "Тарификация Push клиентов", "Тарификация Push клиентов"),

                Arguments.of("Viber", "Шаблоны", "Шаблоны Viber"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Маршрутизация входящих Viber сообщений"),
                Arguments.of("Viber", "Тарификация Viber клиентов", "Тарификация Viber клиентов"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Тарификация Viber поставщиков услуг"),

                Arguments.of("WhatsApp", "Шаблоны", "Шаблоны WhatsApp"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Маршрутизация входящих WhatsApp сообщений"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Тарификация WhatsApp клиентов"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Тарификация WhatsApp поставщиков услуг"),

                Arguments.of("Mail Notify", "Шаблоны", "Шаблоны Mail Notify"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Операторские сервисы Mail Notify"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Операторские шаблоны Mail Notify"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Тарификация Mail Notify клиентов"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Тарификация Mail Notify поставщиков услуг"),

                Arguments.of("Custom", "Шаблоны", "Шаблоны Custom"),
                Arguments.of("Custom", "Тарификация Custom клиентов", "Тарификация Custom клиентов"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Тарификация Custom поставщиков услуг"),

                Arguments.of("Настройки", "Поставщики услуг", "Поставщики услуг"),
                Arguments.of("Настройки", "Клиенты", "Клиенты"),
                Arguments.of("Настройки", "Аккаунты", "Аккаунты"),
                Arguments.of("Настройки", "Адреса отправителей", "Адреса отправителей"),
                Arguments.of("Настройки", "Настройки системы", "Настройки системы"),
                Arguments.of("Настройки", "Типы сообщений", "Типы сообщений"),
                Arguments.of("Настройки", "Категории сообщений", "Категории сообщений"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Блокировка одинаковых сообщений"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Ограничение количества отправляемых сообщений"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Подключения к операторам по SMPP"),
                Arguments.of("Настройки", "Дополнительные правила", "Препроцессинг"),
                Arguments.of("Настройки", "Черные списки", "Черные списки"),
                Arguments.of("Настройки", "Общие черные списки", "Общие чёрные списки"),

                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Пользователи личного кабинета"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Пользователи системы"),
                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Роли пользователей личного кабинета"),
                Arguments.of("Управление пользователями", "Роли", "Роли"),
                Arguments.of("Управление пользователями", "Настройки логирования действий пользователей", "Настройки логирования действий пользователей"),

                Arguments.of("Управление финансами", "Управление балансами", "Управление балансами")
        );
    }

    public static Stream<Arguments> loggerDeleteList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Шаблоны SMS", "patternLog", "Название"),
                Arguments.of("SMS", "Изменение приоритета SMS", "Изменение приоритета SMS", "clientLog", "Клиент"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Изменение адреса отправителя SMS", "clientLog", "Клиент"),
                Arguments.of("SMS", "Блокировка по тексту SMS", "Блокировка по тексту SMS", "clientLog", "Клиент"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Ограничение адресов отправителей", "blockTempMod", "Поставщик услуг"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Маршрутизация входящих SMS", "clientLog", "Клиент"),

                Arguments.of("Звонки", "Шаблоны", "Шаблоны Call", "patternLog", "Название"),
                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Изменение адреса отправителя звонков", "clientLog", "Клиент"),

                Arguments.of("Электронная почта", "Шаблоны", "Шаблоны Email", "patternLog", "Название"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Тарификация Email клиентов", "clientLog", "Клиент"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Тарификация Email поставщиков услуг", "providerLog", "Поставщик услуг"),

                Arguments.of("Push", "Шаблоны", "Шаблоны Push", "patternLog", "Название"),
                Arguments.of("Push", "Управление приложениями", "Управление приложениями", "LogApp", "Название"),
                Arguments.of("Push", "Атрибуты приложений", "Атрибуты приложений", "Log", "Название"),
                Arguments.of("Push", "Тарификация Push клиентов", "Тарификация Push клиентов", "clientLog", "Клиент"),

                Arguments.of("Viber", "Шаблоны", "Шаблоны Viber", "patternLog", "Название"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Маршрутизация входящих Viber сообщений", "clientLog", "Клиент"),
                Arguments.of("Viber", "Тарификация Viber клиентов", "Тарификация Viber клиентов", "clientLog", "Клиент"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Тарификация Viber поставщиков услуг", "providerLog", "Поставщик услуг"),

                Arguments.of("WhatsApp", "Шаблоны", "Шаблоны WhatsApp", "patternLog", "Название"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Маршрутизация входящих WhatsApp сообщений", "clientLog", "Клиент"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Тарификация WhatsApp клиентов", "clientLog", "Клиент"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Тарификация WhatsApp поставщиков услуг", "providerLog", "Поставщик услуг"),

                Arguments.of("Mail Notify", "Шаблоны", "Шаблоны Mail Notify", "patternLog", "Название"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Операторские сервисы Mail Notify", "clientLog", "Клиент"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Операторские шаблоны Mail Notify", "providerLog", "Поставщик услуг"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Тарификация Mail Notify клиентов", "clientLog", "Клиент"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Тарификация Mail Notify поставщиков услуг", "providerLog", "Поставщик услуг"),

                Arguments.of("Custom", "Шаблоны", "Шаблоны Custom", "patternLog", "Название"),
                Arguments.of("Custom", "Тарификация Custom клиентов", "Тарификация Custom клиентов", "clientLog", "Клиент"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Тарификация Custom поставщиков услуг", "providerLog", "Поставщик услуг"),

                Arguments.of("Настройки", "Поставщики услуг", "Поставщики услуг", "providerLog", "Название"),
                Arguments.of("Настройки", "Клиенты", "Клиенты", "clientLog", "Название"),
                Arguments.of("Настройки", "Аккаунты", "Аккаунты", "accLogSMS", "Имя аккаунта"),
                Arguments.of("Настройки", "Адреса отправителей", "Адреса отправителей", "111", "Адрес отправителя"),
                Arguments.of("Настройки", "Настройки системы", "Настройки системы", "SysLog", "Название"),
                Arguments.of("Настройки", "Типы сообщений", "Типы сообщений", "typeLog", "Название"),
                Arguments.of("Настройки", "Категории сообщений", "Категории сообщений", "catLog", "Название"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Блокировка одинаковых сообщений", "clientSameMessageBlock", "Клиент"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Ограничение количества отправляемых сообщений", "typeLog", "Тип сообщения"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Подключения к операторам по SMPP", "LogOpsSMPP", "Название"),
                Arguments.of("Настройки", "Дополнительные правила", "Препроцессинг", "ruleLogSMS", "Правило"),
                Arguments.of("Настройки", "Черные списки", "Черные списки", "BlacklistLog", "Название"),
                Arguments.of("Настройки", "Общий чёрный список", "Общий чёрный список", "Log@test.ru", "Email"),

                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Пользователи личного кабинета", "userLog", "Имя"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Пользователи системы", "UserLog", "Название"),
                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Роли пользователей личного кабинета", "LogRoleLK", "Название"),
                Arguments.of("Управление пользователями", "Роли", "Роли", "LogRole", "Название")
        );
    }

    public static Stream<Arguments> lkuiLoggerListView() {
        return Stream.of(
                Arguments.of("Адреса отправителей", "Просмотр"),
                Arguments.of("Группы контактов", "Просмотр"),
                Arguments.of("Контакты", "Просмотр"),
                Arguments.of("Черные списки", "Просмотр"),
                Arguments.of("Контакты ЧС", "Просмотр"),
                Arguments.of("Шаблоны", "Просмотр"),
                Arguments.of("Рассылки", "Просмотр"),
                Arguments.of("Детальная статистика", "Просмотр"),
                Arguments.of("Групповая статистика", "Просмотр"),
                Arguments.of("Общий групповой отчёт", "Просмотр"),
                Arguments.of("Отчеты по экспорту", "Просмотр")
        );
    }

    public static Stream<Arguments> lkuiLoggerListCreate() {
        return Stream.of(
                Arguments.of("Адреса отправителей", "Создание"),
                Arguments.of("Группы контактов", "Создание"),
                Arguments.of("Контакты", "Создание"),
                Arguments.of("Черные списки", "Создание"),
                Arguments.of("Контакты ЧС", "Создание")
        );
    }

    public static Stream<Arguments> lkuiLoggerListEdit() {
        return Stream.of(
                Arguments.of("Личные данные", "Редактирование"),
                Arguments.of("Группы контактов", "Редактирование"),
                Arguments.of("Контакты", "Редактирование"),
                Arguments.of("Черные списки", "Редактирование"),
                Arguments.of("Контакты ЧС", "Редактирование")
        );
    }

    public static Stream<Arguments> lkuiLoggerListDelete() {
        return Stream.of(
                Arguments.of("Адреса отправителей", "Удаление"),
                Arguments.of("Группы контактов", "Удаление"),
                Arguments.of("Контакты", "Удаление"),
                Arguments.of("Контакты ЧС", "Удаление"),
                Arguments.of("Черные списки", "Удаление")
        );
    }

    public static Stream<Arguments> lkuiLoggerListExport() {
        return Stream.of(
                Arguments.of("Контакты", "Экспорт"),
                Arguments.of("Контакты ЧС", "Экспорт"),
                Arguments.of("Детальная статистика", "Экспорт"),
                Arguments.of("Групповая статистика", "Экспорт"),
                Arguments.of("Общий групповой отчёт", "Экспорт"),
                Arguments.of("Отчеты по экспорту", "Скачивание")
        );
    }

    public static Stream<Arguments> lkuiLoggerListImport() {
        return Stream.of(
                Arguments.of("Контакты", "Импорт"),
                Arguments.of("Контакты ЧС", "Импорт")
        );
    }

    public static Stream<Arguments> lkuiLoggerListMailingStart() {
        return Stream.of(
                Arguments.of("Рассылки", "Запуск/Остановка"),
                Arguments.of("Создание рассылки", "Запуск/Остановка")
        );
    }

    public static Stream<Arguments> lkuiLoggerListCopy() {
        return Stream.of(
                Arguments.of("Контакты", "Копирование"),
                Arguments.of("Контакты ЧС", "Копирование")
        );
    }

    public static Stream<Arguments> lkuiLoggerListMove() {
        return Stream.of(
                Arguments.of("Контакты", "Перемещение"),
                Arguments.of("Контакты ЧС", "Перемещение")
        );
    }

    public static Stream<Arguments> lkuiLoggerList() {
        return Stream.of(
                //LK
//                Arguments.of("Аутентификация","Смена пароля"),
//                Arguments.of("Стартовая страница","Переход на страницу"),
//                Arguments.of("Личные данные","Переход на страницу"),
//                Arguments.of("Адреса отправителей","Переход на страницу"),
//                Arguments.of("Группы контактов","Переход на страницу"),
//                Arguments.of("Черные списки","Переход на страницу"),
//                Arguments.of("Шаблоны","Переход на страницу"),
//                Arguments.of("Рассылки","Переход на страницу"),
//                Arguments.of("Детальная статистика","Переход на страницу"),
//                Arguments.of("Групповая статистика","Переход на страницу"),
//                Arguments.of("Общий групповой отчёт","Переход на страницу"),
//                Arguments.of("Отчеты по экспорту","Переход на страницу")
        );
    }

    public static Stream<Arguments> actionListBoth() {
        return Stream.of(
                Arguments.of("Создание"),
                Arguments.of("Редактирование"),
                Arguments.of("Удаление"),
                Arguments.of("Просмотр"),
                Arguments.of("Авторизация"),
                Arguments.of("Смена пароля"),
                Arguments.of("Импорт"),
                Arguments.of("Экспорт"),
                Arguments.of("Скачивание отчёта")
        );
    }

    public static Stream<Arguments> actionListADM() {
        return Stream.of(
                Arguments.of("Переотправка"),
                Arguments.of("Модерация"),
                Arguments.of("Пополнение баланса"),
                Arguments.of("Синхронизация"),
                Arguments.of("Изменение прав доступа")
        );
    }

    public static Stream<Arguments> actionListLK() {
        return Stream.of(
                Arguments.of("Отправка рассылки"),
                Arguments.of("Запуск рассылки"),
                Arguments.of("Остановка рассылки"),
                Arguments.of("Копирование контактов"),
                Arguments.of("Перемещение контактов")
        );
    }

    public static Stream<Arguments> objectAcuiList() {
        return Stream.of(
                Arguments.of("Группа тарифных зон"),
                Arguments.of("Тарифная зона"),
                Arguments.of("Тариф"),
                Arguments.of("Детальная статистика"),
//                Arguments.of("Сообщение"),
                Arguments.of("Групповая статистика"),
                Arguments.of("Шаблон"),
//                Arguments.of("Черновик шаблона"),
                Arguments.of("Push-приложение"),
                Arguments.of("Атрибут Push-приложения"),
//                Arguments.of("Подписчики"),
//                Arguments.of("Подписки"),
                Arguments.of("Правило изменения приоритета отправки"),
//                Arguments.of("Правило установки канала отправки"),
                Arguments.of("Правило изменения адреса отправителя"),
                Arguments.of("Правило блокировки по тексту"),
//                Arguments.of("Детальная статистика по входящим"),
//                Arguments.of("Входящее сообщение"),
//                Arguments.of("События входящего звонка"),
                Arguments.of("Групповая статистика по входящим"),
                Arguments.of("Правило маршрутизации входящих сообщений"),
                Arguments.of("Правило ограничения адресов отправителей"),
                Arguments.of("Правило ограничения количества отправляемых сообщений"),
                Arguments.of("Поставщик услуг"),
                Arguments.of("Клиент"),
                Arguments.of("Аккаунт"),
                Arguments.of("Адрес отправителя"),
                Arguments.of("Системная настройка"),
                Arguments.of("Отчёт по экспорту"),
                Arguments.of("Подключение поставщика услуг"),
                Arguments.of("Подключение клиента"),
                Arguments.of("SMPP-подключение"),
                Arguments.of("Тип сообщения"),
                Arguments.of("Категория сообщения"),
//                Arguments.of("Регулярное выражение"),
                Arguments.of("Правило препроцессинга"),
//                Arguments.of("Правило блокировки дубликатов"),
                Arguments.of("Чёрный список"),
                Arguments.of("Пользователь личного кабинета"),
                Arguments.of("Роль пользователя личного кабинета"),
                Arguments.of("Пользователь системы"),
                Arguments.of("Роль пользователя системы"),
                Arguments.of("Действие пользователя"),
                Arguments.of("Настройка логирования"),
//                Arguments.of("Рассылка"),
                Arguments.of("Баланс"),
                Arguments.of("История пополнений"),
//                Arguments.of("Порог отключения"),
//                Arguments.of("Интерфейс Платформы"),
                Arguments.of("Страница"),
                Arguments.of("Кэш"),
                Arguments.of("Шаблон Mail Notify"),
                Arguments.of("Сервис Mail Notify"),
                Arguments.of("Адресная книга"),
                Arguments.of("Контакт")
//                Arguments.of("Виджет")
        );
    }

    public static Stream<Arguments> objectAllAction() {
        return Stream.of(
                Arguments.of("Группа тарифных зон"),
                Arguments.of("Тарифная зона"),
                Arguments.of("Тариф"),
//                Arguments.of("Сообщение"),
                Arguments.of("Шаблон"),
                Arguments.of("Push-приложение"),
                Arguments.of("Атрибут Push-приложения"),
                Arguments.of("Правило изменения приоритета отправки"),
//                Arguments.of("Правило установки канала отправки"),
                Arguments.of("Правило изменения адреса отправителя"),
                Arguments.of("Правило блокировки по тексту"),
                Arguments.of("Правило маршрутизации входящих сообщений"),
                Arguments.of("Правило ограничения адресов отправителей"),
                Arguments.of("Правило ограничения количества отправляемых сообщений"),
                Arguments.of("Поставщик услуг"),
                Arguments.of("Клиент"),
                Arguments.of("Аккаунт"),
                Arguments.of("Адрес отправителя"),
                Arguments.of("Системная настройка"),
                Arguments.of("Подключение поставщика услуг"),
                Arguments.of("Подключение клиента"),
                Arguments.of("SMPP-подключение"),
                Arguments.of("Тип сообщения"),
                Arguments.of("Категория сообщения"),
//                Arguments.of("Регулярное выражение"),
                Arguments.of("Правило препроцессинга"),
//                Arguments.of("Правило блокировки дубликатов"),
                Arguments.of("Чёрный список"),
                Arguments.of("Пользователь личного кабинета"),
                Arguments.of("Роль пользователя личного кабинета"),
                Arguments.of("Пользователь системы"),
                Arguments.of("Роль пользователя системы"),
                Arguments.of("Действие пользователя"),
                Arguments.of("Настройка логирования"),
//                Arguments.of("Рассылка"),
//                Arguments.of("Порог отключения"),
//                Arguments.of("Интерфейс Платформы"),
                // Arguments.of("Кэш"), //что за зверь?
                Arguments.of("Шаблон Mail Notify"),
                Arguments.of("Сервис Mail Notify"),
                Arguments.of("Адресная книга"),
                Arguments.of("Контакт")
//                Arguments.of("Виджет")
        );
    }

    public static Stream<Arguments> objectBalance() {
        return Stream.of(
                Arguments.of("Баланс") // Редакт и поплнение баланса
        );
    }


    public static Stream<Arguments> objectOnlyCreate() {
        return Stream.of(
                Arguments.of("Черновик шаблона") //Только создание
        );
    }

    public static Stream<Arguments> objectStatList() {
        return Stream.of(
                Arguments.of("Детальная статистика"), //Статистика
                Arguments.of("Групповая статистика"), //Статистика
//                Arguments.of("Подписчики"), // Статистика
//                Arguments.of("Подписки"), // Статистика
//                Arguments.of("Детальная статистика по входящим"), // Статистика
//                Arguments.of("Входящее сообщение"), // Статистика
//                Arguments.of("События входящего звонка"), // Статистика
                Arguments.of("Групповая статистика по входящим"), // Статистика
                Arguments.of("История пополнений"), // Статистика
                Arguments.of("Отчёт по экспорту"), // Статистика
                Arguments.of("Страница") // Статистика
        );
    }
}
