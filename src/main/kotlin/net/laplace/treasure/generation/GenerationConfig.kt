package net.laplace.treasure.generation

import net.laplace.treasure.config.ConfigInfo
import net.laplace.treasure.config.ConfigInfo.Companion.NO_GROUP
import org.spongepowered.configurate.objectmapping.ConfigSerializable


@ConfigSerializable
data class GenerationConfig(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    companion object {
        val Info = ConfigInfo(
            name = "generation",
            group = NO_GROUP,
        )
    }
}