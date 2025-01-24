package net.laplace.treasure.config

import org.bukkit.plugin.Plugin

class ConfigProvider(
    private val plugin: Plugin,
    private val directory: String,
) {

    fun provide(configInfo: ConfigInfo) = runCatching {
        val path = "$directory/${configInfo.pathWithExtension}"

        runCatching { plugin.saveResource(path, false) }.onFailure {
            plugin.logger.warning(
                "Unable to find bundled configuration file: $path"
            )
        }

        val content = plugin.getResource(path)?.use {
            it.readBytes()
        } ?: throw IllegalArgumentException("Resource not found: $path")

        InMemoryConfigFile(path, content)
    }

    class InMemoryConfigFile(
        override val path: String,
        private var content: ByteArray,
    ) : ConfigFile {
        override fun read() = Result.success(content)
        override fun write(byteArray: ByteArray) {
            content = byteArray
        }

        override fun exists() = true

        override fun toString() = "InMemoryConfigFile(path='$path')"
    }
}

