package net.laplace.treasure.loots

import net.laplace.treasure.config.ConfigInfo
import net.laplace.treasure.config.ConfigInfo.Companion.NO_GROUP
import org.spongepowered.configurate.objectmapping.ConfigSerializable


@ConfigSerializable
data class Loot(
    val name: String,
    val weight: Int,
)

@ConfigSerializable
data class LootConfig(
    val loots: List<Loot>,
) {
    companion object {
        val Info = ConfigInfo(
            name = "loots",
            group = NO_GROUP,
        )
    }
}