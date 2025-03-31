package ru.wsoft.tests.ui.adm.core;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.wsoft.tests.ui.adm.BaseConsoleTest;

import java.io.File;
import java.io.IOException;

public class MainTests extends BaseConsoleTest {

    @Epic("Панель администратора")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Генерация json")
    @Tag("Disable")
    @Test
    @Tag("Main")
    public void wcsTest() {

        try {
            new ObjectMapper().writeValue(new File("wcs.json"), "ы" );

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonWCS = new JSONObject();
        ElementsCollection sections = ui.getSections();
        for (SelenideElement section : sections) {
            String sectionName = section.$x(".//p").getText();
            JSONObject jsonSubSections = new JSONObject();
            ElementsCollection subSections = ui.getSubSections(section);
            for (SelenideElement subSection : subSections) {
                String subSectionName = subSection.$x(".//span[@class='sidebar-normal']").getText();
                ui.subSectionClick(sectionName, subSectionName);
                JSONObject jsonInputs = new JSONObject();
                subSection.scrollTo().click();
                ui.buttonSoftClick("+");
                ElementsCollection inputs = ui.getInputs();
                for (SelenideElement input : inputs) {
                    JSONArray inputAttr = new JSONArray();
                    if (input.$$x("./label").size()<2) continue;
                    SelenideElement inputLabel = input.$x("./label");
                    Boolean isInput = true;
                    if (input.$x("./descendant::input[@class!='md-input-fake']").attr("class").contains("md-select-value"))
                        inputAttr.add("Селектор");
                    else if (input.$x("./descendant::input[@class!='md-input-fake']").attr("type").contains("checkbox")) {
                        inputAttr.add("Чекбокс");
                        isInput = false;
                    }
                    else if (input.$$x("./descendant::textarea").size()>0) {
                        inputAttr.add("Текстблок");
                        isInput = false;
                    }
                    else inputAttr.add("ПростоеПоле");

                    if (!inputLabel.attr("class").contains("md-disabled") && isInput) {
                       // ui.inputMustBeRequired(input);
                        inputAttr.add("Обязательное");
                    }
                    else inputAttr.add("Необязательное");

                    jsonInputs.put(inputLabel.getText(), inputAttr);
                }
                ui.buttonSoftClick("Назад к списку").buttonSoftClick("Нет");
                jsonSubSections.put(subSectionName, jsonInputs);
            }
            jsonWCS.put(sectionName, jsonSubSections);
        }
        try {
            new ObjectMapper().writeValue(new File("wcs.json"), jsonWCS );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
