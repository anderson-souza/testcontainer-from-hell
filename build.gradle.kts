import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.21"
	kotlin("plugin.spring") version "1.8.21"
	kotlin("plugin.jpa") version "1.8.21"
	id ("org.jetbrains.kotlin.plugin.allopen") version "1.8.21"
	id ("org.jetbrains.kotlin.plugin.noarg") version "1.8.21"
}

group = "com.aps"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val testcontainerversion = "1.18.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation ("org.flywaydb:flyway-core:9.8.1")
	implementation ("org.flywaydb:flyway-mysql")


	testImplementation ("org.junit.jupiter:junit-jupiter:5.8.1")
	testImplementation ("org.junit.vintage:junit-vintage-engine:5.8.1")
	testImplementation ("org.testcontainers:testcontainers:$testcontainerversion")
	testImplementation ("org.testcontainers:junit-jupiter:$testcontainerversion")
	testImplementation ("org.testcontainers:mysql:$testcontainerversion")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
//	useJUnitPlatform()
}
