package core;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;

@Execution(CONCURRENT)
public class editTests {
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editProvider] Редактирование Поставщика услуг")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editProviderList")
    @Tag("edit3")
    public void editProvider(Map<String, String> values) {

        if (values.get("Атрибуты").equals("-")) ui.useCompositeStep().editProvider(values.get("Поставщик"), values.get("Транспорт"), values.get("Способ доставки"));
        else ui.useCompositeStep().editProvider(values.get("Поставщик"), values.get("Транспорт"), values.get("Способ доставки"), values.get("Атрибуты"));
        //XMLHandler.instance().replaceCell("Поставщики услуг", );
    }

    public static Stream<?> editProviderList() {
        return XMLHandler.instance().getRows("Поставщики услуг").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    
    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editMessageCategories] Редактирование Категории сообщений")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editMessageCategoriesList")
    @Tag("edit0")
    public void editMessageCategories(Map<String, String> values) {
        String name = values.get("Название");
        String[] transports = values.get("Транспорт").split(",");
        String[] defaultTransports = values.get("Категория по умолчанию").split(",");
        ui.subSectionClick("Настройки", "Поставщики услуг");
        if (defaultTransports[0].equals("Нет"))
            ui.useCompositeStep().editMessageCategory(name, transports);
        else
            ui.useCompositeStep().editMessageCategory(name, transports, defaultTransports);
    }

    public static Stream<?> editMessageCategoriesList() {
        return XMLHandler.instance().getRows("Категории сообщений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editClient] Редактирование клиента")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editClientList")
    @Tag("edit2")
    public void editClient(Map<String, String> values) {
        String client = values.get("Клиент");
        String transport = values.get("Транспорт");

        ui

                //Категория
                .subSectionClick("Настройки", "Клиенты")

                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet("Название", client)
                .buttonClick("a")

                .tableHrefCellClick(client)
                //Редактирование
                
                .titleExists("Редактирование клиента");
                //.inputSet("Название",client)

                //Заполнение данных
                //.useCompositeStep().radioButtonToggle(values.get("Транспорт"));

//        if (values.get("Статус").equals("Активный")) ui.radioButtonOn("Статус");
        if (values.get("Авансовая схема взаиморасчетов").equals("Да")) ui.radioButtonOn("Авансовая схема взаиморасчетов");
        if (values.get("Использовать мультиподпись?").equals("Да")) ui.useCompositeStep().radioButtonToggle(transport, "Мультиподпись");
        if (values.get("Только шаблон").equals("Да")) ui.useCompositeStep().radioButtonToggle(transport, "Только шаблон");
        if (values.get("Модерация").equals("Да")) ui.useCompositeStep().radioButtonToggle(transport, "Модерация");

        ui
                .buttonClick("s")
                .tableRowExists(client)
                .buttonClick("Очистить фильтры")
        ;
    }

    public static Stream<?> editClientList() {
        return XMLHandler.instance().getRows("Клиенты").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editSettings] Настройки системы. Редактирование")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSettingsList")
    @Tag("edit0")
    public void editSettings(Map<String, String> values) {
        String name = values.get("Название"),
                value = values.get("Значение"),
                descriprion = values.get("Значение");
        ui

                //Категория
                .subSectionClick("Настройки", "Настройки системы")
                .tableHrefCellClick(name)
                //Редактирование
                .inputSet("Название", name)
                .inputSet("Значение", value)
                .inputSet("Описание", descriprion)
                .buttonClick("s")

                .isAlreadyExists()
                .tableRowExists(name)
        ;
    }

    public static Stream<?> editSettingsList() {
        return XMLHandler.instance().getRows("Настройки системы").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editMessageType] Типы сообщений. Редактирование")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editMessageTypeList")
    @Tag("edit0")
    public void editMessageType(Map<String, String> values) {
        String name = values.get("Название"),
                description = values.get("Описание");
        ui

                //Категория
                .subSectionClick("Настройки", "Типы сообщений")
                .tableHrefCellClick(name)
        
                .inputSet("Название", name)
                .inputSet("Описание", description)
                .buttonClick("s")

                .tableRowExists(name)
        ;
    }

    public static Stream<?> editMessageTypeList() {
        return XMLHandler.instance().getRows("Типы сообщений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Управление пользователями\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editUserLk] Редактирование пользователя лк")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editUserLkList")
    @Tag("edit1")
    public void editUserLk(Map<String, String> values) {
        String name = values.get("Имя"),
                clientName = values.get("Клиент"),
                email = values.get("Почта"),
                password = "123qwe",
                status = values.get("Статус"),
                timeZone = values.get("Часовой пояс"),
                visibleTexts = values.get("Отображать тексты сообщений"),
                changePasswordOnNextAuth = values.get("Сменить пароль при следующей авторизации"),
                useRole = values.get("Использовать роль");
        String[] allCheckBoxes = {"Шаблоны", "Рассылки", "Рассылки", "Рассылки", "Детальная статистика", "Групповая статистика", "Отчеты по экспорту"};
        String[] checkBoxList = values.get("Разделы").split(",");

        ui

                //Категория
                .subSectionClick("Управление пользователями", "Пользователи личного кабинета")

                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet("~Название", name)
                .buttonClick("a")

                .tableHrefCellClick(name, clientName)
                //Редактирование
           
                .titleExists("Редактирование пользователя")
                //Заполнение данных
                .inputSet("Имя", name)
                .inputSet("Клиент", clientName)
                .inputSet("Часовой пояс", timeZone)
                .inputSet("Почта", email)
                .inputSet("Пароль", password);

        if (status.equals("Активный")) ui.radioButtonClick("Статус");
        if (visibleTexts.equals("Нет")) ui.radioButtonClick("Отображать тексты сообщений");
        if (changePasswordOnNextAuth.equals("Да")) ui.radioButtonClick("Сменить пароль при следующей авторизации");
        if (useRole.equals("Нет") && !checkBoxList[0].equals("-")) {
            ui
                    .radioButtonOff("Использовать роль")
                    .useCompositeStep().checkBoxOff(allCheckBoxes)
                    .useCompositeStep().checkBoxOn(checkBoxList)
            ;
        }
        else {
            ui.radioButtonOn("Использовать роль");
        }
        ui
                .buttonClick("Сохранить")

                .tableRowExists(name)
                .buttonClick("Очистить фильтры")

        ;
    }

    public static Stream<?> editUserLkList() {
        return XMLHandler.instance().getRows("Пользователи личного кабинета").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Управление пользователями\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editUserLk] Редактирование пользователя системы")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSystemUserList")
    @Tag("edit3")
    public void editSystemUser(Map<String, String> values) {
        String name = values.get("Название"),
                email = values.get("Электронная почта"),
                password = "123qwe",
                status = values.get("Статус"),
                finance = values.get("Показывать финансовую информацию"),
                changePasswordOnNextAuth = values.get("Сменить пароль при следующей авторизации"),
                useRole = values.get("Использовать роль");
        String[] checkBoxList = values.get("Разделы").split(",");
        String[] allCheckBoxes = {"Шаблоны", "Рассылки", "Рассылки", "Рассылки", "Детальная статистика", "Групповая статистика", "Отчеты по экспорту"};
        ui

                //Категория
                .subSectionClick("Управление пользователями", "Пользователи системы")
                .tableHrefCellClick(name, name)
                //Редактирование
        
                .titleExists("Редактирование пользователя системы")
                //Заполнение данных
                .inputSet("Название", name)
                .inputSet("Электронная почта", email)
                .inputSet("Пароль", password)
                .inputSet("Подтвердить пароль", password);

        if (status.equals("Активный")) ui.radioButtonOn("Статус");
        if (finance.equals("Нет")) ui.radioButtonOff("Показывать финансовую информацию");
        if (changePasswordOnNextAuth.equals("Да")) ui.radioButtonOn("Сменить пароль при следующей авторизации");
        if (useRole.equals("Нет")) {
            ui
                    .radioButtonOff("Использовать роль")
            ;
            if (!checkBoxList[0].equals("-")) {
                ui
                        .useCompositeStep().checkBoxOff(allCheckBoxes)
                        .useCompositeStep().checkBoxOn(checkBoxList)
                ;
            }

        }
        else {
            ui.radioButtonOn("Использовать роль");
        }
        ui
                .buttonClick("Сохранить")
                .tableRowExists(name)
        ;
    }

    public static Stream<?> editSystemUserList() {
        return XMLHandler.instance().getRows("Пользователи системы").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[C9345] Адрес отправителя")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSenderAddressList")
    @Tag("edit1")
    public void editSenderAddress(Map<String, String> values) {
        String senderAdress = values.get("Адрес отправителя"),
                clientName = values.get("Клиент"),
                deliveryType = values.get("Транспорт"),
                status = values.get("Статус"),
                comment = values.get("Комментарий");
//        if (deliveryType.equals("Call")) {
//            senderAdress=senderAdress.substring(1);
//        }

        ui

                //Категория
                .subSectionClick("Настройки", "Адреса отправителей")

                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet("Адрес отправителя", senderAdress)
                .buttonClick("a")

                .tableHrefCellClick(senderAdress,clientName,deliveryType)
                //Заполнение данных
            
                .inputSet("~Адрес", senderAdress)
                .inputSet("Клиент", clientName)
                .inputSet("Транспорт", deliveryType)
                .radioButtonClick(status);
        if (status.equals("Запрещен")) {
            ui.inputSet("Комментарий", comment);
        }
        ui
                .buttonClick("s")
                .tableRowExists(senderAdress, clientName, deliveryType)

                .buttonClick("f")
                .inputClear("Адрес отправителя")
                .buttonClick("a")
        ;
    }

    public static Stream<?> editSenderAddressList() {
        return XMLHandler.instance().getRows("Адреса отправителей").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[editAccaunt] Аккаунты")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editAccauntList")
    @Tag("edit0")
    public void editAccaunt(Map<String, String> values) {
        String protocolName = values.get("Способ доставки"),
                clientName = values.get("Клиент"),
                accauntName = values.get("Имя аккаунта"),
                accauntLogin=values.get("Логин аккаунта"),
                password = values.get("Пароль"),
                url = values.get("URL"),
                informOf = values.get("Сообщать о статусах"),
                statusType = values.get("Статус"),
                deliveryType = values.get("Транспорт"),
                bandwidth = values.get("Максимальная пропускная способность"),
                returnEmResp = values.get("Сразу возвращать submit_sm_resp"),
                automaticallyGetStatus = values.get("Автоматическое получение статусов доставки");

        ui

                //Категория
                .subSectionClick("Настройки", "Аккаунты")
                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet("Имя аккаунта", accauntName)
                .buttonClick("a")
                .tableHrefCellClick(accauntName)
                //Заполнение данных
                .waiting(3)

                //.inputSet("Способ доставки", protocolName)
                .inputSet("Клиент", clientName)
                .inputSet("Имя аккаунта", accauntName)
                .inputSet("Логин аккаунта",accauntLogin)
                .inputSet("Пароль", password)
                .inputSet("~Подтвердить", password);
        if (statusType.equals("Неактивный"))
            ui.radioButtonOff("Статус");
        else ui.radioButtonOn("Статус");
        if (protocolName.equals("HTTP")) {
            ui
                    .radioButtonClick(informOf);
            if (automaticallyGetStatus.equals("Да"))
                ui.radioButtonOn("Автоматическое получение статусов доставки")
                        .cardInputSet("Настройки HTTP аккаунта","URL", url);
            else ui.radioButtonOff("Автоматическое получение статусов доставки");
        }
        else {
            ui
                    .cardInputClick("Настройки SMPP аккаунта","Транспорт")
                    .dropDownListOptionClick(deliveryType)
                    .cardInputClick("Настройки SMPP аккаунта","Максимальная пропускная способность")
                    .useCompositeStep().bandwidthSet(bandwidth);
            if (returnEmResp.equals("Нет")) ui.radioButtonOff("Сразу возвращать submit_sm_resp");
            else ui.radioButtonOn("Сразу возвращать submit_sm_resp");
        }
        ui
                .buttonClick("s")
                .tableRowExists(clientName, protocolName)
                .buttonClick("Очистить фильтры")
        ;


    }

    public static Stream<?> editAccauntList() {
        return XMLHandler.instance().getRows("Аккаунты").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[C9364] Блокировка одинаковых сообщений")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSameList")
    @Tag("edit4")
    public void acuiTestC9364(Map<String, String> values) {
        String  patternName = values.get("Шаблон"),
                clientName = values.get("Клиент"),
                deliveryType = values.get("Транспорт"),
                timeOut = values.get("Таймаут");

//        if (deliveryType.contains("Custom")) ui.setNotDefinedStatus("Такого функционала для транспорта Custom нет!");

        ui

                //Категория
                .subSectionClick("Настройки", "Блокировка одинаковых сообщений")
                .titleExists("Блокировка одинаковых сообщений")
                .tableHrefCellClick(deliveryType, clientName)
                //Заполнение данных
         
                .inputSet("Транспорт", deliveryType)
                ;

        if (!clientName.equals("Все")) {
            ui
                    .inputSet("Клиент", clientName);
        }

        if (!patternName.equals("Все")) {
            ui
                .inputSet("Шаблон", patternName);
        }

            ui
                .inputSet("Таймаут", timeOut)
                .buttonClick("s")

                .isAlreadyExists()
                .tableRowExists(clientName, deliveryType)
        ;
    }

    public static Stream<?> editSameList() {
        return XMLHandler.instance().getRows("Блокировка одинаковых сообщений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("[C18074] Черные списки. Редактирование")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editBL")
    @Tag("edit2")
    public void C18074(Map<String, String> values) {
        String name = values.get("Название"),
                clientName = values.get("Клиент"),
                userName = values.get("Пользователь"),
                descriprion = values.get("Комментарий");
        ui

                //Категория
                .subSectionClick("Настройки", "Черные списки")
                .tableHrefCellClick(name)
                //Редактирование
           
                .inputSet("Название", name)
                //.inputSet("Клиент", clientName)
                //.inputSet("Пользователь", userName)
                .inputSet("Комментарий", descriprion)
                .buttonClick("s")

                .tableRowExists(name)
        ;
    }

    public static Stream<?> editBL() {
        return XMLHandler.instance().getRows("Чёрные списки").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"Настройки\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Дополнительные правила. Редактирование")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPreproccesing")
    @Tag("edit4")
    public void acuiTestC9368(Map<String, String> values) {
        String ruleName = values.get("Правило"),
                deliveryType = values.get("Транспорт"),
                clientName = values.get("Клиент"),
                accauntName = values.get("Аккаунт"),
                patternName = values.get("Шаблон"),
                senderAddress = values.get("Адрес отправителя");

        if (deliveryType.contains("Call")) ui.setNotDefinedStatus("Звонки будут в 1.x");

        ui

                //Категория
                .subSectionClick("Настройки", "Дополнительные правила")
                .tableHrefCellClick(ruleName)
         

                .inputSet("Правило", ruleName)
        ;

        if (!deliveryType.equals("Все")) {
            ui.inputSet("Транспорт", deliveryType);
        }

        if (!clientName.equals("Все")) {
            ui.inputClick("Клиент").dropDownListOptionClick(clientName);
        }

        if (!accauntName.equals("Все")) {
            ui.inputSet("Аккаунт", accauntName);
        }

        if (!patternName.equals("Все")) {
            ui.inputSet("Шаблон", patternName);
        }

        if (!senderAddress.equals("Все")) {
            ui.inputSet("Адрес отправителя", senderAddress);
        }

        ui
                .buttonClick("s")
                .isAlreadyExists()
                .tableRowExists(ruleName, clientName)
                .deleteIfExists(ruleName,clientName)
        ;
    }

    public static Stream<?> editPreproccesing() {
        return XMLHandler.instance().getRows("Дополнительные правила").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Проверка категории \"SMS\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Маршрутизация и тарификация поставщиков услуг SMS")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("provideSMSProviderNames")
    public void editSMSProviderCreationTariffTest(Map<String, String> values) {
        String providerName = values.get("Поставщик услуг"),
                deliverTypes = values.get("Тарификация"),
                protocolTypes= values.get("Маршрутизация");
        ui
                .subSectionClick("SMS", "Маршрутизация и тарификация поставщиков услуг")
                .inputSet("Поставщик услуг", providerName)
                .buttonClick("~Настройки")
                .inputSet("Тарификация", deliverTypes)
                .inputSet("Маршрутизация", protocolTypes)
                .buttonClick("s")

                .tableHrefCellClick(providerName + "Tariff")
             
                .inputSet("Название", providerName + "-135")
                .inputSet("MSISDN", "135")
                .inputSet("MCC", "135")
                .inputSet("MNC", "135")
                .inputSet("~Цена", "0.13")
                .buttonClick("s")

                .tableRowExists(providerName + "-135")

//                .tableHrefCellClick(providerName + "-789")
//
//                .inputSet("Название", providerName + "-975")
//                .inputSet("MSISDN", "975")
//                .inputSet("MCC", "975")
//                .inputSet("MNC", "975")
//                .inputSet("~Цена", "0.97")
//                .buttonClick("s")
        ;
    }

    public static Stream<?> provideSMSProviderNames(){
        return XMLHandler.instance().getRows("Маршр. Тариф. Поставщиков SMS").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Проверка категории \"SMS\"")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Маршрутизация и тарификация клиентов SMS")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("provideSMSClientNames")
    public void editSMSClientCreationTariffTest(Map<String, String> values) {
        String clientName = values.get("Клиент"),
                deliverTypes = values.get("Тарификация"),
                protocolTypes= values.get("Маршрутизация");
        ui
                .subSectionClick("SMS", "Маршрутизация и тарификация клиентов")
                .inputSet("Клиент", clientName)
                .buttonClick("~Настройки")
                .inputSet("Тарификация", deliverTypes)
                .inputSet("Маршрутизация", protocolTypes)
                .buttonClick("s")

                .tableHrefCellClick(clientName + "Tariff")
              
                .inputSet("Название", clientName + "-135")
                .inputSet("MSISDN", "135")
                .inputSet("MCC", "135")
                .inputSet("MNC", "135")
                .inputSet("~Цена", "0.13")
                .buttonClick("s")

                .tableRowExists(clientName + "-135")

//                .tableHrefCellClick(clientName + "-789")
//
//                .inputSet("Название", clientName + "-975")
//                .inputSet("MSISDN", "975")
//                .inputSet("MCC", "975")
//                .inputSet("MNC", "975")
//                .inputSet("~Цена", "0.97")
//                .buttonClick("s")
        ;
    }

    public static Stream<?> provideSMSClientNames(){
        return XMLHandler.instance().getRows("Маршр. Тариф. Клиентов SMS").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование изменения приоритета SMS")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSMSPriorityRulesList")
    @Tag("edit4")
    public void editSMSPriorityRules(Map<String, String> values) {
        String clientName = values.get("Клиент"),
                senderAdress = values.get("Адрес отправителя"),
                pattern = values.get("Шаблон"),
                priority = values.get("Приоритет");
        ui
                .useCompositeStep().specialEditPriorityRules(clientName,senderAdress,pattern,priority)
        ;
    }

    public static Stream<?> editSMSPriorityRulesList() {
        return XMLHandler.instance().getRows("Изменение приоритета SMS").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование изменения адреса отправителя")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSMSOriginatorRulesList")
    @Tag("edit3")
    public void editSMSOriginatorRules(Map<String, String> values) {
        String client = values.get("Клиент"),
                senderAdress = values.get("Адрес отправителя"),
                provider = values.get("Поставщик услуг"),
                newSenderAdress = values.get("Новый адрес отправителя");
        ui
                .useCompositeStep().editOriginatorRules(provider,client,senderAdress,newSenderAdress)
        ;
    }

    public static Stream<?> editSMSOriginatorRulesList() {
        return XMLHandler.instance().getRows("Изменение адреса отпр. SMS").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование изменения адреса отправителя Call")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editCallOriginatorRulesList")
    @Tag("edit3")
    public void editCallOriginatorRules(Map<String, String> values) {
        String client = values.get("Клиент"),
                senderAdress = values.get("Адрес отправителя"),
                provider = values.get("Поставщик услуг"),
                newSenderAdress = values.get("Новый адрес отправителя");
        ui
                .useCompositeStep().editCallOriginatorRules(provider,client,senderAdress,newSenderAdress)
        ;
    }

    public static Stream<?> editCallOriginatorRulesList() {
        return XMLHandler.instance().getRows("Изменение адреса отпр. Call").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование блокировки по тексту SMS")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSMSFiltersList")
    @Tag("edit1")
    public void editSMSFilters(Map<String, String> values) {
        String client = values.get("Клиент"),
                rule = values.get("Условие"),
                value = values.get("Текст");
        ui
                .useCompositeStep().editSMSFilters(client,rule,value)
        ;
    }

    public static Stream<?> editSMSFiltersList() {
        return XMLHandler.instance().getRows("Блокировка по тексту SMS").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование ограничения адреса отправителя")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSMSRestrictionList")
    @Tag("edit1")
    public void editSMSRestriction(Map<String, String> values) {
        String provider = values.get("Поставщик услуг"),
                allowedOriginators = values.get("Разрешенные отправители");
        ui
                .useCompositeStep().editSMSRestrictions(provider,allowedOriginators)
        ;
    }

    public static Stream<?> editSMSRestrictionList() {
        return XMLHandler.instance().getRows("Ограничения адресов отпр.").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Управления Mail Notify шаблонами")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editManagementTemplatesList")
    @Tag("edit2")
    public void editManagementTemplates(Map<String, String> values) {
        String TMPL = values.get("TMPL"),
                provider = values.get("Поставщик услуг"),
                service = values.get("Сервис Mail Notify"),
                decription = values.get("Описание"),
                status = values.get("Статус");
        ui
                .useCompositeStep().editManagementTemplate(TMPL,provider,service,decription,status)
        ;
    }

    public static Stream<?> editManagementTemplatesList() {
        return XMLHandler.instance().getRows("Управление MN шаблонами").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование маршрутизации входящий сообщений")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editIncomingTariffsList")
    @Tag("edit3")
    public void editIncomingTariffs(Map<String, String> values) {
        String transport = values.get("Транспорт"),
                serviceNumber = values.get("Сервисный номер"),
                regularExpression = values.get("Регулярное выражение"),
                client = values.get("Клиент"),
                accaunt = values.get("Аккаунт"),
                priority = values.get("Приоритет"),
                provider = values.get("Поставщик услуг");
        //if (transport.equals("Viber") || transport.equals("WhatsApp")) ui.setNotDefinedStatus("https://jira.wsoft.ru/browse/MSG-4694");
        ui
                .useCompositeStep().editIncomingTariffs(transport,serviceNumber,regularExpression,client,accaunt,priority,provider)
        ;
    }

    public static Stream<?> editIncomingTariffsList() {
        return XMLHandler.instance().getRows("Маршр. входящих сообщений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование маршрутизации входящий сообщений")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editSMPPConnectionsList")
    @Tag("edit2")
    public void editSMPPConnections(Map<String, String> values) {
        String name = values.get("Название"),
                providerName = values.get("Поставщик услуг"),
                server = values.get("Server Hostname"),
                port = values.get("Server Port"),
                systemID = values.get("System ID"),
                password = values.get("Пароль");
        ui
                .useCompositeStep().editSMPPConnection(name,providerName,server,port,systemID,password)
        ;
    }

    public static Stream<?> editSMPPConnectionsList() {
        return XMLHandler.instance().getRows("Подключ. к опер. по SMPP").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }
    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование тарификации для клиента")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPartnersTariffsForClientList")
    @Tag("edit1")
    public void editPartnersTariffsForClient(Map<String, String> values) {
        String transport = values.get("Транспорт"),
                client = values.get("Клиент"),
                provider = values.get("Поставщик услуг"),
                value = values.get("Цена");

        if (transport.contains("Call")) ui.setNotDefinedStatus("Звонки будут в 1.x");
//        if (transport.contains("Custom")) ui.setNotDefinedStatus("Такого функционала для транспорта Custom нет!");

        ui
                .useCompositeStep().editPartnersTariffsForClient(transport,client,provider,value)
        ;
    }

    public static Stream<?> editPartnersTariffsForClientList() {
        return XMLHandler.instance().getRows("Тарификация клиентов").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование тарификации для поставщика")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPartnersTariffsForProviderList")
    @Tag("edit1")
    public void editPartnersTariffsForProvider(Map<String, String> values) {
        String transport = values.get("Транспорт"),
                provider = values.get("Поставщик услуг"),
                value = values.get("Цена");
        ui
                .useCompositeStep().editPartnersTariffsForProvider(transport,provider,value)
        ;
    }

    public static Stream<?> editPartnersTariffsForProviderList() {
        return XMLHandler.instance().getRows("Тарификация поставщиков").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Mail Notify сервиса")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editMailNotifyManagementServicesList")
    @Tag("edit1")
    public void editMailNotifyManagementServices(Map<String, String> values) {
        String name = values.get("Название"),
                provider = values.get("Поставщик услуг"),
                description = values.get("Описание"),
                status = values.get("Статус");
        ui
                .useCompositeStep().editMailNotifyManagementServices(name,provider,description,status)
        ;
    }

    public static Stream<?> editMailNotifyManagementServicesList() {
        return XMLHandler.instance().getRows("Управление сервисами MN").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(SAME_THREAD)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Push приложения")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPushAppList")
    @Tag("edit1")
    public void editPushApp(Map<String, String> values) {
        String name = values.get("Название"),
                login = values.get("Логин"),
                password = values.get("Пароль");
        String[] clouds = values.get("Платформа").split(","),
                clients = values.get("Клиенты").split(",");
        ui
                .useCompositeStep().editPushApp(name,login,password,clouds,clients)
        ;
    }


    public static Stream<?> editPushAppList() {
        return XMLHandler.instance().getRows("Управление приложениями").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(SAME_THREAD)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Web-Push приложения")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editWebPushAppList")
    @Tag("edit2")
    public void editWebPushApp(Map<String, String> values) {
        String name = values.get("Название"),
                publicKey = values.get("Публичный ключ"),
                privateKey = values.get("Приватный ключ")
//                contrarity = values.get("Подпись отправителя")
                ;
        ui
                .useCompositeStep().editWebPushApp(name,publicKey,privateKey)
        ;
    }

    public static Stream<?> editWebPushAppList() {
        return XMLHandler.instance().getRows("WebPush приложения").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование Push Attributes")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPushAttributesList")
    @Tag("edit2")
    public void editPushAttributes(Map<String, String> values) {
        String app = values.get("Приложение"),
                owner = values.get("Владелец"),
                attribute = values.get("Название"),
                type = values.get("Основные настройки");
        ui.useCompositeStep().editPushAttributes(app,owner,attribute,type);
    }

    public static Stream<?> editPushAttributesList() {
        return XMLHandler.instance().getRows("Атрибуты приложений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование шаблонов")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("ru.wsoft.tests.ui.adm.settings.addition.SettingsDataProvider#getDataProviderEmailPatterns")
    @Tag("edit3")
    public void editPattern(Map<String, String> values) {
        String  transport = values.get("Транспорт"),
                patternName = values.get("Название"),
//                clientName = values.get("Клиент"),
//                userName = values.get("Пользователь"),
                senderName = values.get("Отправитель"),
                message = values.get("Сообщение");

        if (transport.contains("Call")) ui.setNotDefinedStatus("Звонки будут в 1.x");

        ui
                .useCompositeStep().editPattern(transport,patternName,senderName,message)
        ;
    }

    @Execution(CONCURRENT)
    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование контакта ЧС")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editBlackListContactsList")
    @Tag("edit3")
    public void editBlackListContacts(Map<String, String> values) {
        String blackListname = values.get("Чёрный список"),
                MSISDN = values.get("MSISDN"),
                email = values.get("Email"),
                description = values.get("Комментарий");
        ui
                .useCompositeStep().editBlackListContact(blackListname,MSISDN,email,description)
        ;
    }

    public static Stream<?> editBlackListContactsList() {
        return XMLHandler.instance().getRows("Контакты чёрных списков").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование роли ЛК")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editLkuiRolesList")
    @Tag("edit0")
    public void editLkuiRoles(Map<String, String> values) {
        String name = values.get("Название"),
                groupAD = values.get("Группа пользователей Active Directory");
                String[] categories = values.get("Разделы").split(",");
        ui
                .useCompositeStep().editLkuiRole(name,groupAD,categories)
        ;
    }

    public static Stream<?> editLkuiRolesList() {
        return XMLHandler.instance().getRows("Роли пользователей лк").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование роли")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editRolesList")
    @Tag("edit0")
    public void editRoles(Map<String, String> values) {
        String name = values.get("Название"),
                groupAD = values.get("Группа пользователей Active Directory"),
                        finance = values.get("Показывать финансовую информацию");
        String[] categories = values.get("Страницы").split(",");
        ui
                .useCompositeStep().editRole(name,groupAD,finance,categories)
        ;
    }

    public static Stream<?> editRolesList() {
        return XMLHandler.instance().getRows("Роли").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование ограничения на количество отправляемых сообщений")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editQuantityLimitsList")
    @Tag("edit1")
    public void editQuantityLimits(Map<String, String> values) {
        String timeout = values.get("Таймаут"),
                countLimit = values.get("Количество сообщений");
        String[] transports = values.get("Транспорт").split(","),
                types = values.get("Тип сообщения").split(",");
        ui
                .useCompositeStep().editQuantityLimit(transports,countLimit,timeout,types)
        ;
    }

    public static Stream<?> editQuantityLimitsList() {
        return XMLHandler.instance().getRows("Ограничение кол. отпр сообщений").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование общего чёрного списка")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editGeneralBlackListList")
    @Tag("edit0")
    public void editGeneralBlackList(Map<String, String> values) {
        String msisdn = values.get("MSISDN"),
                email = values.get("Email"),
                description = values.get("Комментарий");
        ui
                .useCompositeStep().editGeneralBlackList(msisdn,email,description)
        ;
    }

    public static Stream<?> editGeneralBlackListList() {
        return XMLHandler.instance().getRows("Общие чёрные списки").stream()
                .filter(map -> map.values().stream().anyMatch(val->val.contains("GEdit")));
    }

    @Feature("Инициализация данных")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Редактирование настроек порога отключения")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editBalanceList")
    @Tag("edit0")
    public void editBalance(String client, String sum) {
        ui
                .subSectionClick("Управление финансами", "Управление балансами")

                .buttonClickIfExists("Очистить фильтры")
                .buttonClick("f")
                .inputSet("Клиент", client)
                .buttonClick("a")

                .tableRowExists(client)

                .buttonClick("Настроить порог отключения")

                .inputSet("Клиент", client)
                .inputSet("Порог отключения", sum)
                .buttonClick("s")

                .tableRowExists(client, "1.0000")

                .buttonClick("Очистить фильтры")
        ;
    }

    public static Stream<Arguments> editBalanceList() {
        return Stream.of(
                Arguments.of("ClientGEditSMS", "1"),
                Arguments.of("ClientGEditCall", "1"),
                Arguments.of("ClientGEditViber", "1"),
                Arguments.of("ClientGEditWhatsApp", "1"),
                Arguments.of("ClientGEditEmail", "1"),
                Arguments.of("ClientGEditMailNotify", "1"),
                Arguments.of("ClientGEditCustom", "1"),
                Arguments.of("ClientGEditPush", "1")
                );
    }

    @Feature("Категория \"Все каналы\"")
    @DisplayName("[MSG5471] Шаблоны. Проверка поля Публичность при редактировании")
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("editPatternPublich")
    @Tag("edit0")
    public void MSG5471_2(String transport, String name) throws SQLException {
        boolean checkedParameterExisted=false;

        //Меняем публичность шаблона на "Публичный"
        ui
                .subSectionClick(transport,"Шаблоны")
                .tableHrefCellClick(name)
                .inputSet("Публичность","Публичный")
                .buttonClick("Сохранить")

                .tableRowExists(name, "Wings","wingsUser","Low","check_circle")
        ;

        //Проверяем значение публичности в КЭШе
        SQLHandler sql=new SQLHandler();
        String id= String.valueOf(sql.getID("BULK_DISTR_TEMPLATES","name",name));
        cache
                .cacheService("WCS:group=Services,instance-type=Cache,name=cache-service1")
                .openCache()
                .get("templates", id)
                .xmlContains("publicity","0")
        ;

        String senderAddress="13000";
        String app="SampleApp";
        String service="serviceMNForGeneral";
        //Создаем того же отправителя/приложение/сервис у второго Клиента
        if(transport.equals("SMS") || transport.equals("Viber") || transport.equals("WhatsApp")){
            ui.subSectionClick("Настройки","Адреса отправителей")
                    .deleteIfExists(senderAddress,"ClientAll")
                    .buttonClick("+")
                    .inputSet("Адрес отправителя",senderAddress)
                    .inputSet("Клиент","ClientAll")
                    .inputSet("Транспорт",transport)
                    .radioButtonOn("Разрешен")
                    .buttonClick("Сохранить")
                    ;
        }

        if (transport.equals("Push")){
            ui.subSectionClick("Push","Управление приложениями")
                    .tableHrefCellClick(app)
                    .inputSet("Клиенты","ClientAll")
                    .buttonClick("Сохранить")
                    ;
        }

        if(transport.equals("Mail Notify")){
            ui.subSectionClick("Mail Notify","~Операторские сервисы")
                    .tableHrefCellClick(service)
                    .checkBoxOn("ClientAll")
                    .buttonClick("Сохранить")
                    ;
        }

        ui
                //Проверяем, что у привязанного к шаблону клиента доступен шаблон
                .loginLkui()
                .subSectionClick("Рассылки",transport)
                .inputClick("Шаблон сообщения")
                .dropDownListOptionExists(name)

                //Проверяем, что у непривязанного к шаблону клиента доступен шаблон
                .loginLkui("all@all.ru","123456q")
                .subSectionClick("Рассылки",transport)
                .inputClick("Шаблон сообщения")
                .dropDownListOptionExists(name)
        ;

        ui
                //Меняем публичность шаблона на непубличный
                .loginAcui()
                .subSectionClick(transport,"Шаблоны")
                .tableHrefCellClick(name)
                .inputSet("Публичность","Непубличный")
                .buttonClick("Сохранить")

                .tableRowExists(name, "Wings","wingsUser","Low")
        ;

        //Проверяем значение публичности в КЭШе
        cache
                .cacheService("WCS:group=Services,instance-type=Cache,name=cache-service1")
                .openCache()
                .get("templates",id)
                .xmlContains("publicity","1")
        ;

        ui
                //Проверяем, что шаблон все еще доступен привязанному клиенту
                .loginLkui()
                .subSectionClick("Рассылки",transport)
                .inputClick("Шаблон сообщения")
                .dropDownListOptionExists(name)

                //Проверяем, что шаблон недоступен непривязанному клиенту
                .loginLkui("all@all.ru","123456q")
                .subSectionClick("Рассылки",transport)
                .inputClick("Шаблон сообщения")
                .dropDownListOptionNotExists(name)
        ;

        ui
                //Меняем шаблон на публичный
                .loginAcui()
                .subSectionClick(transport,"Шаблоны")
                .tableHrefCellClick(name)
                .inputSet("Публичность","Публичный")
                .buttonClick("Сохранить")

                .tableRowExists(name, "Wings","wingsUser","Low")
        ;

        //Удаляем адрес отправителя/приложение/сервис у непривязанного к шаблону клиента
        if(transport.equals("SMS") || transport.equals("Viber") || transport.equals("WhatsApp")) {
            ui.subSectionClick("Настройки","Адреса отправителей")
                    .deleteIfExists(senderAddress,"ClientAll");
        }
        if(transport.equals("Push")){
            ui.subSectionClick("Push","Управление приложениями")
                    .tableHrefCellClick(app)
                    .inputClear("Клиенты")
                    .inputSet("Клиенты","Wings")
                    .buttonClick("Сохранить")
            ;
        }
        if(transport.equals("Mail Notify")){
            ui.subSectionClick("Mail Notify","~Операторские сервисы")
                    .tableHrefCellClick(service)
                    .radioButtonOff("ClientAll")
                    .buttonClick("Сохранить")
                    ;
        }

        //Проверяем, что шаблон недоступен непривязанному клиенту
        ui.loginLkui("all@all.ru","123456q")
                .subSectionClick("Рассылки",transport)
                .inputClick("Шаблон сообщения")
                .dropDownListOptionNotExists(name)
                ;
    }

    public static Stream<Arguments> editPatternPublich(){
        return Stream.of(
                Arguments.of("SMS","SMSLKPatCascade"),
                Arguments.of("Push","PushLKPatCascade"),
                Arguments.of("Viber","ViberLKPatCascade"),
                Arguments.of("WhatsApp","WhatsAppLKPatCascade"),
                Arguments.of("Mail Notify","Mail NotifyLKPatCascade")
        );
    }

}
