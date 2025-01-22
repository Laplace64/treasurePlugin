package net.laplace.treasure

import net.laplace.treasure.listeners.InteractMetalDetector
import net.laplace.treasure.listeners.InteractShovel
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

class Treasure : JavaPlugin() {

    override fun onEnable() {
        logger.info("Treasure plugin started, registering events")

        val t = measureTime {
            server.pluginManager.registerEvents(InteractMetalDetector(logger, this), this)
            server.pluginManager.registerEvents(InteractShovel(logger, this), this)
        }


        logger.info("Events registered in $t")
    }

    override fun onDisable() {
        logger.info("Treasure plugin stopped, cancelling all tasks")
        server.scheduler.cancelTasks(this)
    }
}
