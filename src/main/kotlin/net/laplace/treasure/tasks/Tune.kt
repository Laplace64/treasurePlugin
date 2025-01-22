package net.laplace.treasure.tasks

import net.kyori.adventure.text.format.NamedTextColor
import net.laplace.treasure.ChatLogger
import net.laplace.treasure.storage.InMemoryStorage
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.UUID
import kotlin.math.abs

class Tune(private val playerUUID: UUID,
           private val location: Location): BukkitRunnable() {

    private var lastRang = 0
    override fun run() {
        val server = Bukkit.getServer()
        val player = server.getEntity(playerUUID)
        val uuid = player?.uniqueId

        val uuids = InMemoryStorage.uuids

        if (player !is Player || !player.isConnected) {
            cleanup()
            return;
        }

        if (player.inventory.itemInMainHand.type != Material.IRON_HORSE_ARMOR) {
            ChatLogger.message(player, "You tucked your metal detector away.")
            cleanup()
            return;
        }

        if (!uuids.contains(uuid)) {
            cleanup()
            return;
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

        player.spawnParticle(Particle.END_ROD, location, 3, 0.0, 0.0, 0.0, 0.0)

        if (server.currentTick - lastRang > threshold) {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1.5f, 1.0f)
            lastRang = server.currentTick
        }

        if (distance < 2.3) {
            ChatLogger.message(player, "You can feel something below the surface and turned the metal detector off!",
                NamedTextColor.GREEN)

            // treasure will decay after a while
            InMemoryStorage.treasureSpots[player.location.apply {
                y -= 1
            }] = System.currentTimeMillis() + 20000

            cleanup()
        }
    }

    private fun cleanup() {
        InMemoryStorage.uuids.remove(playerUUID)
        cancel()
    }
}