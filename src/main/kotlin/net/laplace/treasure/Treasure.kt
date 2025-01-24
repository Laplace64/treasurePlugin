package net.laplace.treasure


import net.laplace.treasure.config.ConfigManager
import net.laplace.treasure.listeners.InteractMetalDetector
import net.laplace.treasure.listeners.InteractShovel
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

class Treasure : JavaPlugin() {
    val manager by lazy { ConfigManager.getInstance(this) }

    override fun onEnable() {
        logger.info("Treasure plugin started, loading configuration")

        val lootConfig = manager.lootConfig
        val generationConfig = manager.generationConfig

        logger.info("Registering events")
        val t = measureTime {
            server.pluginManager.registerEvents(InteractMetalDetector(logger, this), this)
            server.pluginManager.registerEvents(InteractShovel(logger, this), this)
        }

        logger.info("Events registered in $t")

        logger.info("Loaded loot config: $lootConfig")
        logger.info("Loaded generation config: $generationConfig")
    }

    override fun onDisable() {
        logger.info("Treasure plugin stopped, cancelling all tasks")
        server.scheduler.cancelTasks(this)
    }
}
