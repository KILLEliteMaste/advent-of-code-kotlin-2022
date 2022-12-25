import kotlin.system.measureNanoTime

data class Point(val x: Int, val y: Int, val z: Int, var visited: Boolean = false)

fun main() {
    fun part1(input: List<String>): Int {
        val cubes = mutableListOf<Point>()
        var totalExposed = 0

        for (line in input) {
            val nums = line.split(",").map { it.toInt() }
            cubes.add(Point(nums[0], nums[1], nums[2]))
        }

        for (cube in cubes) {
            val checks = listOf(-1, 1)
            for (check in checks) {
                if (cubes.none { it.x == cube.x + check && it.y == cube.y && it.z == cube.z }) {
                    totalExposed++
                }

                if (cubes.none { it.x == cube.x && it.y == cube.y + check && it.z == cube.z }) {
                    totalExposed++
                }

                if (cubes.none { it.x == cube.x && it.y == cube.y && it.z == cube.z + check }) {
                    totalExposed++
                }
            }
        }

        return totalExposed
    }

    fun part2(input: List<String>): Int {
        val cubes = mutableListOf<Point>()

        for (line in input) {
            val nums = line.split(",").map { it.toInt() }
            cubes.add(Point(nums[0], nums[1], nums[2]))
        }

        val minX = cubes.minOf { it.x } - 1
        val maxX = cubes.maxOf { it.x } + 1
        val minY = cubes.minOf { it.y } - 1
        val maxY = cubes.maxOf { it.y } + 1
        val minZ = cubes.minOf { it.z } - 1
        val maxZ = cubes.maxOf { it.z } + 1

        val void = mutableListOf<Point>()
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    void.add(Point(x, y, z))
                }
            }
        }

        for (cube in cubes) {
            void.remove(cube)
        }

        val first = void.find { it.x == minX }
        val q = mutableListOf<Point>()
        q.add(first!!)

        while (q.isNotEmpty()) {
            val next = q.removeFirst()
            if (!next.visited) {
                next.visited = true
                val checks = listOf(-1, 1)
                for (check in checks) {
                    var neighbor = void.find { it.x == next.x + check && it.y == next.y && it.z == next.z }
                    if (neighbor != null) {
                        q.add(neighbor)
                    }

                    neighbor = void.find { it.x == next.x && it.y == next.y + check && it.z == next.z }
                    if (neighbor != null) {
                        q.add(neighbor)
                    }

                    neighbor = void.find { it.x == next.x && it.y == next.y && it.z == next.z + check }
                    if (neighbor != null) {
                        q.add(neighbor)
                    }
                }
            }
        }

        var totalExposed = 0
        for (v in void.filter { it.visited }) {
            for (c in cubes) {
                if (kotlin.math.abs(v.x - c.x) == 1 && v.y == c.y && v.z == c.z) {
                    totalExposed++
                }
                if (v.x == c.x && kotlin.math.abs(v.y - c.y) == 1 && v.z == c.z) {
                    totalExposed++
                }
                if (v.x == c.x && v.y == c.y && kotlin.math.abs(v.z - c.z) == 1) {
                    totalExposed++
                }
            }
        }

        return totalExposed
    }

    val input = readInput("Day18")

    println(part1(input))

    println(part2(input))
}