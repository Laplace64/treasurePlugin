package net.laplace.treasure.tasks

import net.kyori.adventure.text.format.NamedTextColor
import net.laplace.treasure.ChatLogger
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

enum class Result {
    FAILED,
    SUCCESS
}

class TreasureResult(
    private val player: Player,
    private val result: Result
): BukkitRunnable() {
    override fun run() {
        when (result) {
            Result.FAILED -> {
                ChatLogger.message(player,
                    "You dug up a pile of.. nothing.",
                    NamedTextColor.RED)
            }

            Result.SUCCESS -> {
                ChatLogger.message(player,
                    "Something glimmers within the excavated area. You found a treasure!",
                    NamedTextColor.GREEN)

                if (player.inventory.itemInOffHand.type != Material.AIR) {
                    ChatLogger.message(player,
                        "You found a treasure, but your offhand is full. Please make space.",
                        NamedTextColor.RED)
                    return
                } else {
                    player.inventory.setItemInOffHand(
                        ItemStack(Material.DIAMOND)
                    )
                }
            }
        }
    }
}