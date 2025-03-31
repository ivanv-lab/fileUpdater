package ru.wsoft.tests.ui.adm.core;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import jdk.jfr.Label;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.wsoft.tests.framework.utils.model.DataGenerator;
import ru.wsoft.tests.ui.adm.BaseConsoleTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
public class MSGTests extends BaseConsoleTest {

    public static Stream<Arguments> subSectionList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны"),
                Arguments.of("SMS", "Детальная статистика"),
                Arguments.of("SMS", "Групповая статистика"),
                Arguments.of("SMS", "Маршрутизация и тарификация клиентов"),
                Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS"),
                Arguments.of("SMS", "Блокировка по тексту"),
                Arguments.of("SMS", "Ограничения адресов отправителей"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений"),
                Arguments.of("SMS", "Детальная статистика входящих SMS"),
                Arguments.of("SMS", "Групповая статистика входящих SMS"),

                Arguments.of("Звонки", "Детальная статистика"),
                Arguments.of("Звонки", "Групповая статистика"),
                Arguments.of("Звонки", "Детальная статистика входящих звонков"),
                Arguments.of("Звонки", "Групповая статистика входящих звонков"),
                Arguments.of("Звонки", "Изменение адреса отправителя Call"),
                Arguments.of("Звонки", "IVR-меню"),

                Arguments.of("Электронная почта", "Шаблоны"),
                Arguments.of("Электронная почта", "Детальная статистика"),
                Arguments.of("Электронная почта", "Групповая статистика"),
                Arguments.of("Электронная почта", "Групповая статистика событий Email"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг"),

                Arguments.of("Push", "Шаблоны"),
                Arguments.of("Push", "Детальная статистика"),
                Arguments.of("Push", "Групповая статистика"),
                Arguments.of("Push", "Атрибуты приложений"),
                Arguments.of("Push", "Управление приложениями"),
                Arguments.of("Push", "Группы приложений"),
                Arguments.of("Push", "Количество подписчиков"),
                Arguments.of("Push", "История подписок"),
                Arguments.of("Push", "Тарификация Push клиентов"),

                Arguments.of("Viber", "Шаблоны"),
                Arguments.of("Viber", "Детальная статистика"),
                Arguments.of("Viber", "Групповая статистика"),
                Arguments.of("Viber", "Тарификация Viber клиентов"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений"),
                Arguments.of("Viber", "Детальная статистика входящих Viber"),
                Arguments.of("Viber", "Групповая статистика входящих Viber"),

                Arguments.of("WhatsApp", "Шаблоны"),
                Arguments.of("WhatsApp", "Детальная статистика"),
                Arguments.of("WhatsApp", "Групповая статистика"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений"),
                Arguments.of("WhatsApp", "Детальная статистика входящих WhatsApp"),
                Arguments.of("WhatsApp", "Групповая статистика входящих WhatsApp"),

                Arguments.of("Mail Notify", "Шаблоны"),
                Arguments.of("Mail Notify", "Детальная статистика"),
                Arguments.of("Mail Notify", "Групповая статистика"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify"),

                Arguments.of("Custom", "Шаблоны"),
                Arguments.of("Custom", "Детальная статистика"),
                Arguments.of("Custom", "Групповая статистика"),
                Arguments.of("Custom", "Тарификация Custom клиентов"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг"),

                Arguments.of("Настройки", "Поставщики услуг"),
                Arguments.of("Настройки", "Клиенты"),
                Arguments.of("Настройки", "Аккаунты"),
                Arguments.of("Настройки", "Адреса отправителей"),
                Arguments.of("Настройки", "Настройки системы"),
                Arguments.of("Настройки", "Отчеты по экспорту"),
                Arguments.of("Настройки", "Типы сообщений"),
                Arguments.of("Настройки", "Категории сообщений"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений"),
                Arguments.of("Настройки", "Подключения поставщиков услуг"),
                Arguments.of("Настройки", "Подключения клиентов"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP"),
                Arguments.of("Настройки", "Дополнительные правила"),
                Arguments.of("Настройки", "Черные списки"),
                Arguments.of("Настройки", "Общие черные списки"),
                Arguments.of("Настройки", "Белый список"),
                Arguments.of("Настройки", "Прокси-серверы"),
                Arguments.of("Настройки", "Изменение приоритета"),


                Arguments.of("Управление пользователями", "Пользователи личного кабинета"),
                Arguments.of("Управление пользователями", "Пользователи колл-центра"),
                Arguments.of("Управление пользователями", "Пользователи системы"),
                Arguments.of("Управление пользователями", "Действия пользователей"),
                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета"),
                Arguments.of("Управление пользователями", "Роли"),
                Arguments.of("Управление пользователями", "Модерация рассылок"),
                Arguments.of("Управление пользователями", "Модерация шаблонов"),
                Arguments.of("Управление пользователями", "Настройки логирования действий пользователей"),

                Arguments.of("Управление финансами", "Управление балансами"),
                Arguments.of("Управление финансами", "История пополнений")
        );
    }

    public static Stream<Arguments> nonRequiredFieldsListForTariffication() {
        return Stream.of(
                Arguments.of("SMS", "Маршрутизация и тарификация клиентов"),
                Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг"),

                Arguments.of("Электронная почта", "Тарификация Email клиентов"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг"),

                Arguments.of("Push", "Тарификация Push клиентов"),
                Arguments.of("Push", "Атрибуты приложений"),

                Arguments.of("Viber", "Тарификация Viber клиентов"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг"),

                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг"),

                Arguments.of("Custom", "Тарификация Custom клиентов"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг")
                );
    }

    public static Stream<Arguments> nonRequiredFieldsList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Черный список"),
                Arguments.of("SMS", "Шаблоны", "Приоритет"),
                Arguments.of("SMS", "Шаблоны", "Тип сообщения"),
                Arguments.of("SMS", "Шаблоны", "Срок жизни сообщения (с)"),
                Arguments.of("SMS", "Шаблоны", "Скорость отправки в секунду"),
                Arguments.of("SMS", "Шаблоны", "Длина сообщения"),

                //Arguments.of("SMS", "Редактирование приоритета SMS", "Клиент"),
                //Arguments.of("SMS", "Редактирование приоритета SMS", "Адрес отправителя"),
                //Arguments.of("SMS", "Редактирование приоритета SMS", "Шаблон"),

                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Клиент"),
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Поставщик услуг"),
               // Arguments.of("SMS", "Изменение адреса отправителя SMS", "Адрес отправителя"), - Спец

                Arguments.of("SMS", "Блокировка по тексту SMS", "Клиент"),

                //Arguments.of("SMS", "Ограничения адресов отправителей"), - обяз
                //Arguments.of("SMS", "Маршрутизация входящих сообщений"), - обяз

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Клиент"),
                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Поставщик услуг"),
                //Arguments.of("Звонки", "Изменение адреса отправителя Call", "Адрес отправителя"),

                Arguments.of("Электронная почта", "Шаблоны", "От кого"),
                Arguments.of("Электронная почта", "Шаблоны", "Тема письма"),
                //Arguments.of("Электронная почта", "Шаблоны", "Черный список"),
                //Arguments.of("Электронная почта", "Шаблоны", "Тип сообщения"),

                Arguments.of("Push", "Шаблоны", "Заголовок"),
                Arguments.of("Push", "Шаблоны", "Сообщение"),
                Arguments.of("Push", "Шаблоны", "Контент"),
                //Arguments.of("Push", "Шаблоны", "Тип сообщения"),

                Arguments.of("Push", "Управление приложениями", "Описание"),

                Arguments.of("Viber", "Шаблоны", "Ссылка на изображение"),
                Arguments.of("Viber", "Шаблоны", "Название кнопки"),
                Arguments.of("Viber", "Шаблоны", "Ссылка на кнопку"),
                Arguments.of("Viber", "Шаблоны", "Срок жизни сообщения (с)"),
                //Arguments.of("Viber", "Шаблоны", "Черный список"),
                //Arguments.of("Viber", "Шаблоны", "Тип сообщения"),

                //Arguments.of("Viber", "Маршрутизация входящих сообщений"), - обяз

                Arguments.of("WhatsApp", "Шаблоны", "Срок жизни сообщения (с)"),
                //Arguments.of("WhatsApp", "Шаблоны", "Черный список"),
                //Arguments.of("WhatsApp", "Шаблоны", "Тип сообщения"),

                // Arguments.of("WhatsApp", "Маршрутизация входящих сообщений"), - обяз

                Arguments.of("Mail Notify", "Шаблоны", "Список соц.сетей для отправки"),
                //Arguments.of("Mail Notify", "Шаблоны", "Черный список"),
                //Arguments.of("Mail Notify", "Шаблоны", "Тип сообщения"),

                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Описание"),

                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Описание"),

                Arguments.of("Custom", "Шаблоны", "Ссылка на изображение"),
                Arguments.of("Custom", "Шаблоны", "Название кнопки"),
                Arguments.of("Custom", "Шаблоны", "Ссылка на кнопку"),
                Arguments.of("Custom", "Шаблоны", "Срок жизни сообщения (с)"),
                //Arguments.of("Custom", "Шаблоны", "Черный список"),
                //Arguments.of("Custom", "Шаблоны", "Тип сообщения"),

                //Arguments.of("Настройки", "Поставщики услуг"),
                //Arguments.of("Настройки", "Клиенты"),
                //Arguments.of("Настройки", "Аккаунты"),
                //Arguments.of("Настройки", "Адреса отправителей"),
                Arguments.of("Настройки", "Настройки системы", "Значение"),
                Arguments.of("Настройки", "Настройки системы", "Описание"),

                Arguments.of("Настройки", "Типы сообщений", "Описание"),

                Arguments.of("Настройки", "Категории сообщений", "Описание"),

                //Arguments.of("Настройки", "Блокировка одинаковых сообщений"),
                //Arguments.of("Настройки", "Ограничение количества отправляемых сообщений"),

                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Комментарий"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Local Ip"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Local Port"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Addr Ton"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Addr Npi"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Default Source Ton"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Default Source Npi"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Default Destination Ton"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Default Destination Npi"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "System Type"),
                // Arguments.of("Настройки", "Подключения к операторам по SMPP", "Max Throughput"), -спец!

                //Arguments.of("Настройки", "Дополнительные правила"), - особ

                Arguments.of("Настройки", "Черные списки", "Комментарий"),
                Arguments.of("Настройки", "Черные списки", "Клиент"),

//                Arguments.of("Настройки", "Общие черные списки  ", "Комментарий"),

                //Arguments.of("Управление пользователями", "Пользователи личного кабинета"),
                //Arguments.of("Управление пользователями", "Пользователи системы"),

                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Группа пользователей Active Directory"),

                Arguments.of("Управление пользователями", "Роли", "Группа пользователей Active Directory")

                //Arguments.of("Управление финансами", "Управление балансами"), ВТОРАЯ кнопка!
        );
    }

    public static Stream<Arguments> requiredFieldsList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Название"),
                Arguments.of("SMS", "Шаблоны", "Клиент"),
                Arguments.of("SMS", "Шаблоны", "Сообщение"),

                // Arguments.of("SMS", "Маршрутизация и тарификация клиентов"), - иной
                // Arguments.of("SMS", "Маршрутизация и тарификация поставщиков услуг"),

                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Новый адрес отправителя"),

                Arguments.of("SMS", "Ограничения адресов отправителей", "Поставщик услуг"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Разрешенные отправители"),


                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Регулярное выражение"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Клиент"),

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Новый адрес отправителя"),

                Arguments.of("Электронная почта", "Шаблоны", "Название"),
                Arguments.of("Электронная почта", "Шаблоны", "Клиент"),

                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Клиент"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Цена"),

                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Поставщик услуг"),
                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Цена"),

                Arguments.of("Push", "Шаблоны", "Название"),
                Arguments.of("Push", "Шаблоны", "Клиент"),

                // Arguments.of("Push", "Атрибуты приложений"), - спец

                Arguments.of("Push", "Управление приложениями", "Название"),
                Arguments.of("Push", "Управление приложениями", "Логин"),
//
                Arguments.of("Push", "Управление приложениями", "Устройства"),
//
                Arguments.of("Push", "Тарификация Push клиентов", "Клиент"),
                Arguments.of("Push", "Тарификация Push клиентов", "Цена"),

                Arguments.of("Viber", "Шаблоны", "Название"),
                Arguments.of("Viber", "Шаблоны", "Клиент"),
                Arguments.of("Viber", "Шаблоны", "Сообщение"),

                Arguments.of("Viber", "Тарификация Viber клиентов", "Клиент"),
                Arguments.of("Viber", "Тарификация Viber клиентов", "Цена"),

                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Поставщик услуг"),
                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Цена по умолчанию"),

                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Клиент"),

                Arguments.of("WhatsApp", "Шаблоны", "Название"),
                Arguments.of("WhatsApp", "Шаблоны", "Клиент"),
                Arguments.of("WhatsApp", "Шаблоны", "Сообщение"),

                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Клиент"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Цена"),

                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Поставщик услуг"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Цена по умолчанию"),

                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Регулярное выражение"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Клиент"),

                Arguments.of("Mail Notify", "Шаблоны", "Название"),
                Arguments.of("Mail Notify", "Шаблоны", "Клиент"),
                Arguments.of("Mail Notify", "Шаблоны", "Текст сообщения для VK"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Клиент"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Цена"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Поставщик услуг"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Цена по умолчанию"),

                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Название"),
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Поставщик услуг"),

                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "TMPL"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Поставщик услуг"),

                Arguments.of("Custom", "Шаблоны", "Название"),
                Arguments.of("Custom", "Шаблоны", "Сообщение"),

                Arguments.of("Custom", "Тарификация Custom клиентов", "Клиент"),
                Arguments.of("Custom", "Тарификация Custom клиентов", "Цена"),

                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Поставщик услуг"),
                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Цена по умолчанию"),
//
                Arguments.of("Настройки", "Поставщики услуг", "Название"),

                Arguments.of("Настройки", "Клиенты", "Название"),

                Arguments.of("Настройки", "Аккаунты", "Протокол"),
                Arguments.of("Настройки", "Аккаунты", "Клиент"),
                Arguments.of("Настройки", "Аккаунты", "Имя аккаунта"),


                Arguments.of("Настройки", "Адреса отправителей", "Адрес отправителя"),
                Arguments.of("Настройки", "Адреса отправителей", "Клиент"),

                Arguments.of("Настройки", "Настройки системы", "Название"),

                Arguments.of("Настройки", "Типы сообщений", "Название"),

                Arguments.of("Настройки", "Категории сообщений", "Название"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Транспорт"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Таймаут"),

                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Количество сообщений"),
//                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Таймаут"),

                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Название"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Поставщик услуг"),

                Arguments.of("Настройки", "Дополнительные правила", "Правило"),

                Arguments.of("Настройки", "Черные списки", "Название"),

                Arguments.of("Настройки", "Общие черные списки", "MSISDN"),
                Arguments.of("Настройки", "Общие черные списки", "Email"),

                Arguments.of("Настройки", "Блокировка по тексту", "Условие"),

                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Имя"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Клиент"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Почта"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Часовой пояс"),
//
                Arguments.of("Управление пользователями", "Пользователи колл-центра", "Имя"),
                Arguments.of("Управление пользователями", "Пользователи колл-центра", "Клиент"),
                Arguments.of("Управление пользователями", "Пользователи колл-центра", "Почта"),
                Arguments.of("Управление пользователями", "Пользователи колл-центра", "Часовой пояс"),
//
                Arguments.of("Управление пользователями", "Пользователи системы", "Название"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Электронная почта"),


                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Название"),

                Arguments.of("Управление пользователями", "Роли", "Название"),
//
                Arguments.of("Управление финансами", "Управление балансами", "Клиент"),
                Arguments.of("Управление финансами", "Управление балансами", "Порог отключения")
                );
    }

