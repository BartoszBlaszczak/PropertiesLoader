plugins {
	kotlin("jvm") version "1.5.0"
	`maven-publish`
}

group = "hex"
version = "1.0.3"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation ("io.kotest:kotest-runner-junit5-jvm:4.5.0")
}
tasks.test {
	useJUnitPlatform {}
}