import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("org.jlleitschuh.gradle.ktlint-idea") version "11.6.1"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    kotlin("plugin.allopen") version "1.9.23"
    kotlin("plugin.noarg") version "1.9.23"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // implementation
    implementation("org.springframework.boot:spring-boot-starter-security:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.5")
    implementation("com.mysql:mysql-connector-j:8.3.0")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("net.logstash.logback:logstash-logback-encoder:6.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.23")

    // runtimeOnly
    runtimeOnly("com.h2database:h2:2.2.224")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // compileOnly
    compileOnly("org.projectlombok:lombok:1.18.32")

    // annotationProcessor
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // testImplementation
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.5")
    testImplementation("org.springframework.security:spring-security-test:6.2.4")
    testImplementation("org.projectlombok:lombok:1.18.32")
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")

    // testRuntimeOnly
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}