    public static Stream<Arguments> nonRequiredWithPreconditionFieldsList() {
        return Stream.of(
                Arguments.of("Настройки", "Изменение приоритета", "Шаблон", "Клиент", "ClientSMS"),

                Arguments.of("Push", "Шаблоны", "Приложение", "Клиент", "ClientPush"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Клиент", "Транспорт", "SMS"),

                Arguments.of("Настройки", "Дополнительные правила", "Клиент", "Транспорт", "SMS"),

                Arguments.of("Настройки", "Черные списки", "Пользователь", "Клиент", "ClientSMS")
        );
    }

    public static Stream<Arguments> nonRequiredWithDoublePreconditionFieldsList() {
        return Stream.of(
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Шаблон",
                        "Транспорт", "SMS", "Клиент", "ClientSMS")
        );
    }
    public static Stream<Arguments> requiredWithPreconditionFieldsList() {
        return Stream.of(
                Arguments.of("SMS", "Шаблоны", "Пользователь", "Клиент", "ClientSMS"),
                Arguments.of("SMS", "Шаблоны", "Отправитель", "Клиент", "ClientSMS"),

                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Аккаунт", "Клиент", "ClientSMS"),

                Arguments.of("Электронная почта", "Шаблоны", "Пользователь", "Клиент", "ClientEmail"),
                Arguments.of("Электронная почта", "Шаблоны", "Отправитель", "Клиент", "ClientEmail"),

                Arguments.of("Push", "Шаблоны", "Пользователь", "Клиент", "ClientPush"),

                Arguments.of("Viber", "Шаблоны", "Пользователь", "Клиент", "ClientViber"),
                Arguments.of("Viber", "Шаблоны", "Отправитель", "Клиент", "ClientViber"),

                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Аккаунт", "Клиент", "ClientViber"),

                Arguments.of("WhatsApp", "Шаблоны", "Пользователь", "Клиент", "ClientWhatsApp"),
                Arguments.of("WhatsApp", "Шаблоны", "Отправитель", "Клиент", "ClientWhatsApp"),

                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Аккаунт", "Клиент", "ClientWhatsApp"),

                Arguments.of("Mail Notify", "Шаблоны", "Пользователь", "Клиент", "ClientMailNotify"),
                Arguments.of("Mail Notify", "Шаблоны", "Сервис Mail Notify", "Клиент", "ClientMailNotify"),

                Arguments.of("Настройки", "Адреса отправителей", "Транспорт", "Клиент", "ClientSMS")
        );
    }

