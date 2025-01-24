package net.laplace.treasure.utils

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

fun spawnableLocation(
    player: Player,
): Location? {
    val world = player.world

    val x = player.location.blockX
    val y = player.location.blockY
    val z = player.location.blockZ

    for (i in -32..32) {
        for (j in -32..32) {
            for (k in 3 downTo -2) {
                val block = world.getBlockAt(x + i, y + j, z + k)
                if (block.type == Material.SAND) {
                    val above = world.getBlockAt(x + i, y + j + 1, z + k)
                    if (above.type == Material.AIR) {
                        return block.location.add(0.5, 0.0, 0.5)
                    }
                }
            }
        }
    }

    return null
}