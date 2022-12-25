import java.util.PriorityQueue
import kotlin.math.abs

private enum class Directionn { UP, DOWN, LEFT, RIGHT }

private data class Blizzard(var x: Int, var y: Int, var dir: Directionn)
private data class Pointt(var x: Int, var y: Int)

fun main() {
    var maxX = 0
    var maxY = 0
    fun moveBlizzards(blizzards: List<Blizzard>) {
        for (blizzard in blizzards) {
            var nextX = blizzard.x
            var nextY = blizzard.y
            when (blizzard.dir) {
                Directionn.LEFT -> nextX--
                Directionn.UP -> nextY--
                Directionn.RIGHT -> nextX++
                Directionn.DOWN -> nextY++
            }
            if (nextX == 0)
                nextX = maxX
            if (nextX > maxX)
                nextX = 1
            if (nextY == 0)
                nextY = maxY
            if (nextY > maxY)
                nextY = 1

            blizzard.x = nextX
            blizzard.y = nextY
        }
    }

    val safetySquares = hashMapOf<Int, MutableList<Pointt>>()
    var safetyTurn = 0
    fun incrementSafetySquares(blizzards: List<Blizzard>) {
        moveBlizzards(blizzards)
        safetyTurn++

        val newSquares = mutableListOf<Pointt>()

        for (y in 1..maxY) {
            for (x in 1..maxX) {
                if (blizzards.count { it.x == x && it.y == y } == 0) {
                    newSquares.add(Pointt(x, y))
                }
            }
        }
        safetySquares[safetyTurn] = newSquares
    }

    fun findPathBfs(blizzards: List<Blizzard>, x: Int, y: Int, goalX: Int, goalY: Int, turn: Int): Int {
        val q = PriorityQueue<Triple<Int, Int, Int>> { t1, t2 ->
            (t1.third + abs(t1.first - goalX) + abs(t1.second - goalY)) - (t2.third + abs(t2.first - goalX) + abs(
                t2.second - goalY
            ))
        }

        q.add(Triple(x, y, turn))

        var maxTurn = turn - 1

        while (q.isNotEmpty()) {
            val next = q.remove()
            q.removeAll { it == next }
            if (next.third > maxTurn) {
                maxTurn = next.third
            }
            if (next.first == goalX && next.second == goalY)
                return next.third + 1

            while (safetySquares[maxTurn + 1] == null) {
                incrementSafetySquares(blizzards)
            }

            // RIGHT
            if (safetySquares[next.third + 1]?.contains(Pointt(next.first + 1, next.second)) == true)
                q.add(Triple(next.first + 1, next.second, next.third + 1))
            // DOWN
            if (safetySquares[next.third + 1]?.contains(Pointt(next.first, next.second + 1)) == true)
                q.add(Triple(next.first, next.second + 1, next.third + 1))
            // LEFT
            if (safetySquares[next.third + 1]?.contains(Pointt(next.first - 1, next.second)) == true)
                q.add(Triple(next.first - 1, next.second, next.third + 1))
            // UP
            if (safetySquares[next.third + 1]?.contains(Pointt(next.first, next.second - 1)) == true)
                q.add(Triple(next.first, next.second - 1, next.third + 1))
            // WAIT
            if (safetySquares[next.third + 1]?.contains(
                    Pointt(
                        next.first,
                        next.second
                    )
                ) == true || (next.first == x && next.second == y)
            )
                q.add(Triple(next.first, next.second, next.third + 1))
        }

        return Int.MIN_VALUE
    }

    fun part1(input: List<String>): Int {
        safetyTurn = 0
        safetySquares.clear()
        val blizzards = mutableListOf<Blizzard>()

        for (y in input.indices) {
            val line = input[y]
            for (x in line.indices) {
                when (line[x]) {
                    '<' -> blizzards.add(Blizzard(x, y, Directionn.LEFT))
                    '^' -> blizzards.add(Blizzard(x, y, Directionn.UP))
                    '>' -> blizzards.add(Blizzard(x, y, Directionn.RIGHT))
                    'v' -> blizzards.add(Blizzard(x, y, Directionn.DOWN))
                }
            }
        }

        maxX = input[0].length - 2
        maxY = input.size - 2

        return findPathBfs(
            blizzards = blizzards,
            x = 1,
            y = 0,
            goalX = maxX,
            goalY = maxY,
            0
        )
    }

    fun part2(input: List<String>): Int {
        safetyTurn = 0
        safetySquares.clear()
        val blizzards = mutableListOf<Blizzard>()

        for (y in input.indices) {
            val line = input[y]
            for (x in line.indices) {
                when (line[x]) {
                    '<' -> blizzards.add(Blizzard(x, y, Directionn.LEFT))
                    '^' -> blizzards.add(Blizzard(x, y, Directionn.UP))
                    '>' -> blizzards.add(Blizzard(x, y, Directionn.RIGHT))
                    'v' -> blizzards.add(Blizzard(x, y, Directionn.DOWN))
                }
            }
        }

        maxX = input[0].length - 2
        maxY = input.size - 2

        var min = findPathBfs(
            blizzards = blizzards,
            x = 1,
            y = 0,
            goalX = maxX,
            goalY = maxY,
            turn = 0
        )
        min = findPathBfs(
            blizzards = blizzards,
            x = maxX,
            y = maxY + 1,
            goalX = 1,
            goalY = 1,
            turn = min
        )
        min = findPathBfs(
            blizzards = blizzards,
            x = 1,
            y = 0,
            goalX = maxX,
            goalY = maxY,
            turn = min
        )
        return min
    }

    val input = readInput("Day24")

    println(part1(input))

    println(part2(input))
}