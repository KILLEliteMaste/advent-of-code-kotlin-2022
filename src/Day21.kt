import java.lang.Exception
import java.lang.Long.max

data class Monkeyy(val name: String, val monkey1: String, val monkey2: String, val operation: Char, var output: Long = Long.MIN_VALUE)

fun main() {
    fun recurseMonkeys(monkeyys: MutableList<Monkeyy>, current: String, target: Long): Long {
        if (current == "humn") {
            return target
        }

        val monkey = monkeyys.find { it.name == current }!!
        val monkey1 = monkeyys.find{ it.name == monkey.monkey1 }!!
        val monkey2 = monkeyys.find{ it.name == monkey.monkey2 }!!

        if (monkey1.output == Long.MIN_VALUE) {
            val newTarget = when (monkey.operation) {
                '+' -> target - monkey2.output
                '-' -> target + monkey2.output
                '*' -> target / monkey2.output
                '/' -> target * monkey2.output
                '=' -> monkey2.output
                else -> throw Exception()
            }
            monkey1.output = newTarget
            return recurseMonkeys(monkeyys, monkey1.name, newTarget)
        } else {
            val newTarget = when (monkey.operation) {
                '+' -> target - monkey1.output
                '-' -> monkey1.output - target
                '*' -> target / monkey1.output
                '/' -> monkey1.output / target
                '=' -> monkey1.output
                else -> throw Exception()
            }
            monkey2.output = newTarget
            return recurseMonkeys(monkeyys, monkey2.name, newTarget)
        }
    }

    fun part1(input: List<String>): Long {
        val monkeyys = mutableListOf<Monkeyy>()

        for (line in input) {
            // root: pppw + sjmn
            val name = line.substring(0, 4)
            var output = Long.MIN_VALUE
            var monkey1 = ""
            var monkey2 = ""
            var operation = ' '
            if (line.length < 15) {
                output = line.substringAfter(" ").toLong()
            } else {
                monkey1 = line.substring(6, 10)
                monkey2 = line.substring(13, 17)
                operation = line[11]
            }

            monkeyys.add(Monkeyy(name, monkey1, monkey2, operation, output))
        }

        while (monkeyys.find { it.name == "root"}!!.output == Long.MIN_VALUE) {
            for (monkey in monkeyys) {
                if (monkey.output == Long.MIN_VALUE) {
                    val monkey1 = monkeyys.find{ it.name == monkey.monkey1 }!!.output
                    val monkey2 = monkeyys.find{ it.name == monkey.monkey2 }!!.output
                    if (monkey1 != Long.MIN_VALUE && monkey2 != Long.MIN_VALUE) {
                        monkey.output = when (monkey.operation) {
                            '+' -> monkey1 + monkey2
                            '-' -> monkey1 - monkey2
                            '*' -> monkey1 * monkey2
                            '/' -> monkey1 / monkey2
                            else -> throw Exception()
                        }
                        if (monkey.output < 0 && monkey1 > 0 && monkey2 > 0) {
                            println("Woo")
                        }
                    }
                }
            }
        }

        return monkeyys.find { it.name == "root"}!!.output
    }

    fun part2(input: List<String>): Long {
        val monkeyys = mutableListOf<Monkeyy>()

        for (line in input) {
            // root: pppw + sjmn
            val name = line.substring(0, 4)
            var output = Long.MIN_VALUE
            var monkey1 = ""
            var monkey2 = ""
            var operation = ' '
            if (line.length < 15) {
                output = line.substringAfter(" ").toLong()
            } else {
                monkey1 = line.substring(6, 10)
                monkey2 = line.substring(13, 17)
                operation = line[11]
            }

            // Change human's output to impossible
            if (name == "humn") {
                output = Long.MIN_VALUE
                monkey1 = "humn"
                monkey2 = "humn"
            }

            // Change root's operation
            if (name == "root") {
                operation = '='
            }

            monkeyys.add(Monkeyy(name, monkey1, monkey2, operation, output))
        }

        var changed = true
        while (changed) {
            changed = false
            for (monkey in monkeyys) {
                if (monkey.output == Long.MIN_VALUE) {
                    val monkey1 = monkeyys.find{ it.name == monkey.monkey1 }!!.output
                    val monkey2 = monkeyys.find{ it.name == monkey.monkey2 }!!.output
                    if (monkey1 != Long.MIN_VALUE && monkey2 != Long.MIN_VALUE) {
                        monkey.output = when (monkey.operation) {
                            '+' -> monkey1 + monkey2
                            '-' -> monkey1 - monkey2
                            '*' -> monkey1 * monkey2
                            '/' -> monkey1 / monkey2
                            else -> throw Exception()
                        }
                        changed = true
                    }
                }
            }
        }

        val root = monkeyys.find { it.name == "root"}!!
        val root1 = monkeyys.find{ it.name == root.monkey1 }!!.output
        val root2 = monkeyys.find{ it.name == root.monkey2 }!!.output
        root.output = max(root1, root2)

        return recurseMonkeys(monkeyys, "root", root.output)
    }

    val input = readInput("Day21")

    println(part1(input))
    println(part2(input))
}