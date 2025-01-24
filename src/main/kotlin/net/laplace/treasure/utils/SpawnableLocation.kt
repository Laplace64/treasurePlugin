package net.laplace.treasure.utils

import net.laplace.treasure.generation.GenerationConfig
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

fun spawnableLocation(
    player: Player,
    cfg: GenerationConfig,
): Location? {
    val world = player.world

    val x = player.location.blockX
    val y = player.location.blockY
    val z = player.location.blockZ

    val xRange = cfg.x
    val yRange = cfg.y
    val zRange = cfg.z

    for (i in -xRange..xRange) {
        for (j in -yRange..yRange) {
            for (k in zRange downTo -zRange) {

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