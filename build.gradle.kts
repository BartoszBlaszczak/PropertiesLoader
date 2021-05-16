plugins {
	kotlin("jvm") version "1.5.0"
	`maven-publish`
}

group = "hex"
version = "1.0.4"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation ("io.kotest:kotest-runner-junit5-jvm:4.5.0")
}
tasks.test {
	useJUnitPlatform {}
}


publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "hex"
			artifactId = "PropertiesLoader"
			version = version
			
			from(components["java"])
		}
	}
}

