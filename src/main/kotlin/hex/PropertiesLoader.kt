package hex

import java.io.File
import java.util.Properties
import java.util.stream.Collectors.toList

object PropertiesLoader {
	private val fileIsProperty: (File) -> Boolean = { it.extension == "properties" }
	
	enum class PropertiesSource(internal val load: (Properties) -> Unit) {
		LOCAL(::loadLocalProperties),
		APP_RESOURCES(::loadAppResourceProperties),
		SYSTEM(::loadSystemProperties),
	}
	
	fun loadPropertiesFromDefaultSources() = loadPropertiesFromDefaultSourcesAnd(arrayOf())
	fun loadPropertiesFromDefaultSourcesAnd(args: Array<String>) = loadPropertiesFrom(args, *PropertiesSource.values())
	
	@JvmOverloads
	fun loadPropertiesFrom(args: Array<String> = arrayOf(), vararg types: PropertiesSource): Properties {
		val properties = Properties()
		types.reversed().forEach { it.load(properties) }
		loadArgs(properties, args)
		
		return properties
	}
	
	private fun loadArgs(properties: Properties, args: Array<out String>) =
		args.map { it.split("=", limit = 2) }
			.filter { it.size == 2 }
			.forEach { properties[it[0]] = it[1] }
	
	private fun loadLocalProperties(properties: Properties) = load(from = localFiles(), to = properties)
	
	private fun loadAppResourceProperties(properties: Properties) = load(from = appResources(), to = properties)
	
	private fun localFiles(): Array<File> = File(".").listFiles() ?: arrayOf()
	
	private fun appResources(): Array<File> =
		Thread.currentThread().contextClassLoader.resources("").collect(toList())
			.map { it.toURI() }
			.filter { it.isAbsolute }
			.filterNot { it.isOpaque }
			.sortedBy { it.path.contains("test") }
			.flatMap { File(it).walk().toList() }
			.toTypedArray()
	
	private fun load(from: Array<File>, to: Properties) =
		from
			.filter(fileIsProperty)
			.map(File::inputStream)
			.forEach(to::load)
	
	
	private fun loadSystemProperties(properties: Properties) = properties.putAll(System.getProperties())
}