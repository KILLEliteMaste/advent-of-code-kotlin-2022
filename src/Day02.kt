enum class RPC(val points: Int, val opponent: String, val self: String) {
    ROCK(1, "A", "X"),
    PAPER(2, "B", "Y"),
    SCISSORS(3, "C", "Z");

    companion object {
        fun getByName(x: String): RPC {
            for (value in values()) {
                if (value.opponent == x || value.self == x) {
                    return value
                }
            }
            return ROCK
        }
    }
}

fun getPoints(pair: Pair<RPC, RPC>): Int {
    return if (pair.first == RPC.ROCK && pair.second == RPC.PAPER
        || pair.first == RPC.PAPER && pair.second == RPC.SCISSORS
        || pair.first == RPC.SCISSORS && pair.second == RPC.ROCK
    ) {
        6
    } else if (pair.first == pair.second) {
        3
    } else {
        0
    }
}

fun main() {

    val testInput = readInput("Day02")

    val part1 = testInput
        .map { it.split(" ") }
        .map { Pair(RPC.getByName(it[0]), RPC.getByName(it[1])) }
        .sumOf { getPoints(it) + it.second.points }


    println(part1)


    val part2 = testInput
        .map { it.split(" ") }
        .map {
            val opponent = RPC.getByName(it[0])
            if (RPC.getByName(it[1]) == RPC.ROCK) { //LOOSE
                return@map when (opponent) {
                    RPC.ROCK -> RPC.SCISSORS
                    RPC.PAPER -> RPC.ROCK
                    RPC.SCISSORS -> RPC.PAPER
                }.points
            } else if (RPC.getByName(it[1]) == RPC.PAPER) { //DRAW
                return@map 3+opponent.points
            } else { //WIN
                return@map when (opponent) {
                    RPC.ROCK -> RPC.PAPER
                    RPC.PAPER -> RPC.SCISSORS
                    RPC.SCISSORS -> RPC.ROCK
                }.points + 6
            }
        }
        .sum()

    println(part2)
}
