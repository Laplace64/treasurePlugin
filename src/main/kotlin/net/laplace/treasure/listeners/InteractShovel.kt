package net.laplace.treasure.listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.laplace.treasure.ChatLogger
import net.laplace.treasure.storage.InMemoryStorage
import net.laplace.treasure.tasks.Result
import net.laplace.treasure.tasks.TreasureResult
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.Plugin
import java.util.logging.Logger

class InteractShovel(private val logger: Logger,
                     private val plugin: Plugin): Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.item?.type != Material.IRON_SHOVEL ||
            event.action != Action.RIGHT_CLICK_BLOCK ||
            event.hand == EquipmentSlot.OFF_HAND) {
            return;
        }

        // check if it corresponds to a treasure location
        val block = event.interactionPoint?.let { event.player.world.getBlockAt(it) }
        val spots = InMemoryStorage.treasureSpots

        if (block == null) {
            ChatLogger.message(event.player, "Not a valid treasure spot to dig (how did you do that?)", NamedTextColor.RED)
            return
        }


        var treasureLocation: Location? = null
        for ((location, time) in spots) {
            if (System.currentTimeMillis() - time > 20000) {
                logger.info("treasure spot expired, removing from list")
                spots.remove(location)
                continue
            }

            if (location.distance(block.location) > 1.5) {
                logger.info("treasure spot not close to interaction")
                continue
            }
            treasureLocation = location
        }

        ChatLogger.message(event.player, "Digging...", NamedTextColor.WHITE)



        if (treasureLocation == null) {
            TreasureResult(event.player, Result.FAILED).runTaskLater(plugin, 20)
            return
        }

        TreasureResult(event.player, Result.SUCCESS).runTaskLater(plugin, 20)
        spots.remove(treasureLocation)
    }
}