package net.laplace.treasure.config

import net.laplace.treasure.generation.GenerationConfig
import net.laplace.treasure.loots.LootConfig
import org.bukkit.plugin.Plugin


class ConfigManager(
    val lootConfig: LootConfig,
    val generationConfig: GenerationConfig,
) {
    companion object {
        private var instance: ConfigManager? = null

        fun getInstance(plugin: Plugin? = null): ConfigManager {
            return instance ?: run {
                requireNotNull(plugin) { "Plugin instance must be provided for the first call" }

                val provider = ConfigProvider(plugin, "config")
                val loader = ConfigLoader(provider)
                val lootConfig = loader.load<LootConfig>(LootConfig.Info).getOrThrow()


                val generationConfig = loader.load<GenerationConfig>(GenerationConfig.Info).getOrThrow()
//                val generationConfig = GenerationConfig(
//                    x = 32,
//                    y = 32,
//                    z = 32,
//                    xOffset = 0,
//                    yOffset = 0,
//                    zOffset = 0,
//                )

                ConfigManager(lootConfig, generationConfig).also { instance = it }
            }
        }
    }
}