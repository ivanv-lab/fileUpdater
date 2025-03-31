plugins {
    id("java")
}

group = "org.spring"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.junit:junit-bom:5.10.0"))
    implementation("org.junit.jupiter:junit-jupiter")
    // https://mvnrepository.com/artifact/io.qameta.allure/allure-selenide
    implementation("io.qameta.allure:allure-selenide:2.29.1")
    // https://mvnrepository.com/artifact/com.codeborne/selenide
    implementation("com.codeborne:selenide:7.7.3")
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson/jackson-bom
    implementation("com.fasterxml.jackson:jackson-bom:2.18.3")
    // https://mvnrepository.com/artifact/com.liferay/com.fasterxml.jackson.databind
    implementation("com.liferay:com.fasterxml.jackson.databind:2.10.5.1.LIFERAY-PATCHED-1")
}

tasks.test {
    useJUnitPlatform()
}