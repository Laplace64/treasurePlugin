package net.laplace.treasure.storage;

import org.bukkit.Location
import java.util.UUID

object InMemoryStorage {
    val uuids = mutableSetOf<UUID>()
    val treasureSpots = mutableMapOf<Location, Long>()
}