rootProject.name = "BoL"

pluginManagement {
    val springVersion: String by settings
    val kotlinVersion: String by settings
    plugins {
        id("org.springframework.boot") version springVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        kotlin("plugin.noarg") version kotlinVersion
        kotlin("kapt") version "1.6.21"
    }
}

include(
    "domain",
    "core",
    "port-in",
    "port-out",
    "adapter-in:web",
    "adapter-out:rdb",
    "adapter-out:social",
    "support:jwt",
    "support:yaml",
    "support:logging",
)
