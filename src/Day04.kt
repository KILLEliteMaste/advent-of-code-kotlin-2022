fun main() {

    val testInput = readInput("Day04")

    val input = testInput
        .map { it.split(",") }
        .map {
            listOf(
                Pair(it[0].split("-")[0].toInt(), it[0].split("-")[1].toInt()),
                Pair(it[1].split("-")[0].toInt(), it[1].split("-")[1].toInt())
            )
        }


    println("Part 1: " + input.count { it[0].first <= it[1].first && it[0].second >= it[1].second || it[0].first >= it[1].first && it[0].second <= it[1].second })

    println("Part 2: " + input.count { it[0].first in it[1].first..it[1].second || it[0].second in it[1].first..it[1].second
            || it[1].first in it[0].first..it[0].second || it[1].second in it[0].first..it[0].second })

}
