import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.asciidoctor.convert") version "1.5.3"
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.spring") version "1.4.0"
}

group = "ch.jvi.magpie"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jersey")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    implementation(project(":magpie-app"))
    implementation(project(":magpie-domain"))
    implementation(project(":magpie-web"))
    implementation(project(":magpie-command-service"))
    implementation(project(":magpie-command-store"))
    implementation(project(":magpie-query-store"))
    implementation(project(":magpie-query-service"))
    implementation(project(":magpie-query-domain"))
    implementation(project(":magpie-event-bus"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true