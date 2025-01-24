package net.laplace.treasure.config

import net.laplace.treasure.loots.LootConfig
import org.bukkit.plugin.Plugin


class ConfigManager(
    val lootConfig: LootConfig,
) {
    companion object {
        private var instance: ConfigManager? = null

        fun getInstance(plugin: Plugin? = null): ConfigManager {
            return instance ?: run {
                requireNotNull(plugin) { "Plugin instance must be provided for the first call" }

                val provider = ConfigProvider(plugin, "config")
                val loader = ConfigLoader(provider)
                val lootConfig = loader.load<LootConfig>(LootConfig.Info).getOrThrow()

                ConfigManager(lootConfig).also { instance = it }
            }
        }
    }
}