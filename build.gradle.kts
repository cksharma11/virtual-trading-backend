import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "com.infydex"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val junit5JupiterVersion = "5.8.2"
val assertjVersion = "3.22.0"
val springMockKVersion = "3.1.1"
val mockKVersion = "1.12.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-security:2.6.4")
    implementation("org.postgresql:postgresql:42.3.3")
    implementation("org.flywaydb:flyway-core:8.5.2")
    implementation("com.vladmihalcea:hibernate-types-52:2.14.0")
    implementation("com.github.jsqlparser:jsqlparser:4.3")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // #JWT
    implementation("com.auth0:java-jwt:3.18.3")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")

    implementation("io.github.microutils:kotlin-logging:2.1.21")

    //   #junit5 #mockk
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.4")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junit5JupiterVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junit5JupiterVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${junit5JupiterVersion}")
    testImplementation("org.assertj:assertj-core:3.22.0")

    testImplementation("com.ninja-squad:springmockk:${springMockKVersion}")
    testImplementation("io.mockk:mockk:1.12.3")

    testImplementation("io.zonky.test:embedded-database-spring-test:2.1.1")
    testImplementation("org.assertj:assertj-db:2.0.2")
    testImplementation("org.hsqldb:hsqldb:2.6.1")

    // #devtool
    developmentOnly("org.springframework.boot:spring-boot-devtools:2.6.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
