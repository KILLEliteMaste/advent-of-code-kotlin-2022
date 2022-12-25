import java.lang.Exception

data class WorkingNumber(val original: Int, val number: Long)

fun main() {
    fun moveItem(coords: MutableList<WorkingNumber>, index: Int) {
        val item = coords.find { it.original == index } ?: throw Exception()
        val oldIndex = coords.indexOf(item)
        var newIndex = (oldIndex + item.number) % (coords.size - 1)
        if (newIndex < 0) {
            newIndex += coords.size - 1
        }
        coords.removeAt(oldIndex)
        coords.add(newIndex.toInt(), item)
    }

    fun part1(input: List<String>): Long {
        val coords = input.mapIndexed { index, s -> WorkingNumber(index, s.toLong()) }.toMutableList()

        for (i in coords.indices) {
            moveItem(coords, i)
        }

        val zeroIndex = coords.indexOfFirst { it.number == 0L } ?: throw Exception()

        return coords[((zeroIndex + 1_000L) % coords.size).toInt()].number + coords[((zeroIndex + 2_000L) % coords.size).toInt()].number + coords[((zeroIndex + 3_000L) % coords.size).toInt()].number
    }

    fun part2(input: List<String>): Long {
        val coords = input.mapIndexed { index, s -> WorkingNumber(index, s.toLong() * 811_589_153L) }.toMutableList()

        for (x in 0 until 10) {
            for (i in coords.indices) {
                moveItem(coords, i)
            }
        }

        val zeroIndex = coords.indexOfFirst { it.number == 0L } ?: throw Exception()

        return coords[(zeroIndex + 1_000) % coords.size].number + coords[(zeroIndex + 2_000) % coords.size].number + coords[(zeroIndex + 3_000) % coords.size].number
    }

    val input = readInput("Day20")

    println(part1(input))

    println(part2(input))
}