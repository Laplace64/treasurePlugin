package net.laplace.treasure.tasks

import net.kyori.adventure.text.format.NamedTextColor
import net.laplace.treasure.ChatLogger
import net.laplace.treasure.config.ConfigManager
import net.laplace.treasure.storage.InMemoryStorage
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.logging.Logger
import kotlin.math.abs

class Tune(
    private val playerUUID: UUID,
    private val location: Location,
    private val logger: Logger,
) : BukkitRunnable() {

    private var lastRang = 0
    private var active = false

    override fun run() {
        val server = Bukkit.getServer()
        val player = server.getEntity(playerUUID)
        val uuid = player?.uniqueId

        val uuids = InMemoryStorage.uuids

        try {
            if (player !is Player || !player.isConnected) {
                return
            }

            if (player.inventory.itemInMainHand.type != Material.IRON_HORSE_ARMOR) {
                ChatLogger.message(player, "You tucked your metal detector away.")
                return
            }

            if (!uuids.contains(uuid)) {
                return
            }


            // L1 (Manhattan) distance
            val distance = run {
                val x = location.x - player.location.x
                val z = location.z - player.location.z

                abs(x) + abs(z)
            } + 2.0

            val threshold = when {
                distance > 20 -> 20.0
                else -> distance
            }



            if (server.currentTick - lastRang > threshold) {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.5f, 1.0f)
                lastRang = server.currentTick
            }

            val cfg = ConfigManager.getInstance().lootConfig

            if (cfg.debug) {
                player.spawnParticle(Particle.END_ROD, location.clone().apply {
                    y += 2
                }, 3, 0.0, 0.0, 0.0, 0.0)
                ChatLogger.message(
                    player, "Distance: $distance. Particle coords: ${
                        location.clone().apply {
                            y += 2
                        }
                    }", NamedTextColor.WHITE)
            }


            if (distance < 2.3) {
                ChatLogger.message(
                    player, "You can feel something below the surface and turned the metal detector off!",
                    NamedTextColor.GREEN
                )

                // treasure will decay after a while
                InMemoryStorage.treasureSpots[player.location] = System.currentTimeMillis() + 20000
            } else {
                active = true
            }
        } finally {
            cleanup()
        }

    }

    private fun cleanup() {
        if (active) {
            active = false
            return
        }


        InMemoryStorage.uuids.remove(playerUUID)
        cancel()
    }
}
