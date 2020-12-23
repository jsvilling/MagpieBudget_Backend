pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "magpie"
include("magpie-app")
include("magpie-domain")
include("magpie-web")
include("magpie-command-service")
include("magpie-command-store")
include("magpie-query-service")
include("magpie-query-domain")
include("magpie-query-store")
include("magpie-event-bus")
