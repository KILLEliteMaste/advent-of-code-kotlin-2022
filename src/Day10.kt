fun main() {
    fun generateCycles(input: List<String>, startCycle: Int): Array<Int> {
        val cycles = Array(input.size * 2) { 1 }
        var currentCycle = startCycle
        var register = 1

        for (line in input) {
            if (line == "noop") {
                currentCycle++
                cycles[currentCycle] = register
            } else {
                val v = line.split(" ").last().toInt()
                currentCycle++
                cycles[currentCycle] = register
                currentCycle++
                register += v
                cycles[currentCycle] = register
            }
        }
        return cycles
    }

    fun part1(input: List<String>): Int {
        val cycles = generateCycles(input, 1)
        return listOf(20, 60, 100, 140, 180, 220).sumOf { it * cycles[it] }
    }

    fun part2(input: List<String>): String {
        val cycles = generateCycles(input, 0)
        val output = StringBuilder()
        for (i in 0 until 6) {
            for (j in 0 until 40) {
                val spot = cycles[i * 40 + j]
                output.append(
                    if (spot in (j - 1)..(j + 1)) {
                        "#"
                    } else {
                        " "
                    })
            }
            output.appendLine()
        }
        return output.toString()
    }

    val input = readInput("Day10")

    println(part1(input))

    println("\u001B[31m" + part2(input))
}