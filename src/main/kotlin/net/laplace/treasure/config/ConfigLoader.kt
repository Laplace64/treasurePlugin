package net.laplace.treasure.config

import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.YamlConfigurationLoader

class ConfigLoader(
    val provider: ConfigProvider,
) {
    inline fun <reified T> load(configInfo: ConfigInfo): Result<T> = runCatching {
        val configFile = provider.provide(configInfo).getOrThrow()
        val content = configFile.read().getOrThrow()

        val loader = YamlConfigurationLoader.builder().source {
            content.inputStream().bufferedReader()
        }.defaultOptions { options ->
            options.serializers { builder ->
                builder.registerAnnotatedObjects(objectMapperFactory())
            }
        }.build()

        val node = loader.load()

        node.get<T>() ?: throw IllegalArgumentException("Cannot load configuration file: $configInfo")
    }
}