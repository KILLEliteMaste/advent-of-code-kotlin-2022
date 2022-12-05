import java.util.LinkedList

fun main() {
    fun getSupplyStacks() = listOf(
        LinkedList(listOf("D", "M", "S", "Z", "R", "F", "W", "N")),
        LinkedList(listOf("W", "P", "Q", "G", "S")),
        LinkedList(listOf("W", "R", "V", "Q", "F", "N", "J", "C")),
        LinkedList(listOf("F", "Z", "P", "C", "G", "D", "L")),
        LinkedList(listOf("T", "P", "S")),
        LinkedList(listOf("H", "D", "F", "W", "R", "L")),
        LinkedList(listOf("Z", "N", "D", "C")),
        LinkedList(listOf("W", "N", "R", "F", "V", "S", "J", "Q")),
        LinkedList(listOf("R", "M", "S", "G", "Z", "W", "Y")),
    )

    val testInput = readInput("Day05")
    val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")

    val part1List = getSupplyStacks()
    testInput.map { regex.find(it)!! }
        .map { val (amount, from, to) = it.destructured; Triple(amount.toInt(), from.toInt(), to.toInt()) }
        .forEach { (amount, from, to) ->
            val fromList = part1List[from - 1]
            val toList = part1List[to - 1]

            repeat(amount) {
                val removed = fromList.removeLast()
                toList.addLast(removed)
            }
        }
    //Part 1
    part1List.joinToString("") { it.last }.also(::println)


    val part2List = getSupplyStacks()

    testInput.map { regex.find(it)!! }
        .map { val (amount, from, to) = it.destructured; Triple(amount.toInt(), from.toInt(), to.toInt()) }
        .forEach { (amount, from, to) ->
            val fromList = part2List[from - 1]
            val toList = part2List[to - 1]

            fromList.takeLast(amount).forEach { toList.addLast(it) }

            repeat(amount) {
                fromList.removeLast()
            }
        }
    //Part 2
    part2List.joinToString("") { it.last }.also(::println)
}
