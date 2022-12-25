import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun readInputSpaceDelimited(name: String): List<List<String>> {
    val groups = File("src", "$name.txt").readText().replace("\r", "").split("\n\n")
    return groups.map { it.split("\n") }
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
