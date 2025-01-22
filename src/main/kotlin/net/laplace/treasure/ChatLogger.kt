package net.laplace.treasure

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

object ChatLogger {
    private val prefix = Component.text("[Treasure] ").color(NamedTextColor.GOLD)

    fun message(player: Player, message: String, color: NamedTextColor = NamedTextColor.WHITE) {
        player.sendMessage(prefix.append(Component.text(message).color(color)))
    }
}