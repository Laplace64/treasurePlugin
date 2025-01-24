package net.laplace.treasure.config

interface ConfigFile {
    val path: String

    fun read(): Result<ByteArray>

    fun write(byteArray: ByteArray)

    fun exists(): Boolean
}