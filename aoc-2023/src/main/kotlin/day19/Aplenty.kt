package day19

import utils.readInput
import utils.readInputLines

class Aplenty(private val input: String) {

    fun part1(): Long {
        val (workflows, parts) = parseInput()
        val acceptedParts = parts.filter { part ->
            isAccepted(part, workflows.getValue("in"), workflows)
        }
        return acceptedParts.sumOf { it.sum().toLong() }
    }

    private tailrec fun isAccepted(part: Part, currentWorkflow: Workflow, workflows: Map<String, Workflow>): Boolean {
        val matchedRule = currentWorkflow.rules.firstOrNull { it.matches(part) }
        return if (matchedRule == null) {
            when (currentWorkflow.fallback) {
                "A" -> true
                "R" -> false
                else -> isAccepted(part, workflows.getValue(currentWorkflow.fallback), workflows)
            }
        } else {
            if (matchedRule.isAccepted()) {
                true
            } else if (matchedRule.isRejected()) {
                false
            } else {
                isAccepted(part, workflows.getValue(matchedRule.outcome), workflows)
            }
        }
    }

    private fun parseInput(): Pair<Map<String, Workflow>, List<Part>> {
        val (workflows, parts) = input.split("\n\n")
        return Pair(
            workflows.lines().map { workflow -> Workflow.fromString(workflow) }.associateBy { it.name },
            parts.lines().map { part -> Part.fromString(part) }
        )
    }

    data class Rule(val category: String, val operator: Operator, val value: Int, val outcome: String) {
        fun isAccepted() = outcome == "A"
        fun isRejected() = outcome == "R"

        fun matches(part: Part): Boolean {
            return operator.operation(part.getValueOfCategory(category), value)
        }
    }

    data class Workflow(val name: String, val rules: List<Rule>, val fallback: String) {
        companion object {
            private val testPattern = Regex("""([xmas])([<>])(\d+):([AR]|[a-z]+)""")
            fun fromString(str: String): Workflow {
                val name = str.substringBefore('{')
                val rulesStr = str.substringAfter('{').substringBeforeLast(',')
                val rules = rulesStr.split(',').map { rule ->
                    val (category, operator, value, outcome) = testPattern.matchEntire(rule)!!.destructured
                    Rule(category, Operator.fromString(operator), value.toInt(), outcome)
                }
                val fallback = str.substringAfterLast(',').dropLast(1)
                return Workflow(name, rules, fallback)
            }
        }
    }

    enum class Operator(val symbol: String, val operation: (Int, Int) -> Boolean) {
        GT(">", { x, y -> x > y }),
        LT("<", { x, y -> x < y });

        companion object {
            fun fromString(str: String): Operator {
                return entries.first { it.symbol == str }
            }
        }
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int) {
        fun getValueOfCategory(category: String) = when (category) {
            "x" -> x
            "m" -> m
            "a" -> a
            "s" -> s
            else -> throw IllegalArgumentException("Unexpected category $category")
        }

        fun sum() = x + m + a + s

        companion object {
            private val pattern = Regex("""\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""")
            fun fromString(str: String): Part {
                val match = pattern.matchEntire(str)!!
                val (x, m, a, s) = match.destructured
                return Part(x.toInt(), m.toInt(), a.toInt(), s.toInt())
            }
        }
    }
}

fun main() {
    val aplenty = Aplenty(readInput(19))
    println(aplenty.part1())
    //println(aplenty.part2())
}