    public static Stream<Arguments> requiredWithDoublePreconditionFieldsList() {
        return Stream.of(
                Arguments.of("Настройки", "Дополнительные правила", "Аккаунт",
                        "Транспорт", "SMS", "Клиент", "ClientSMS"),
                Arguments.of("Настройки", "Дополнительные правила", "Шаблон",
                        "Транспорт", "SMS", "Клиент", "ClientSMS"),
                Arguments.of("Настройки", "Дополнительные правила", "Адрес отправителя",
                        "Транспорт", "SMS", "Клиент", "ClientSMS")
        );
    }

    public static Stream<Arguments> inputOnlyList() {
        return Stream.of(
                Arguments.of("Mail Notify", "Операторские сервисы Mail Notify", "Клиент")
        );
    }

    public static Stream<Arguments> filtersList() {
        return Stream.of(
                Arguments.of("Настройки", "Дополнительные правила", "Аккаунт"),
                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Клиент"),
                Arguments.of("SMS", "Шаблоны", "Приоритет"),
                Arguments.of("SMS", "Шаблоны", "Название"),

                Arguments.of("SMS", "Шаблоны", "Клиент"),
                Arguments.of("SMS", "Шаблоны", "Сообщение"),
                Arguments.of("SMS", "Шаблоны", "Пользователь"),
                Arguments.of("SMS", "Шаблоны", "Активность"),

                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Поставщик услуг"),
                //Arguments.of("SMS", "Изменение адреса отправителя SMS", "Адрес отправителя"), // Нельзя взять из поля!

                Arguments.of("Настройки", "Блокировка по тексту", "Клиент"),

                Arguments.of("SMS", "Ограничения адресов отправителей", "Поставщик услуг"),
                Arguments.of("SMS", "Ограничения адресов отправителей", "Разрешенные отправители"),

                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Клиент"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Поставщик услуг"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("SMS", "Маршрутизация входящих сообщений", "Аккаунт"),

                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Клиент"), // Виснет админка
                Arguments.of("Звонки", "Изменение адреса отправителя Call", "Поставщик услуг"),
                //Arguments.of("Звонки", "Изменение адреса отправителя Call", "Адрес отправителя"), // Нельзя взять из поля!

                Arguments.of("Электронная почта", "Шаблоны", "Название"),
                Arguments.of("Электронная почта", "Шаблоны", "Тема письма"),
                Arguments.of("Электронная почта", "Шаблоны", "Приоритет"),
                Arguments.of("Электронная почта", "Шаблоны", "Клиент"),
                Arguments.of("Электронная почта", "Шаблоны", "Пользователь"),
                Arguments.of("Электронная почта", "Шаблоны", "Активность"),

                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Клиент"),
                Arguments.of("Электронная почта", "Тарификация Email клиентов", "Поставщик услуг"),

                Arguments.of("Электронная почта", "Тарификация Email поставщиков услуг", "Поставщик услуг"),

                Arguments.of("Push", "Шаблоны", "Название"),
                Arguments.of("Push", "Шаблоны", "Клиент"),
                Arguments.of("Push", "Шаблоны", "Сообщение"),
                Arguments.of("Push", "Шаблоны", "Приоритет"),
                Arguments.of("Push", "Шаблоны", "Пользователь"),
                Arguments.of("Push", "Шаблоны", "Активность"),

                Arguments.of("Push", "Управление приложениями", "Название"),
                Arguments.of("Push", "Управление приложениями", "Описание"),

                Arguments.of("Viber", "Шаблоны", "Название"),
                Arguments.of("Viber", "Шаблоны", "Клиент"),
                Arguments.of("Viber", "Шаблоны", "Сообщение"),
                Arguments.of("Viber", "Шаблоны", "Приоритет"),
                Arguments.of("Viber", "Шаблоны", "Пользователь"),
                Arguments.of("Viber", "Шаблоны", "Активность"),

                Arguments.of("Viber", "Тарификация Viber клиентов", "Клиент"),
                Arguments.of("Viber", "Тарификация Viber клиентов", "Поставщик услуг"),

                Arguments.of("Viber", "Тарификация Viber поставщиков услуг", "Поставщик услуг"),

                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Клиент"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Поставщик услуг"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("Viber", "Маршрутизация входящих сообщений", "Аккаунт"),

                Arguments.of("WhatsApp", "Шаблоны", "Название"),
                Arguments.of("WhatsApp", "Шаблоны", "Клиент"),
                Arguments.of("WhatsApp", "Шаблоны", "Сообщение"),
                Arguments.of("WhatsApp", "Шаблоны", "Приоритет"),
                Arguments.of("WhatsApp", "Шаблоны", "Пользователь"),
                Arguments.of("WhatsApp", "Шаблоны", "Активность"),

                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Клиент"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Поставщик услуг"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Сервисный номер"),
                Arguments.of("WhatsApp", "Маршрутизация входящих сообщений", "Аккаунт"),


                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Клиент"),
                Arguments.of("WhatsApp", "Тарификация WhatsApp клиентов", "Поставщик услуг"),
//
                Arguments.of("WhatsApp", "Тарификация WhatsApp поставщиков услуг", "Поставщик услуг"),

                Arguments.of("Mail Notify", "Шаблоны", "Название"),
                Arguments.of("Mail Notify", "Шаблоны", "Клиент"),
                Arguments.of("Mail Notify", "Шаблоны", "Текст сообщения для VK"),
                Arguments.of("Mail Notify", "Шаблоны", "Текст сообщения для OK"),
                Arguments.of("Mail Notify", "Шаблоны", "Приоритет"),
                Arguments.of("Mail Notify", "Шаблоны", "Пользователь"),
                Arguments.of("Mail Notify", "Шаблоны", "Активность"),

                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "TMPL"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Поставщик услуг"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Сервис Mail Notify"),
                Arguments.of("Mail Notify", "Операторские шаблоны Mail Notify", "Статус"),

                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Клиент"),
                Arguments.of("Mail Notify", "Тарификация Mail Notify клиентов", "Поставщик услуг"),
//
                Arguments.of("Mail Notify", "Тарификация Mail Notify поставщиков услуг", "Поставщик услуг"),

                Arguments.of("Custom", "Шаблоны", "Название"),
                Arguments.of("Custom", "Шаблоны", "Клиент"),
                Arguments.of("Custom", "Шаблоны", "Сообщение"),
                Arguments.of("Custom", "Шаблоны", "Приоритет"),
                Arguments.of("Custom", "Шаблоны", "Пользователь"),
                Arguments.of("Custom", "Шаблоны", "Активность"),

                Arguments.of("Custom", "Тарификация Custom клиентов", "Клиент"),
                Arguments.of("Custom", "Тарификация Custom клиентов", "Поставщик услуг"),

                Arguments.of("Custom", "Тарификация Custom поставщиков услуг", "Поставщик услуг"),

                Arguments.of("Настройки", "Поставщики услуг", "Название"),
                Arguments.of("Настройки", "Поставщики услуг", "Статус"),

                Arguments.of("Настройки", "Клиенты", "Название"),
                Arguments.of("Настройки", "Клиенты", "Статус"),
//
                Arguments.of("Настройки", "Аккаунты", "Протокол"),
                Arguments.of("Настройки", "Аккаунты", "Статус"),
                Arguments.of("Настройки", "Аккаунты", "Клиент"),
                Arguments.of("Настройки", "Аккаунты", "Имя аккаунта"),
//
                Arguments.of("Настройки", "Адреса отправителей", "Транспорт"),
                Arguments.of("Настройки", "Адреса отправителей", "Транспорт"),
                Arguments.of("Настройки", "Адреса отправителей", "Статус"),
                Arguments.of("Настройки", "Адреса отправителей", "Клиент"),
                Arguments.of("Настройки", "Адреса отправителей", "Адрес отправителя"),
////
                Arguments.of("Настройки", "Настройки системы", "Название"),

                Arguments.of("Настройки", "Типы сообщений", "Название"),

                Arguments.of("Настройки", "Категории сообщений", "Название"),
                Arguments.of("Настройки", "Категории сообщений", "Транспорт"),
                Arguments.of("Настройки", "Категории сообщений", "Категория по умолчанию"),
////
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Транспорт"),
                //Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Шаблон"), precondition
                //Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Клиент"), double precondition
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Таймаут"),
////
//                //Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Тип сообщения"), multiplechoise

//
                Arguments.of("Настройки", "Подключения поставщиков услуг", "Название соединения"),
                Arguments.of("Настройки", "Подключения поставщиков услуг", "Сервер"),
                Arguments.of("Настройки", "Подключения поставщиков услуг", "Статус"),

                Arguments.of("Настройки", "Подключения клиентов", "Клиент"),
                Arguments.of("Настройки", "Подключения клиентов", "Аккаунт"),
                Arguments.of("Настройки", "Подключения клиентов", "Протокол"),
                Arguments.of("Настройки", "Подключения клиентов", "Транспорт"),
                Arguments.of("Настройки", "Подключения клиентов", "Тип соединения"),
//
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Комментарий"),
                Arguments.of("Настройки", "Подключения к операторам по SMPP", "Название"),

                Arguments.of("Настройки", "Дополнительные правила", "Правило"),
                Arguments.of("Настройки", "Дополнительные правила", "Транспорт"),
                Arguments.of("Настройки", "Дополнительные правила", "Клиент"),
                Arguments.of("Настройки", "Дополнительные правила", "Аккаунт"),
                Arguments.of("Настройки", "Дополнительные правила", "Шаблон"),
                Arguments.of("Настройки", "Дополнительные правила", "Адрес отправителя"),

                Arguments.of("Настройки", "Черные списки", "Название"),
                Arguments.of("Настройки", "Черные списки", "Клиент"),
                Arguments.of("Настройки", "Черные списки", "Пользователь"),
////
                Arguments.of("Настройки", "Изменение приоритета", "Транспорт"),
                Arguments.of("Настройки", "Изменение приоритета", "Клиент"),
                Arguments.of("Настройки", "Изменение приоритета", "Отправитель"),
//                // Arguments.of("Настройки", "Изменение приоритета", "Шаблон"), -precondition
                Arguments.of("Настройки", "Изменение приоритета", "Приоритет"),
////
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Название"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Почта"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Клиент"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Статус"),
                Arguments.of("Управление пользователями", "Пользователи личного кабинета", "Роль"),
//
                Arguments.of("Управление пользователями", "Пользователи системы", "Название"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Электронная почта"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Роль"),
                Arguments.of("Управление пользователями", "Пользователи системы", "Статус"),

