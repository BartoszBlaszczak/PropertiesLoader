# PropertiesLoader

 Simple tool for loading properties from:
* args (array of Strings in 'key=value' format)
* local .properties files (from the directory where the application was run) - `LOCAL`
* application resources and test resources .properties files - `APP_RESOURCES`
* System properties (System.getProperties()) - `SYSTEM`

Example invocations:
* `PropertiesLoader.loadPropertiesFromDefaultSources()` - will load properties from LOCAL, APP_RESOURCES and SYSTEM (in this order)
* `PropertiesLoader.loadPropertiesFromDefaultSourcesAnd(args)` - will load properties from args, LOCAL, APP_RESOURCES and SYSTEM (in this order)

To change the order of your sources, enter them explicitly:
* `PropertiesLoader.loadPropertiesFrom(args, APP_RESOURCES, LOCAL, SYSTEM)` - will load properties from args, APP_RESOURCES, LOCAL and SYSTEM (in this order)
* `PropertiesLoader.loadPropertiesFrom(args, APP_RESOURCES, SYSTEM)` - will load properties from args, APP_RESOURCES and SYSTEM (in this order)
* `PropertiesLoader.loadPropertiesFrom(args, APP_RESOURCES)` - will load properties from args and APP_RESOURCES (in this order)
* `PropertiesLoader.loadPropertiesFrom(args, SYSTEM)` - will load properties from args and SYSTEM (in this order)
* `PropertiesLoader.loadPropertiesFrom(args)` - will load properties from args only