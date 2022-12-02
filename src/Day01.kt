fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01")
    val list = buildList {
        var list = mutableListOf<Int>()
        for (string in testInput) {
            if (string == "") {
                add(list)
                list = mutableListOf()
            } else {
                list.add(string.toInt())
            }
        }
        add(list)
    }

    //Part 1
    println(list.maxOfOrNull { it.sum() })

    //Part 2
    list.map { it.sum() }.sorted().takeLast(3).sum().let(::println)
}
