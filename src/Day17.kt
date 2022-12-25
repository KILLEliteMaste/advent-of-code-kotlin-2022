fun main() {

    val rocks = buildList {
        add(arrayOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)))
        add(arrayOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, 2), Pair(2, 1)))
        add(arrayOf(Pair(0, 2), Pair(1, 2), Pair(2, 0), Pair(2, 1), Pair(2, 2)))
        add(arrayOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)))
        arrayOf(Pair(0, 0), Pair(0, 1), Pair(1, 0), Pair(1, 1))
    }

    fun part1(input: List<String>): Int {
        val jets = input[0]
        val chamber = mutableListOf<Array<Boolean>>()
        chamber.add(Array(7) { true })

        var totalMoves = 0

        for (t in 0 until 2022) {
            val rock = rocks[t % rocks.size]
            var height = chamber.indexOfLast { it.contains(true) } + rock.maxOf { it.first } + 4
            while (chamber.size < height + 1) {
                chamber.add(Array(7) { false })
            }

            var left = 2
            var falling = true

            while (falling) {
                falling = false
                val isLeft = jets[totalMoves++ % jets.length] == '<'
                if (isLeft) {
                    if (left >= 1) {
                        var movesLeft = true
                        for (p in rock) {
                            val newCoordination = Pair(height - p.first, left + p.second - 1)
                            if (chamber[newCoordination.first][newCoordination.second]) {
                                movesLeft = false
                            }
                        }
                        if (movesLeft) {
                            left--
                        }
                    }
                } else {
                    if (left + rock.maxOf { it.second } < 6) {
                        var movesRight = true
                        for (p in rock) {
                            val newCoordination = Pair(height - p.first, left + p.second + 1)
                            if (chamber[newCoordination.first][newCoordination.second]) {
                                movesRight = false
                            }
                        }
                        if (movesRight) {
                            left++
                        }
                    }
                }
                var drops = true
                for (p in rock) {
                    val newCoordination = Pair(height - p.first - 1, p.second + left)
                    if (chamber[newCoordination.first][newCoordination.second]) {
                        drops = false
                    }
                }
                if (drops) {
                    height--
                    falling = true
                }
            }

            for (p in rock) {
                chamber[height - p.first][p.second + left] = true
            }
        }
        return chamber.indexOfLast { it.contains(true) }
    }

    val input = readInput("Day17")

    println(part1(input))
}