package net.laplace.treasure.loots

import net.laplace.treasure.config.ConfigInfo
import net.laplace.treasure.config.ConfigInfo.Companion.NO_GROUP
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import kotlin.random.Random


@ConfigSerializable
data class Loot(
    val name: String,
    val weight: Int,
)

@ConfigSerializable
data class LootConfig(
    val loots: List<Loot>,
    val debug: Boolean,
) {
    fun getRandomItem(): Loot {
        val weighted = loots.sumOf { it.weight }
        val random = Random.nextFloat()

        var acc = 0.0f
        // There's probably a better way to do this
        val selected = loots.first {
            val normalized = it.weight.toFloat() / weighted
            acc += normalized
            random <= acc
        }

        return selected
    }

    companion object {
        val Info = ConfigInfo(
            name = "loots",
            group = NO_GROUP,
        )
    }
}