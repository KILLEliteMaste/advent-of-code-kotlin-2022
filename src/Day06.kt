fun main() {

    val testInput = readInput("Day06")

    val charArray = testInput[0].toCharArray()

    for (i in 0..charArray.size - 4) {
        val set = setOf(charArray[i], charArray[i + 1], charArray[i + 2], charArray[i + 3])
        if (set.size == 4) {
            println("Part1: Found 4 unique chars at index ${i+4}")
        }
    }

    for (i in 0..charArray.size - 14) {
        val messageMarkerSet = buildSet {
            repeat(14){
                add(charArray[i + it])
            }
        }

        if (messageMarkerSet.size == 14) {
            println("Part2: Found 14 unique chars at index ${i+14}")
        }

    }

}
