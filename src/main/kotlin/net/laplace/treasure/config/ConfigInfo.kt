package net.laplace.treasure.config

data class ConfigInfo(
    val name: String,
    val group: String = NO_GROUP,
    val extension: String = DEFAULT_EXTENSION,
) {
    val pathWithExtension by lazy { "$path.$extension" }
    val path: String by lazy {
        if (group.isEmpty()) name else "$group/$name"
    }

    companion object {
        const val NO_GROUP = ""
        const val DEFAULT_EXTENSION = "yml"
    }
}
