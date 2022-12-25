import kotlin.math.max

fun main() {


    fun part1(testInput: List<String>): Int {


        var visibleTrees = 0

        //Top and bottom row
        visibleTrees += testInput.first().length
        visibleTrees += testInput.last().length
        // Sides minus the edges
        visibleTrees += testInput.size * 2 - 4

        for (row in 1 until testInput.size - 1) {
            for (column in 1 until testInput[row].length - 1) {
                // count how many numbers are surrounded by larger numbers
                val currentNum = testInput[row][column]
                var visible = false
                var edge = 1

                while (testInput[row - edge][column] < currentNum) {
                    if (row - edge == 0) {
                        visible = true
                        break
                    }
                    edge++
                }

                edge = 1
                while (testInput[row][column - edge] < currentNum) {
                    if (column - edge == 0) {
                        visible = true
                        break
                    }
                    edge++
                }

                edge = 1
                while (testInput[row + edge][column] < currentNum) {
                    if (row + edge == testInput.size - 1) {
                        visible = true
                        break
                    }
                    edge++
                }

                edge = 1
                while (testInput[row][column + edge] < currentNum) {
                    if (column + edge == testInput[row].length - 1) {
                        visible = true
                        break
                    }
                    edge++
                }

                if (visible) {
                    visibleTrees++
                }
            }
        }
        return visibleTrees
    }


    fun part2(input: List<String>): Int {
        val trees = mutableListOf<MutableList<Int>>()
        var bestView = 0

        for (line in input) {
            trees.add(mutableListOf())
            for (i in line) {
                trees.last().add(i.digitToInt())
            }
        }

        for (r in trees.indices) {
            for (c in trees[r].indices) {
                val height = trees[r][c]

                // Left
                var left = 0
                for (x in c - 1 downTo 0) {
                    if (trees[r][x] < height) {
                        left++
                    } else {
                        left++
                        break
                    }
                }

                // Right
                var right = 0
                for (x in c + 1 until trees[r].size) {
                    if (trees[r][x] < height) {
                        right++
                    } else {
                        right++
                        break
                    }
                }

                // Up
                var up = 0
                for (x in r - 1 downTo 0) {
                    if (trees[x][c] < height) {
                        up++
                    } else {
                        up++
                        break
                    }
                }

                // Down
                var down = 0
                for (x in r + 1 until trees.size) {
                    if (trees[x][c] < height) {
                        down++
                    } else {
                        down++
                        break
                    }
                }

                val scenic = left * right * up * down
                bestView = max(bestView, scenic)
            }
        }

        return bestView
    }

    val testInput = readInput("Day08")
    println(part1(testInput))

    println(part2(testInput))


}
