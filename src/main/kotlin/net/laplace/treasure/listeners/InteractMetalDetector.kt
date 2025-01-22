package net.laplace.treasure.listeners

import net.kyori.adventure.text.format.NamedTextColor
import net.laplace.treasure.ChatLogger
import net.laplace.treasure.storage.InMemoryStorage
import net.laplace.treasure.tasks.Tune
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin
import java.util.logging.Logger

class InteractMetalDetector(private val logger: Logger,
                            private val plugin: Plugin): Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.item?.type != Material.IRON_HORSE_ARMOR) {
            return;
        }

        logger.info("Interaction made with metal detector")

        val player = event.player
        val uuid = player.uniqueId

        val uuids = InMemoryStorage.uuids

        if (uuids.contains(uuid)) {
            ChatLogger.message(player, "You turned off your metal detector",
                NamedTextColor.WHITE)
            uuids.remove(uuid)
        } else {
            ChatLogger.message(player, "With a switch of a button, the metal detector hums to life.",
                NamedTextColor.WHITE)
            uuids.add(uuid)
        }

        Tune(uuid, Location(player.world, 204.5, 67.0, 62.5)).runTaskTimer(plugin, 5, 1)
    }


}