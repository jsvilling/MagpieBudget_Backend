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
