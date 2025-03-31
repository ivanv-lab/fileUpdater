package core;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

public class SyncButtonsTests {

    private final String cacheService1 = "WCS:group=Services,instance-type=Cache,name=cache-service1";

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации шаблонов (templates)")
    @Test
    @Tag("CacheSyncButton")
    public void checkTemplatesSyncButton(){
        ArrayList<String> templatesList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            templatesList= sql.getArrayFromDB("BULK_DISTR_TEMPLATES.NAME,TRANSPORTS.NAME,BULK_DISTR_TEMPLATES.ID","BULK_DISTR_TEMPLATES",
                    "LEFT JOIN TRANSPORTS ON BULK_DISTR_TEMPLATES.TRANSPORT_ID = TRANSPORTS.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляем первую запись в кэше если есть хоть одна
        if (!templatesList.isEmpty()){
            String[] toDelete = templatesList.get(0).split(",");
            cache.removeByKey(cacheService1,"templates",toDelete[2]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. шаблоны")
                .confirm()
        ;
        //Разбиваю полученные строки в БД на отдельные параметры и проверяю в кэше
        for (String s : templatesList) {
            String[] templateFields = s.split(",");
            cache.checkTemplates(templateFields[0],templateFields[1]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (clients)")
    @Test
    @Tag("CacheSyncButton")
    public void checkClientsSyncButton(){
        ArrayList<String> clientsList;
        ArrayList<String> transportsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            clientsList= sql.getArrayFromDB("ID,NAME,IS_ACTIVE,PREPAID","PARTNERS",
                    "");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!clientsList.isEmpty()){
            String[] toDelete = clientsList.get(0).split(",");
            cache.removeByKey(cacheService1,"clients",toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Для каждого клиента получаю доступные транспорты и отправляю на проверку кэша
        sql = new SQLHandler();
        try {
            for (String s : clientsList) {
                String[] clientsFields = s.split(",");
                transportsList = sql.getArrayFromDB("TRANSPORTS.NAME","PARTNER_TRANSPORTS",
                        "LEFT JOIN TRANSPORTS ON PARTNER_TRANSPORTS.TRANSPORT_ID = TRANSPORTS.ID " +
                                "WHERE PARTNER_ID = " + clientsFields[0]);
                for(String tr : transportsList){
                    cache.checkClients(clientsFields[1],clientsFields[2],tr,clientsFields[3]);
                }
            }
        } finally {
            sql.disconnect();
        }
    }


    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (providers)")
    @Test
    @Tag("CacheSyncButton")
    public void checkProvidersSyncButton(){
        ArrayList<String> providersList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            providersList= sql.getArrayFromDB("ID,NAME","SERVICE_PROVIDERS",
                    "");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!providersList.isEmpty()){
            String[] toDelete = providersList.get(0).split(",");
            cache.removeByKey(cacheService1,"providers",toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : providersList) {
            String[] providerFields = s.split(",");
            cache.checkProviders(providerFields[1]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (settings)")
    @Test
    @Tag("CacheSyncButton")
    public void checkSettingsSyncButton(){
        ArrayList<String> settingsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            settingsList= sql.getArrayFromDB("NAME,VALUE","SYSTEM_SETTINGS",
                    "");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!settingsList.isEmpty()){
            String[] toDelete = settingsList.get(0).split(",");
            cache.removeByKey(cacheService1,"settings",toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : settingsList) {
            String[] settingFields = s.split(",");
            if (!settingFields[1].equalsIgnoreCase("null")){
                cache.checkSettings(settingFields[0],settingFields[1]);
            }
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (user-settings)")
    @Test
    @Tag("CacheSyncButton")
    public void checkUserSettingsSyncButton(){
        ArrayList<String> providersList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            providersList= sql.getArrayFromDB("EMAIL,OFFSET_TZ","PARTNER_USERS",
                    "LEFT JOIN TIMEZONES ON PARTNER_USERS.TIMEZONE_ID=TIMEZONES.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!providersList.isEmpty()){
            String[] toDelete = providersList.get(0).split(",");
            cache.removeByKey(cacheService1,"user-settings",toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : providersList) {
            String[] userFields = s.split(",");
            cache.checkUserSettings(userFields[0],Integer.parseInt(userFields[1]));
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (allowed-senders)")
    @Test
    @Tag("CacheSyncButton")
    public void checkAllowedSendersSyncButton(){
        ArrayList<String> sendersList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            sendersList= sql.getArrayFromDB("SENDER_ADDRESSES.NAME,PARTNERS.NAME,TRANSPORTS.NAME,SENDER_STATUSES.ID," +
                            "PARTNERS.ID",
                    "SENDER_ADDRESSES",
                    "LEFT JOIN TRANSPORTS ON SENDER_ADDRESSES.TRANSPORT_ID=TRANSPORTS.ID " +
                            "LEFT JOIN SENDER_STATUSES ON SENDER_ADDRESSES.STATUS_ID=SENDER_STATUSES.ID " +
                            "LEFT JOIN PARTNERS ON SENDER_ADDRESSES.PARTNER_ID=PARTNERS.ID " +
                            "LEFT JOIN SENDER_STATUSES ON SENDER_ADDRESSES.STATUS_ID=SENDER_STATUSES.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!sendersList.isEmpty()){
            String[] toDelete = sendersList.get(0).split(",");
            cache.removeByKey(cacheService1,"allowed-senders",toDelete[4]+"_"+toDelete[2].toLowerCase()+"_"+toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : sendersList) {
            String[] senderFields = s.split(",");
            cache.checkAllowedSenders(senderFields[0],senderFields[1],senderFields[2],senderFields[3]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (accounts)")
    @Test
    @Tag("CacheSyncButton")
    public void checkAccountsSyncButton(){
        ArrayList<String> accountsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            accountsList= sql.getArrayFromDB("PROTOCOLS.NAME,PARTNERS.NAME,BROKER_ACCOUNTS.NAME," +
                            "BROKER_ACCOUNTS.LOGIN,BROKER_ACCOUNTS.URL,BROKER_ACCOUNTS.STATUS_REPORTS,BROKER_ACCOUNTS.IS_ACTIVE,TRANSPORTS.NAME",
                    "BROKER_ACCOUNTS",
                    "LEFT JOIN PROTOCOLS ON BROKER_ACCOUNTS.PROTOCOL_ID=PROTOCOLS.ID " +
                            "LEFT JOIN PARTNERS ON BROKER_ACCOUNTS.PARTNER_ID=PARTNERS.ID " +
                            "LEFT JOIN TRANSPORTS ON BROKER_ACCOUNTS.TRANSPORT_ID=TRANSPORTS.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!accountsList.isEmpty()){
            String[] toDelete = accountsList.get(0).split(",");
            cache.removeByKey(cacheService1,"accounts",toDelete[2]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : accountsList) {
            String[] accountFields = s.split(",");
            cache.checkAccounts(accountFields[0],accountFields[1],accountFields[2],accountFields[3],accountFields[4],
                    accountFields[5],accountFields[6],accountFields[7]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (black-lists)")
    @Test
    @Tag("CacheSyncButton")
    public void checkBlackListsSyncButton(){
        ArrayList<String> blackListSubsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            blackListSubsList= sql.getArrayFromDB("NAME,MSISDN,EMAIL",
                    "BULK_BLACKLISTS_SUBSCRIBERS",
                    "LEFT JOIN BULK_BLACKLISTS ON BULK_BLACKLISTS_SUBSCRIBERS.BLACKLIST_ID=BULK_BLACKLISTS.ID " +
                            "WHERE BULK_BLACKLISTS_SUBSCRIBERS.BLACKLIST_ID IS NOT NULL");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!blackListSubsList.isEmpty()){
            String[] toDelete = blackListSubsList.get(0).split(",");
            if(!toDelete[1].equals("null")) {
                cache.removeByKey(cacheService1,"black-lists",toDelete[1]);
            }
            if(!toDelete[2].equals("null")) {
                cache.removeByKey(cacheService1,"black-lists",toDelete[2]);
            }
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : blackListSubsList) {
            String[] blackListFields = s.split(",");
            if(blackListFields[1].equals("null")) blackListFields[1]="-";
            if(blackListFields[2].equals("null")) blackListFields[2]="-";
            cache.checkBlackLists(blackListFields[0],blackListFields[1],blackListFields[2]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (mt-duplicates)")
    @Test
    @Tag("CacheSyncButton")
    public void checkMTDuplicatesSyncButton(){
        ArrayList<String> duplicatesList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            duplicatesList= sql.getArrayFromDB("BULK_DISTR_TEMPLATES.NAME,PARTNERS.NAME,TRANSPORTS.NAME,DUPLICATE_RULES.TIMEOUT," +
                            "PARTNERS.ID,BULK_DISTR_TEMPLATES.ID",
                    "DUPLICATE_RULES",
                    "LEFT JOIN BULK_DISTR_TEMPLATES ON DUPLICATE_RULES.TEMPLATE_ID=BULK_DISTR_TEMPLATES.ID " +
                            "LEFT JOIN PARTNERS ON DUPLICATE_RULES.PARTNER_ID=PARTNERS.ID " +
                            "LEFT JOIN TRANSPORTS ON DUPLICATE_RULES.TRANSPORT_ID=TRANSPORTS.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!duplicatesList.isEmpty()){
            String[] toDelete = duplicatesList.get(0).split(",");
            String key=toDelete[4]+"_";
            if(toDelete[5].equalsIgnoreCase("null")){
                key+="0_";
            } else {
                key+=toDelete[5]+"_";
            }
            if(toDelete[2].equalsIgnoreCase("null")){
                key+="0";
            } else {
                key+=toDelete[2].toLowerCase();
            }
            cache.removeByKey(cacheService1,"mt-duplicates",key);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : duplicatesList) {
            String[] duplicateFields = s.split(",");
            if(!duplicateFields[0].equals("null")){
                cache.checkMtDuplicates(duplicateFields[0],duplicateFields[1],duplicateFields[2],duplicateFields[3]);
            }
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (allowed-applications)")
    @Test
    @Tag("CacheSyncButton")
    public void checkAllowedApplicationsSyncButton(){
        ArrayList<String> appsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            appsList= sql.getArrayFromDB("PARTNERS.NAME,PUSH_APPLICATIONS.NAME,PARTNERS.ID",
                    "PARTNER_APPLICATIONS",
                    "LEFT JOIN PARTNERS ON PARTNER_APPLICATIONS.PARTNER_ID=PARTNERS.ID " +
                            "LEFT JOIN PUSH_APPLICATIONS ON PARTNER_APPLICATIONS.APP_ID=PUSH_APPLICATIONS.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!appsList.isEmpty()){
            String[] toDelete = appsList.get(0).split(",");
            cache.removeByKey(cacheService1,"allowed-applications",toDelete[2]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : appsList) {
            String[] appFields = s.split(",");
            cache.checkAllowedApplications(appFields[0],appFields[1]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (mn-allowed-services)")
    @Test
    @Tag("CacheSyncButton")
    public void checkMNAllowedServicesSyncButton(){
        ArrayList<String> servicesList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            servicesList= sql.getArrayFromDB("PARTNERS.NAME,MN_SERVICES.NAME,MN_SERVICES.IS_ACTIVE,PARTNERS.ID",
                    "MN_ALLOWED_SERVICES",
                    "LEFT JOIN PARTNERS ON MN_ALLOWED_SERVICES.PARTNER_ID=PARTNERS.ID " +
                            "LEFT JOIN MN_SERVICES ON MN_ALLOWED_SERVICES.MN_SERVICE_ID=MN_SERVICES.ID");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!servicesList.isEmpty()){
            String[] toDelete = servicesList.get(0).split(",");
            cache.removeByKey(cacheService1,"mn-allowed-services",toDelete[3]+"_"+toDelete[1]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : servicesList) {
            String[] serviceFields = s.split(",");
            cache.checkNMAllowedServices(serviceFields[0],serviceFields[1],serviceFields[2]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (modules)")
    @Test
    @Tag("CacheSyncButton")
    public void checkModulesSyncButton(){
        ArrayList<String> modulesList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            modulesList= sql.getArrayFromDB("PROVIDER_PARAMETERS.VALUE,SERVICE_PROVIDERS.NAME",
                    "PROVIDER_PARAMETERS",
                    "LEFT JOIN SERVICE_PROVIDERS ON PROVIDER_PARAMETERS.PROVIDER_ID=SERVICE_PROVIDERS.ID " +
                            "WHERE PROVIDER_PARAMETERS.PARAMETER_ID=1");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!modulesList.isEmpty()){
            String[] toDelete = modulesList.get(0).split(",");
            cache.removeByKey(cacheService1,"modules",toDelete[0]);
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : modulesList) {
            String[] moduleFields = s.split(",");
            cache.checkModules(moduleFields[0],moduleFields[1]);
        }
    }

    @Feature("CacheSyncButton")
    @Tag("UI")
    @Tag("Admin")
    @DisplayName("Проверка кнопки синхронизации кэшей (general-black-list)")
    @Test
    @Tag("CacheSyncButton")
    public void checkGeneralBlackListsSyncButton(){
        ArrayList<String> blackListSubsList;
        //Выбрать данные из БД
        sql = new SQLHandler();
        try {
            blackListSubsList= sql.getArrayFromDB("MSISDN,EMAIL",
                    "BULK_BLACKLISTS_SUBSCRIBERS",
                    "WHERE BULK_BLACKLISTS_SUBSCRIBERS.BLACKLIST_ID IS NULL");
        } finally {
            sql.disconnect();
        }
        //Удаляю первую запись из кэша если он не пустой
        if (!blackListSubsList.isEmpty()){
            String[] toDelete = blackListSubsList.get(0).split(",");
            if(!toDelete[0].equals("null")) {
                cache.removeByKey(cacheService1,"general-black-list",toDelete[0]);
            }
            if(!toDelete[1].equals("null")) {
                cache.removeByKey(cacheService1,"general-black-list",toDelete[1]);
            }
        }
        //Нажать на кнопку
        ui
                .subSectionClick("Настройки", "Настройки системы")
                .buttonClick("Синхр. кэши")
                .confirm()
        ;
        //Проверка записей в кэше
        for (String s : blackListSubsList) {
            String[] blackListFields = s.split(",");
            if(blackListFields[0].equals("null")) blackListFields[0]="-";
            if(blackListFields[1].equals("null")) blackListFields[1]="-";
            cache.checkGeneralBlackLists(blackListFields[0],blackListFields[1]);
        }
    }
}