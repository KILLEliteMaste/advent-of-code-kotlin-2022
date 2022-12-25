private enum class Direction(val dx: Int, val dy: Int) {
    N(-1, 0),
    S(1, 0),
    W(0, -1),
    E(0, 1),
    NW(-1, -1),
    NE(-1, 1),
    SW(1, -1),
    SE(1, 1)
}

private data class Position(val row: Int, val col: Int) {
    fun next(d: Direction): Position = Position(row + d.dx, col + d.dy)
}

private sealed class MoveProposal {
    object Bad : MoveProposal()
    data class Good(val source: Position) : MoveProposal()
}

private data class SimulationResult(val grid: Set<Position>, val rounds: Int)

fun main() {
    fun parseInput(input: List<String>): MutableSet<Position> =
        input.foldIndexed(sortedSetOf(compareBy({ it.row }, { it.col }))) { row, acc, line ->
            line.forEachIndexed { col, c ->
                if (c == '#') {
                    acc += Position(row, col)
                }
            }
            acc
        }

    fun runSimulation(input: List<String>, maxRounds: Int = Int.MAX_VALUE): SimulationResult {
        val grid = parseInput(input)

        fun hasNoElvesAround(curr: Position, vararg ds: Direction): Boolean {
            check(ds.isNotEmpty())
            return ds.none { d ->
                val next = curr.next(d)
                next in grid
            }
        }

        fun canMove(curr: Position, d: Direction): Boolean = when (d) {
            Direction.N -> hasNoElvesAround(curr, Direction.N, Direction.NE, Direction.NW)
            Direction.S -> hasNoElvesAround(curr, Direction.S, Direction.SE, Direction.SW)
            Direction.W -> hasNoElvesAround(curr, Direction.W, Direction.NW, Direction.SW)
            Direction.E -> hasNoElvesAround(curr, Direction.E, Direction.NE, Direction.SE)
            else -> false
        }

        val directions = listOf(Direction.N, Direction.S, Direction.W, Direction.E)
        var offset = 0

        var rounds = 1
        while (rounds <= maxRounds) {
            val proposals = mutableMapOf<Position, MoveProposal>()

            for (curr in grid) {
                val ok = hasNoElvesAround(curr, *Direction.values())
                if (ok) {
                    continue
                }

                for (i in 0 until 4) {
                    // choose direction
                    val d = directions[(i + offset) % 4]
                    val canMove = canMove(curr, d)
                    if (canMove) {
                        val next = curr.next(d)
                        proposals[next] = if (next !in proposals) MoveProposal.Good(curr) else MoveProposal.Bad
                        break
                    }
                }
            }

            if (proposals.isEmpty()) {
                break
            }

            proposals.asSequence()
                .filter { (_, proposal) -> proposal is MoveProposal.Good }
                .forEach { (next, proposal) ->
                    val curr = (proposal as MoveProposal.Good).source
                    grid -= curr
                    grid += next
                }

            offset++
            offset %= 4
            rounds++
        }
        return SimulationResult(grid, rounds)
    }

    fun part1(input: List<String>): Int {
        val rounds = 10
        val (grid, _) = runSimulation(input, rounds)

        var minRow = Int.MAX_VALUE
        var maxRow = Int.MIN_VALUE
        var minCol = Int.MAX_VALUE
        var maxCol = Int.MIN_VALUE

        grid.forEach {
            minRow = minOf(minRow, it.row)
            maxRow = maxOf(maxRow, it.row)
            minCol = minOf(minCol, it.col)
            maxCol = maxOf(maxCol, it.col)
        }

        return (maxRow - minRow + 1) * (maxCol - minCol + 1) - grid.size
    }

    fun part2(input: List<String>): Int {
        val (_, rounds) = runSimulation(input)
        return rounds
    }

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}