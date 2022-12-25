fun main() {

    fun addToList(a: String, elements: MutableList<String>) {
        var str = a
        while (str != "") {
            if (str[0] != '[') {
                elements.add(str.substringBefore(","))
                str = if (!str.contains(',')) "" else str.substringAfter(",")
            } else {
                var i = 1
                var brackets = 1
                while (brackets > 0 && i < str.length) {
                    brackets += when (str[i]) {
                        '[' -> 1
                        ']' -> -1
                        else -> 0
                    }
                    i++
                }
                elements.add(str.substring(0, i))
                str = str.substring(i)
            }
        }
    }

    fun listCompare(a: String, b: String): Boolean? {
        var at = a
        var bt = b
        if (a.toIntOrNull() != null && b.toIntOrNull() != null) {
            if (a.toInt() < b.toInt())
                return true
            if (a.toInt() > b.toInt())
                return false
            return null
        }

        if (a.toIntOrNull() == null && b.toIntOrNull() != null) {
            bt = "[$b]"
        }
        if (a.toIntOrNull() != null && b.toIntOrNull() == null) {
            at = "[$a]"
        }

        if (a == "" && b != "")
            return false
        if (a != "" && b == "")
            return true
        if (a == "")
            return null

        val aElements = mutableListOf<String>()
        val bElements = mutableListOf<String>()

        at = at.substring(1, at.length - 1)
        bt = bt.substring(1, bt.length - 1)

        addToList(at, aElements)
        addToList(bt, bElements)

        aElements.remove("")
        bElements.remove("")

        while (aElements.isNotEmpty() && bElements.isNotEmpty()) {
            val comp = listCompare(aElements.removeFirst(), bElements.removeFirst())
            if (comp != null)
                return comp
        }

        if (aElements.isEmpty() && bElements.isNotEmpty()) {
            return true
        }

        if (aElements.isNotEmpty()) {
            return false
        }

        return null
    }

    fun part1(input: List<List<String>>) =
        input.mapIndexed { index, list -> if (listCompare(list[0], list[1]) == true) index + 1 else 0 }.sum()


    fun part2(input: List<List<String>>): Int {
        val cleanInput = mutableListOf<String>()
        for (i in input) {
            for (j in i) {
                cleanInput.add(j)
            }
        }

        cleanInput.add("[[2]]")
        cleanInput.add("[[6]]")

        cleanInput.sortWith { a, b ->
            when (listCompare(a, b)) {
                true -> -1
                false -> 1
                else -> 0
            }
        }

        return (cleanInput.indexOf("[[2]]") + 1) * (cleanInput.indexOf("[[6]]") + 1)
    }

    val input = readInputSpaceDelimited("Day13")

    println(part1(input))

    println(part2(input))
}