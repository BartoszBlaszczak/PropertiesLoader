package hex

import hex.PropertiesLoader.PropertiesSource.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class PropertiesLoaderTest : FunSpec({
	
	test("loadProperties") {
		//given
		System.setProperty("property", "from system")
		val args = arrayOf("property=from args")
		var case = 0
		
		table(
			headers("#","load", "expected property"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(args)}, "from args"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf())}, null),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf(), APP_RESOURCES)}, "from app resource"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf(), APP_RESOURCES, LOCAL)}, "from app resource"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf(), APP_RESOURCES, LOCAL, SYSTEM)}, "from app resource"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf(), SYSTEM)}, "from system"),
			row(++case, {PropertiesLoader.loadPropertiesFrom(arrayOf(), SYSTEM, APP_RESOURCES, LOCAL)}, "from system"),
			row(++case, {PropertiesLoader.loadPropertiesFromDefaultSources()}, "from local resource"),
			row(++case, {PropertiesLoader.loadPropertiesFromDefaultSourcesAnd(args)}, "from args"),
		).forAll { _, load, expected ->
			
			// when
			val properties = load()
			
			//then
			properties["property"] shouldBe expected
		}
	}
	
	test("overriding app resource property by test resource") {
		//when
		val properties = PropertiesLoader.loadPropertiesFromDefaultSources()
		//then
		properties["overridden_in_tests"] shouldBe "from test resource"
	}
})
