import kotlin.math.pow

fun main() {
    fun snafuToDecimal(snafu: String): Long {
        var total = 0L
        for (i in snafu.indices) {
            val power = 5.0.pow(snafu.length - i.toDouble() - 1).toLong()
            total += when (snafu[i]) {
                '2' -> 2L * power
                '1' -> 1L * power
                '0' -> 0L
                '-' -> -1L * power
                '=' -> -2L * power
                else -> 0L
            }
        }
        return total
    }

    fun decimalToSnafu(decimal: Long): String {
        var remainder = decimal
        var snafu = ""

        var fives = 1L
        while (fives < decimal) {
            fives *= 5
        }

        fives /= 5

        while (fives >= 1) {
            val div = (remainder + fives / 2 * if (remainder > 0) 1 else -1) / fives
            snafu += when (div.toInt()) {
                0 -> "0"
                1 -> "1"
                2 -> "2"
                -1 -> "-"
                -2 -> "="
                else -> ""
            }
            remainder -= fives * div
            fives /= 5
        }

        return snafu
    }

    fun part1(input: List<String>) = decimalToSnafu(input.sumOf { snafuToDecimal(it) })

    val input = readInput("Day25")

    println(part1(input))
}