                Arguments.of("Управление пользователями", "Роли пользователей личного кабинета", "Название"),

                Arguments.of("Управление пользователями", "Роли", "Название"),

                Arguments.of("Управление пользователями", "Модерация шаблонов", "Клиент"),
                Arguments.of("Управление пользователями", "Модерация шаблонов", "Транспорт"),
                Arguments.of("Управление пользователями", "Модерация шаблонов", "Статус"),

                Arguments.of("Управление финансами", "Управление балансами", "Клиент"),
//
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Шаблон"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Количество сообщений"),
                Arguments.of("Настройки", "Ограничение количества отправляемых сообщений", "Транспорт")
        );
    }

    public static Stream<Arguments> transportFiltersList(){
        return Stream.of(
                Arguments.of("Настройки", "Поставщики услуг", "Транспорт"),
                Arguments.of("Настройки", "Клиенты", "Транспорт"),
                Arguments.of("Настройки", "Аккаунты", "Транспорт")
        );
    }

    public static Stream<Arguments> strongFiltersList(){
        return Stream.of(
                Arguments.of("SMS", "Изменение адреса отправителя SMS", "Клиент")
        );
    }

    public static Stream<Arguments> checkBoxesList() {
        return Stream.of(

                Arguments.of("SMS", "Шаблоны", "Понедельник"),
                Arguments.of("SMS", "Шаблоны", "Вторник"),
                Arguments.of("SMS", "Шаблоны", "Среда"),
                Arguments.of("SMS", "Шаблоны", "Четверг"),
                Arguments.of("SMS", "Шаблоны", "Пятница"),
                Arguments.of("SMS", "Шаблоны", "Суббота"),
                Arguments.of("SMS", "Шаблоны", "Воскресенье"),

                Arguments.of("Электронная почта", "Шаблоны", "Понедельник"),
                Arguments.of("Электронная почта", "Шаблоны", "Вторник"),
                Arguments.of("Электронная почта", "Шаблоны", "Среда"),
                Arguments.of("Электронная почта", "Шаблоны", "Четверг"),
                Arguments.of("Электронная почта", "Шаблоны", "Пятница"),
                Arguments.of("Электронная почта", "Шаблоны", "Суббота"),
                Arguments.of("Электронная почта", "Шаблоны", "Воскресенье"),

                Arguments.of("Viber", "Шаблоны", "Понедельник"),
                Arguments.of("Viber", "Шаблоны", "Вторник"),
                Arguments.of("Viber", "Шаблоны", "Среда"),
                Arguments.of("Viber", "Шаблоны", "Четверг"),
                Arguments.of("Viber", "Шаблоны", "Пятница"),
                Arguments.of("Viber", "Шаблоны", "Суббота"),
                Arguments.of("Viber", "Шаблоны", "Воскресенье"),

                Arguments.of("WhatsApp", "Шаблоны", "Понедельник"),
                Arguments.of("WhatsApp", "Шаблоны", "Вторник"),
                Arguments.of("WhatsApp", "Шаблоны", "Среда"),
                Arguments.of("WhatsApp", "Шаблоны", "Четверг"),
                Arguments.of("WhatsApp", "Шаблоны", "Пятница"),
                Arguments.of("WhatsApp", "Шаблоны", "Суббота"),
                Arguments.of("WhatsApp", "Шаблоны", "Воскресенье"),

                Arguments.of("Custom", "Шаблоны", "Понедельник"),
                Arguments.of("Custom", "Шаблоны", "Вторник"),
                Arguments.of("Custom", "Шаблоны", "Среда"),
                Arguments.of("Custom", "Шаблоны", "Четверг"),
                Arguments.of("Custom", "Шаблоны", "Пятница"),
                Arguments.of("Custom", "Шаблоны", "Суббота"),
                Arguments.of("Custom", "Шаблоны", "Воскресенье"),

                Arguments.of("Mail Notify", "Шаблоны", "Понедельник"),
                Arguments.of("Mail Notify", "Шаблоны", "Вторник"),
                Arguments.of("Mail Notify", "Шаблоны", "Среда"),
                Arguments.of("Mail Notify", "Шаблоны", "Четверг"),
                Arguments.of("Mail Notify", "Шаблоны", "Пятница"),
                Arguments.of("Mail Notify", "Шаблоны", "Суббота"),
                Arguments.of("Mail Notify", "Шаблоны", "Воскресенье")
        );
    }


    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка существования страницы {1} в категории {0} - {index}")
    @MethodSource("subSectionList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
                "1. Открыть категорию {section}\n\n" +
                "2. Зайти на страницу {subsection}\n\n" +
                "3. Проверить заголовок страницы\n\n"
        )
    public void subsectionCheck(String section, String subSection) {
        ui
                .subSectionClick(section,subSection)
                .pageTitleShoudBe(subSection);
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия необязательного поля {2} в категории {0}:{1} - {index}")
    @MethodSource("nonRequiredFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
                    "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Проверить, что поле {nonRequiredField} не являтся обязательным\n\n"
    )
    public void nonRequiredFieldCheck(String section, String subSection, String nonRequiredField) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+")
                .inputIsNotRequired(nonRequiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работоспособности поля {2} в категории {0}:{1} - {index}")
    @MethodSource({"requiredFieldsList", "nonRequiredFieldsList"})
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Проверить, что поле {field} не являтся обязательным\n\n"
    )
    public void fieldValidation(String section, String subSection, String field) {
        ui
                .subSectionClick(section,subSection);
        if(subSection.equals("Общие черные списки")){
            ui.tableCellClick("Количество контактов","Глобальный черный список");
        }
                ui.buttonClick("+")
                .inputValidation(field)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия необязательного поля {2} в категории {0}:{1} - {index}")
    @MethodSource("nonRequiredWithPreconditionFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Заполнить необходимые для доступности {nonRequiredField} поля\n\n" +
                    "5. Проверить, что поле {nonRequiredField} не являтся обязательным\n\n"
    )
    public void nonRequiredWithPreconditionFieldCheck(String section, String subSection, String nonRequiredField,
                                                            String preconditionInput, String value) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+");
        if(subSection.equals("Изменение приоритета"))
            ui.inputSet("Транспорт","SMS");
        ui
                .inputSet(preconditionInput, value)
                .inputIsNotRequired(nonRequiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия необязательного поля {2} в категории {0}:{1} - {index}")
    @MethodSource("nonRequiredWithDoublePreconditionFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Проверить, что поле {nonRequiredField} не являтся обязательным\n\n"
    )
    public void nonRequiredWithDoublePreconditionFieldCheck(String section, String subSection, String nonRequiredField,
    String preconditionInput, String value, String preconditionInput2, String value2) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+")
                .inputSet(preconditionInput, value)
                .inputSet(preconditionInput2, value2)
                .inputIsNotRequired(nonRequiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия обязательного поля {0} в категории {2}:{1} - {index}")
    @MethodSource("requiredFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Проверить, что поле {requiredField} являтся обязательным\n\n"
    )
    public void requiredFieldCheck(String section, String subSection, String requiredField) {
        ui
                .subSectionClick(section,subSection);
        if(subSection.equals("Общие черные списки"))
            ui.tableCellClick("Количество контактов","Глобальный черный список");
                ui.buttonClick("+");
                if(subSection.equals("Клиенты") || subSection.equals("Поставщики услуг")){
                    ui.radioButtonOn("SMS");
                }
                if(requiredField.equals("Часовой пояс") ||
                requiredField.equals("Устройства")){
                    ui
                            .waiting(0.2f)
                            .inputClear(requiredField);
                }
                ui.inputIsRequired(requiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия обязательного поля {2} в категории {0}:{1} - {index}")
    @MethodSource("requiredWithPreconditionFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Заполнить поле, необходимое для доступности поля {requiredField}\n\n" +
                    "5. Проверить, что поле {requiredField} являтся обязательным\n\n"
    )
    public void requiredWithPreconditionFieldCheck(String section, String subSection, String requiredField, String preconditionInput, String value) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+")
                .inputSet(preconditionInput, value)
                .inputIsRequired(requiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка наличия обязательного поля {2} в категории {0}:{1} - {index}")
    @MethodSource("requiredWithDoublePreconditionFieldsList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Заполнить поля, необходимые для доступности поля {requiredField}\n\n" +
                    "5. Проверить, что поле {requiredField} являтся обязательным\n\n"
    )
    public void requiredWithDoublePreconditionFieldCheck(String section, String subSection, String requiredField,
                                                         String preconditionInput,  String value,
                                                         String preconditionInput2,  String value2) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+")
                .inputSet(preconditionInput, value)
                .inputSet(preconditionInput2, value2)
                .inputIsNotRequired(requiredField)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка функционирования чек-бокса {2} в категории {0}:{1} - {index}")
    @MethodSource("checkBoxesList")
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Нажать кнопку добавления нового элемента\n\n" +
                    "4. Проверить, что существует чекбокс {requiredCheckBox}\n\n"
    )
    public void checkBoxesCheck(String section, String subSection, String requiredCheckBox) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("+")
                .checkBoxExists(requiredCheckBox)
                .isCheckBoxEnabled(requiredCheckBox)
                .checkBoxOff(requiredCheckBox)
                .isCheckBoxOff(requiredCheckBox)
                .checkBoxOn(requiredCheckBox)
                .isCheckBoxOn(requiredCheckBox)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка функционала фильтрации {2} в категории {0}:{1} - {index}")
    @MethodSource({"filtersList"})
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Октрыть фильтры\n\n" +
                    "4. Выполнить сортировку по фильтру {requiredFilter}\n\n" +
                    "5. Проверить корректность фильтрации\n\n"
    )
    public void filtersCheck(String section, String subSection, String requiredFilter) {
        ui
                .subSectionClick(section,subSection)
                .checkFilterSuccesfull(requiredFilter)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка функционала фильтрации {2} в категории {0}:{1} - {index}")
    @MethodSource({"transportFiltersList"})
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Октрыть фильтры\n\n" +
                    "4. Выполнить сортировку по фильтру {requiredFilter}\n\n" +
                    "5. Проверить корректность фильтрации\n\n"
    )
    public void transportFiltersCheck(String section, String subSection, String requiredFilter) {
        ui
                .subSectionClick(section,subSection)
                .settingsOpen()
                .settingsCheckBoxOn("Транспорт")
                .settingsClose()
                .checkFilterSuccesfull(requiredFilter)
        ;
    }

    @Test
    @DisplayName("Добавление \"Изменения адреса отправителя SMS\" с указанным Клиентом")
    @Tag("Mod1")
    public void clientAdd(){
        ui
                .subSectionClick("SMS","Изменение адреса отправителя SMS")
                .buttonClick("Создать")
                .inputSet("Поставщик услуг","ProviderSMSClear3")
                .inputSet("Клиент","ClientSenderAddresses2")
                .inputSet("Адрес отправителя","9353")
                .inputSet("Новый адрес отправителя","123123")
                .buttonClick("Сохранить")
                .tableRowExists("ProviderSMSClear3", "ClientSenderAddresses2","9353 -> 123123");
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка функционала фильтрации {2} в категории {0}:{1} - {index}")
    @MethodSource({"strongFiltersList"})
    @Tag("Admin")
    @Tag("Main")
    @Tag("Mod2")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Октрыть фильтры\n\n" +
                    "4. Выполнить сортировку по фильтру {requiredFilter}\n\n" +
                    "5. Проверить корректность фильтрации\n\n"
    )
    public void strongFiltersCheck(String section, String subSection, String requiredFilter) {
        ui
                .subSectionClick(section,subSection)
                .checkFilterSuccesfullStrong(requiredFilter)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работы контроллера очистки {2} в категории {0}:{1} - {index}")
    @MethodSource({"filtersList"})
    @Tag("Admin")
    @Tag("Main")
    @Description(
            "1. Открыть категорию {section}\n\n" +
                    "2. Зайти на страницу {subsection}\n\n" +
                    "3. Октрыть фильтры\n\n" +
                    "4. Выполнить сортировку по фильтру {requiredTag}\n\n" +
                    "5. Проверить работу контроллера очистки\n\n"
    )
    public void tagsCheck(String section, String subSection, String requiredTag) {
        ui
                .subSectionClick(section,subSection)
                .buttonClick("f")
                .waiting(0.5f);
        if(subSection.equals("Ограничение количества отправляемых сообщений")){
            ui.inputSet("Транспорт","SMS");
        }
                ui.inputFill(requiredTag)
                .waiting(0.5f)
                .titleClick()
                .buttonClick("a")
                .tagExists(requiredTag)
                .tagContainsInputValue(requiredTag)
                .tagClear(requiredTag)
                .tagNotExists(requiredTag)
                .inputFill(requiredTag)
                .titleClick()
                .buttonClick("a")
                .buttonClick("Очистить фильтры")
                .tagNotExists(requiredTag)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка возвожности редактирования поля \"{2}\" в категории \"{0}\" на странице \"{1}\" - {index}")
    @MethodSource({"rowsForEditAndCheckByTableList"})
    @Tag("Admin")
    @Tag("Main")
    public void editRowCheckWithTable(String section, String subSection, String inputForFilter, String valueForFilter, String inputForEdit, String oldValue, String newValue) {
        String resultFilterValue = valueForFilter;
        if (inputForEdit.equals(inputForFilter)) resultFilterValue = newValue;
        Allure.description("1. Открыть категорию "+section+"\n\n" +
                "2. Зайти на страницу "+subSection+"\n\n" +
                "3. Октрыть фильтры\n\n" +
                "4. Выполнить сортировку по фильтру "+inputForFilter+": "+valueForFilter+"\n\n" +
                "5. Зайти в редактирование записи\n\n"+
                "6. Изменить "+oldValue+" на "+newValue+" в поле "+inputForEdit+"\n\n"+
                "7. Сохранить изменения\n\n"+
                "8. Выполнить сортировку по фильтру "+inputForFilter+": "+resultFilterValue+"\n\n" +
                "9. Проверить, что запись обновлена\n\n");
        ui
                .subSectionClick(section,subSection)
                .buttonClick("f")
                .inputSet(inputForFilter, valueForFilter)
                .buttonClick("a")
                .tableHrefCellClick(valueForFilter)
                .inputSet(inputForEdit, newValue)
                .buttonClick("s")
                .buttonClick("f")
                .inputSet(inputForFilter, resultFilterValue)
                .buttonClick("a")
                .tableRowExists(resultFilterValue)
                .tableHrefCellClick(resultFilterValue)
                .inputContains(inputForEdit, newValue)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка возвожности редактирования поля \"{2}\" в категории \"{0}\" на странице \"{1}\" - {index}")
    @MethodSource({"rowsForEditAndCheckWithoutTable"})
    @Tag("Admin")
    @Tag("Main")
    @Tag("Mod1")
    public void editRowCheck(String section, String subSection, String channel, String inputForFilter, String valueForFilter, String inputForEdit, String oldValue, String newValue) {
        Allure.description("1. Открыть категорию "+section+"\n\n" +
                "2. Зайти на страницу "+subSection+"\n\n" +
                "3. Октрыть фильтры\n\n" +
                "4. Выполнить сортировку по фильтру "+inputForFilter+": "+valueForFilter+"\n\n" +
                "5. Зайти в редактирование записи\n\n"+
                "6. Изменить "+oldValue+" на "+newValue+" в поле "+inputForEdit+"\n\n"+
                "7. Сохранить изменения\n\n"+
                "8. Зайти в редактирование записи\n\n" +
                "9. Проверить, что поле "+inputForEdit+" содержит "+newValue+"\n\n");

        if (channel.equals("Call") && subSection.equals("Блокировка одинаковых сообщений"))
            ui.setNotDefinedStatus("Звонки будут в 1.x");

        ui
                .subSectionClick(section,subSection)

                .buttonClick("f");

        if (!channel.isEmpty()) {
            ui.inputSet("Транспорт", channel);
        }

        ui
                .inputSet(inputForFilter, valueForFilter)
                .buttonClick("a")

                .tableHrefCellClick(valueForFilter)

                .inputSet(inputForEdit, newValue)
                .buttonClick("s")

                .tableHrefCellClick(valueForFilter)
                .inputContains(inputForEdit, newValue)
        ;
    }

    @Epic("Консоль администратора")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка возвожности редактирования поля \"{2}\" в категории \"{0}\" на странице \"{1}\" - {index}")
    @MethodSource({"rowsForEditCheck"})
    @Tag("Admin")
    @Tag("Main")
    @Tag("Mod2")
    public void editRowCheck2(String section, String subSection, String channel, String inputForFilter, String valueForFilter, String inputForEdit, String oldValue, String newValue) {
        Allure.description("1. Открыть категорию "+section+"\n\n" +
                "2. Зайти на страницу "+subSection+"\n\n" +
                "3. Октрыть фильтры\n\n" +
                "4. Выполнить сортировку по фильтру "+inputForFilter+": "+valueForFilter+"\n\n" +
                "5. Зайти в редактирование записи\n\n"+
                "6. Изменить "+oldValue+" на "+newValue+" в поле "+inputForEdit+"\n\n"+
                "7. Сохранить изменения\n\n"+
                "8. Зайти в редактирование записи\n\n" +
                "9. Проверить, что поле "+inputForEdit+" содержит "+newValue+"\n\n");

        if (channel.equals("Call") && subSection.equals("Блокировка одинаковых сообщений"))
            ui.setNotDefinedStatus("Звонки будут в 1.x");

        ui
                .subSectionClick(section,subSection)
                .buttonClick("f");

        if (!channel.isEmpty()) {
            ui.inputSet("Транспорт", channel);
        }

        ui
                .inputSet(inputForFilter, valueForFilter)
                .buttonClick("a")

                .tableHrefCellClick(valueForFilter)

                .inputSet(inputForEdit, newValue)
                .buttonClick("Сохранить")

                .buttonClickIfExists("Очистить фильтры")
                .filterSet("Транспорт",channel)
                .filterSet("Клиент",newValue)

                .tableHrefCellClick(channel,newValue)
                .inputContains(inputForEdit, newValue)
        ;
    }

    public static Stream<Arguments> rowsForEditAndCheckByTableList() {
        return Stream.of(
                Arguments.of("Настройки", "Настройки системы", "Название", "SysGEdit", "Название", "SysGEdit", "SysGEdited")
        );
    }

    @Epic("Проверка работоспособности прав Ролей пользователей системы")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работы прав у роли на страницу \"{0}\" - {index}")
    @MethodSource({"rolesGeneralProviderMethod"})
    @Tag("Admin")
    @Tag("Main")
    public void rolesGeneralWithRole(String page) {
        DataGenerator dg = new DataGenerator();
        String name = dg.genNameWithMiddle();
        String password = dg.genPassword();
        String email = dg.genEmail();
        Allure.description("1. Создать роль с доступом к странице "+page+"\n\n" +
                "2. Создать пользователя "+name+" с этой ролью\n\n" +
                "3. Выполнить вход в панель администратора, используя созданного пользователя\n\n" +
                "4. Ожидается: Страница "+page+" является доступной\n\n");

        ui
                .subSectionClick("Управление пользователями","Роли")
                .buttonClick("+")
        ;
        String[] primaryCheckBoxList = {"SMS","Звонки","Электронная почта","Push","Viber","WhatsApp","Mail Notify","Custom","Настройки","Управление финансами"};
        for (String s : primaryCheckBoxList) {
            ui.checkBoxPrimaryOff(s).imageButtonOfListClick(s)
                    .checkBoxOff(s);
        }
        ui
                .inputSet("Название", name+"Role")
                .checkBoxOn(page)
                .buttonClick("s")
                .subSectionClick("Управление пользователями","Пользователи системы")
                .buttonClick("+")
                .inputSet("Название", name)
                .radioButtonOn("Использовать роль")
                .inputSet("Роль", name+"Role")
                .inputSet("Электронная почта", email)
                .inputSet("Пароль", password)
                .inputSet("Подтвердить пароль", password)
                .buttonClick("s")

                .logout()
                .loginAcui(email, password)
                .subSectionOrSectionExists(page)

                .logout()
                .loginAcuiRandom()
                .subSectionClick("Управление пользователями","Пользователи системы")
                .filterSet("Название",name)
                .delete(name)
                .subSectionClick("Управление пользователями","Роли")
                .filterSet("Название",name+"Role")
                .delete(name+"Role")
                ;
    }

    @Epic("Проверка работоспособности прав Ролей для личного кабинета")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работы прав у роли на страницу \"{0}\" - {index}")
    @MethodSource({"rolesLKGeneralProviderMethod"})
    @Tag("Admin")
    @Tag("Main")
    public void lkuiRolesGeneralWithRole(String page) {
        DataGenerator dg = new DataGenerator();
        String name = dg.genNameWithMiddle();
        String password = dg.genPassword();
        String email = dg.genEmail();
        Allure.description("1. Создать роль лк с доступом к разделу "+page+"\n\n" +
                "2. Создать пользователя "+name+" с этой ролью\n\n" +
                "3. Выполнить вход в личный кабинет, используя созданного пользователя\n\n" +
                "4. Ожидается: Страница "+page+" является доступной\n\n");

        ui
                .subSectionClick("Управление пользователями","Роли пользователей личного кабинета")
                .buttonClick("+")
        ;
      /*  String[] primaryCheckBoxList = {
                "Шаблоны",
                "Рассылки",
                "Отправители",
                "Контакты",
                "Детальная статистика",
                "Групповая статистика",
                "Отчеты по экспорту",
                "Управление финансами",
                "Аккаунты"};
        for (String s : primaryCheckBoxList) {
            ui.checkBoxPrimaryOff(s);
        }*/
        ui
                .inputSet("Название", name+"Role")
                .checkBoxOn(page)
                .buttonClick("s")
                .subSectionClick("Управление пользователями","Пользователи личного кабинета")
                .buttonClick("+")
                .inputSet("Имя", name)
                .inputSet("Клиент", "ClientAll")
                .radioButtonOn("Использовать роль")
                .inputSet("Роль", name+"Role")
                .inputSet("Почта", email)
                .inputSet("Пароль", password)
                .radioButtonOn("Статус")
                .buttonClick("Сохранить")

                .logout()
                .loginLkui(email, password)
                .sectionExists(page)

                .logout()
                .loginAcuiRandom()
                .subSectionClick("Управление пользователями","Пользователи личного кабинета")
                .filterSet("Название",name)
                .delete(name)
                .subSectionClick("Управление пользователями","Роли пользователей личного кабинета")
                .filterSet("Название",name+"Role")
                .delete(name+"Role")
        ;
    }

    @Epic("Проверка работоспособности прав пользователей для личного кабинета")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работы прав у пользователя на страницу \"{0}\" - {index}")
    @MethodSource({"rolesLKGeneralProviderMethod"})
    @Tag("Admin")
    @Tag("Main")
    public void lkuiRolesGeneral(String page) {
        DataGenerator dg = new DataGenerator();
        String name = dg.genNameWithMiddle();
        String password = dg.genPassword();
        String email = dg.genEmail();
        Allure.description("1. Создать роль лк с доступом к разделу "+page+"\n\n" +
                "2. Создать пользователя "+name+" с этой ролью\n\n" +
                "3. Выполнить вход в личный кабинет, используя созданного пользователя\n\n" +
                "4. Ожидается: Страница "+page+" является доступной\n\n");

        ui
                .subSectionClick("Управление пользователями","Пользователи личного кабинета")
                .buttonClick("+")
        ;
        String[] primaryCheckBoxList = {
                "Шаблоны",
                "Рассылки",
                "Отправители",
                "Контакты",
                "Детальная статистика",
                "Групповая статистика",
                "Отчёты по экспорту",
                "Управление финансами",
                "Аккаунты"};
        for (String s : primaryCheckBoxList) {
            ui.checkBoxPrimaryOff(s);
        }
        ui
                .checkBoxOn(page)
                .inputSet("Имя", name)
                .inputSet("Клиент", "ClientAll")
                .inputSet("Почта", email)
                .inputSet("Пароль", password)
                .radioButtonOn("Статус")
                .buttonClick("Сохранить")

                .logout()
                .loginLkui(email, password)
                .sectionExists(page)

                .logout()
                .loginAcuiRandom()
                .subSectionClick("Управление пользователями","Пользователи личного кабинета")
                .filterSet("Название",name)
                .delete(name)
        ;
    }

    public static Stream<Arguments> rolesLKGeneralProviderMethod() {
        return Stream.of(
                Arguments.of("Шаблоны"),
                Arguments.of("Рассылки"),
                Arguments.of("Отправители"),
                Arguments.of("Контакты"),
                Arguments.of("Детальная статистика"),
                Arguments.of("Групповая статистика"),
                Arguments.of("Отчёты по экспорту"),
                Arguments.of("Управление финансами"),
                Arguments.of("Аккаунты")
        );
    }

    @Epic("Проверка работоспособности прав пользователей системы")
    @Feature("{section}")
    @Story("{subsection}")
    @ParameterizedTest(name = "Проверка работы прав у роли на страницу \"{0}\" - {index}")
    @MethodSource({"rolesGeneralProviderMethod"})
    @Tag("Admin")
    @Tag("Main")
    public void rolesGeneral(String page) {
        DataGenerator dg = new DataGenerator();
        String name = dg.genNameWithMiddle();
        String password = dg.genPassword();
        String email = dg.genEmail();
        Allure.description("1. Создать пользователя "+name+" с доступом к странице "+page+"\n\n" +
                "2. Выполнить вход в панель администратора, используя созданного пользователя\n\n" +
                "3. Ожидается: Страница "+page+" является доступной\n\n");

        ui
                .subSectionClick("Управление пользователями","Пользователи системы")
                .buttonClick("+")
        ;
        String[] primaryCheckBoxList = {"SMS","Звонки","Электронная почта","Push","Viber","WhatsApp","Mail Notify","Настройки","Управление пользователями","Управление финансами"};
        for (String s : primaryCheckBoxList) {
            ui.checkBoxPrimaryOff(s).imageButtonOfListClick(s);
        }
        ui
                .inputSet("Название", name)

                .inputSet("Электронная почта", email)
                .inputSet("Пароль", password)
                .inputSet("Подтвердить пароль", password)
                .buttonClick("s")

                .logout()
                .loginAcui(email, password)
                .subSectionOrSectionExists(page)

                .logout()
                .loginAcuiRandom()
                .subSectionClick("Управление пользователями","Пользователи системы")
                .filterSet("Название",name)
                .delete(name)
        ;
    }
    public static Stream<Arguments> rolesGeneralProviderMethod() {
        return Stream.of(
                Arguments.of("SMS"),
                Arguments.of("Детальная статистика SMS"),
                Arguments.of("Групповая статистика SMS"),
                Arguments.of("Изменение адреса отправителя"),
//                Arguments.of("Блокировка по тексту"),
                Arguments.of("Изменение приоритета"),
                Arguments.of("Шаблоны"),
                Arguments.of("Маршрутизация входящих сообщений"),
                Arguments.of("Детальная статистика входящих SMS"),
                Arguments.of("Групповая статистика входящих SMS"),
                Arguments.of("Ограничения по номерам отправителей"),
                Arguments.of("Маршрутизация и тарификация SMS-клиентов"),
                Arguments.of("Маршрутизация и тарификация SMS-поставщиков услуг"),

                Arguments.of("Звонки"),
                Arguments.of("Библиотека звуков"),
                Arguments.of("IVR меню"),
                Arguments.of("Детальная статистика звонков"),
                Arguments.of("Групповая статистика звонков"),
                Arguments.of("Шаблоны"),
                Arguments.of("Изменение адреса отправителя"),
                Arguments.of("Детальная статистика входящих звонков"),
                Arguments.of("Групповая статистика входящих звонков"),
                Arguments.of("Маршрутизация и тарификация Call клиентов"),
                Arguments.of("Маршрутизация и тарификация Call поставщиков услуг"),

                Arguments.of("Электронная почта"),
                Arguments.of("Детальная статистика Email"),
                Arguments.of("Групповая статистика Email"),
                Arguments.of("Шаблоны"),
                Arguments.of("Тарификация Email клиентов"),
                Arguments.of("Тарификация Email поставщиков услуг"),
                Arguments.of("Групповая статистика событий Email"),
//
                Arguments.of("Push"),
                Arguments.of("Управление приложениями"),
                Arguments.of("Атрибуты приложений"),
                Arguments.of("Тарификация Push-клиентов"),
                Arguments.of("Детальная статистика Push"),
                Arguments.of("Групповая статистика Push"),
                Arguments.of("Шаблоны"),
                Arguments.of("Количество подписчиков"),
                Arguments.of("История подписок"),
                Arguments.of("Группы приложений"),

                Arguments.of("Viber"),
                Arguments.of("Шаблоны"),
                Arguments.of("Групповая статистика Viber"),
                Arguments.of("Детальная статистика Viber"),
                Arguments.of("Тарификация Viber клиентов"),
                Arguments.of("Тарификация Viber поставщиков услуг"),
                Arguments.of("Маршрутизация входящих сообщений"),
                Arguments.of("Детальная статистика входящих Viber"),
                Arguments.of("Групповая статистика входящих Viber"),

                Arguments.of("WhatsApp"),
                Arguments.of("Шаблоны"),
                Arguments.of("Детальная статистика"),
                Arguments.of("Групповая статистика"),
                Arguments.of("Тарификация WhatsApp клиентов"),
                Arguments.of("Тарификация WhatsApp поставщиков услуг"),
                Arguments.of("Маршрутизация входящих сообщений"),
                Arguments.of("Детальная статистика входящих WhatsApp"),
                Arguments.of("Групповая статистика входящих WhatsApp"),

                Arguments.of("Mail Notify"),
                Arguments.of("Операторские шаблоны Mail Notify"),
                Arguments.of("Операторские сервисы Mail Notify"),
                Arguments.of("Шаблоны"),
                Arguments.of("Детальная статистика"),
                Arguments.of("Групповая статистика"),
                Arguments.of("Тарификация Mail Notify клиентов"),
                Arguments.of("Тарификация Mail Notify поставщиков услуг"),

                Arguments.of("Custom"),
                Arguments.of("Тарификация Custom клиентов"),
                Arguments.of("Маршрутизация входящих сообщений"),
                Arguments.of("Тарификация Custom поставщиков услуг"),
                Arguments.of("Шаблоны"),
                Arguments.of("Групповая статистика"),
                Arguments.of("Детальная статистика"),

                Arguments.of("Настройки"),
                Arguments.of("Аккаунты"),
                Arguments.of("Адреса отправителей"),
                Arguments.of("Клиенты"),
                Arguments.of("Поставщики услуг"),
                Arguments.of("Настройки системы"),
                Arguments.of("Пулы номеров"),
                Arguments.of("Подключения поставщиков услуг"),
                Arguments.of("Подключения клиентов"),
                Arguments.of("Отчеты по экспорту"),
                Arguments.of("Блокировка одинаковых сообщений"),
                Arguments.of("Подключения к операторам по SMPP"),
                Arguments.of("Черные списки"),
                Arguments.of("Типы сообщений"),
                Arguments.of("Дополнительные правила"),
                Arguments.of("Общие черные списки"),
                Arguments.of("Категории сообщений"),
                Arguments.of("Ограничение количества отправляемых сообщений"),
                Arguments.of("Прокси-серверы"),
                Arguments.of("Белый список"),
//
                Arguments.of("Управление пользователями"),
                Arguments.of("Модерация рассылок"),
                Arguments.of("Настройки логирования действий пользователей"),
                Arguments.of("Модерация шаблонов"),
                Arguments.of("Пользователи колл-центра"),
                Arguments.of("Действия пользователей"),
                Arguments.of("Пользователи личного кабинета"),
                Arguments.of("Роли пользователей личного кабинета"),
                Arguments.of("Роли"),
                Arguments.of("Пользователи системы"),
                Arguments.of("Управление финансами"),
                Arguments.of("История пополнений"),
                Arguments.of("Балансы клиентов")
                );
    }
    public static Stream<Arguments> rowsForEditAndCheckWithoutTable() {
        return Stream.of(
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "SMS", "Клиент", "ClientGEditSMS", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "SMS", "Клиент", "ClientGEditSMS", "Таймаут", "6", "16"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Call", "Клиент", "ClientGEditCall", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Call", "Клиент", "ClientGEditCall", "Таймаут", "7", "17"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Viber", "Клиент", "ClientGEditViber", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Viber", "Клиент", "ClientGEditViber", "Таймаут", "8", "18"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "WhatsApp", "Клиент", "ClientGEditWhatsApp", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "WhatsApp", "Клиент", "ClientGEditWhatsApp", "Таймаут", "9", "19"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Email", "Клиент", "ClientGEditEmail", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Email", "Клиент", "ClientGEditEmail", "Таймаут", "10", "20"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Mail Notify", "Клиент", "ClientGEditMailNotify", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Mail Notify", "Клиент", "ClientGEditMailNotify", "Таймаут", "11", "21"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Custom", "Клиент", "ClientGEditCustom", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Custom", "Клиент", "ClientGEditCustom", "Таймаут", "12", "22"),

                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Push", "Клиент", "ClientGEditPush", "Шаблон",  "~patGEdit", "LKPat"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Push", "Клиент", "ClientGEditPush", "Таймаут", "13", "23")
                );
    }

    public static Stream<Arguments> rowsForEditCheck(){
        return Stream.of(
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "SMS", "Клиент", "ClientGEditSMS", "Клиент", "ClientGEditSMS", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Call", "Клиент", "ClientGEditCall", "Клиент", "ClientGEditCall", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Viber", "Клиент", "ClientGEditViber", "Клиент", "ClientGEditViber", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "WhatsApp", "Клиент", "ClientGEditWhatsApp", "Клиент", "ClientGEditWhatsApp", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Email", "Клиент", "ClientGEditEmail", "Клиент", "ClientGEditEmail", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Mail Notify", "Клиент", "ClientGEditMailNotify", "Клиент", "ClientGEditMailNotify", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Custom", "Клиент", "ClientGEditCustom", "Клиент", "ClientGEditCustom", "ModClient"),
                Arguments.of("Настройки", "Блокировка одинаковых сообщений", "Push", "Клиент", "ClientGEditPush", "Клиент", "ClientGEditPush", "ModClient")
        );
    }
}
