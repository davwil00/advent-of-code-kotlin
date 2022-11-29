package day14

import org.slf4j.LoggerFactory
import java.io.File
import kotlin.math.floor
import kotlin.math.min

class SpaceStoichiometry {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SpaceStoichiometry::class.java)
        const val ONE_TRILLION = 1000000000000
    }

    private fun parseReactions(reactions: List<String>): Map<String, Reaction> {
        return reactions.map(Reaction::fromString).associateBy { it.output.chemical }
    }

    fun calculateOreNeeded(input: List<String>): Long {
        val reactions = parseReactions(input)
        val targetReaction = reactions["FUEL"]!!
        val stock = mutableMapOf<String, Long>()
        makeNecessaryReactants(reactions, targetReaction, stock)
        return stock.getValue("ORE")
    }

    fun calculateFuelFromOreLimit(input: List<String>): Long {
        val reactions = parseReactions(input)
        val targetReaction = reactions["FUEL"]!!
        val stock = mutableMapOf<String, Long>()
        makeNecessaryReactants(reactions, targetReaction, stock)
        val oreForOneFuel = stock.getValue("ORE")
        val minFuel = floor(ONE_TRILLION.toDouble() / oreForOneFuel).toLong() // If making fuel had no leftovers, this is how much you could have
        stock.keys.forEach { key -> stock[key] = stock.getValue(key) * minFuel }

        var i = minFuel + 1
        try {
            while (true) {
//                val newTargetReaction = targetReaction.copy(
//                    input = targetReaction.input.map { it.copy(quantity = it.quantity * i) },
//                    output = Reactant(i, "FUEL")
//                )
                makeNecessaryReactants(reactions, targetReaction, stock)
//                makeNecessaryReactants(reactions, newTargetReaction, stock)
                i++
            }
        } catch (e: OutOfOreException) {
            println("ran out of ore")
        }
        return i
    }

    // After 1 fuel made, here are the leftovers
    // For each leftover how many

    private fun makeNecessaryReactants(reactions: Map<String, Reaction>, targetReaction: Reaction, stock: MutableMap<String, Long>) {
        val targetChemical = targetReaction.output.chemical
        val targetQuantity = targetReaction.output.quantity

        if (targetReaction.input.first().isOre()) {
            val oreQuantity = targetReaction.input.first().quantity
//            LOGGER.debug("Creating $oreQuantity ORE")
            stock["ORE"] = stock.getOrDefault("ORE", 0) + oreQuantity
            if (stock.getValue("ORE") > ONE_TRILLION) {
                throw OutOfOreException()
            }

//            LOGGER.debug("Creating $targetQuantity $targetChemical")
            stock[targetChemical] = stock.getOrDefault(targetChemical, 0) + targetQuantity
        } else {
            val nextTargetReactions = targetReaction.input
            nextTargetReactions.forEach { reactant ->
                while (stock.getOrDefault(reactant.chemical, 0) < reactant.quantity) {
                    makeNecessaryReactants(reactions, reactions[reactant.chemical]!!, stock)
                }

//                LOGGER.debug("Using ${reactant.quantity} ${reactant.chemical}")
                stock[reactant.chemical] = stock.getValue(reactant.chemical) - reactant.quantity
            }
            stock[targetChemical] = stock.getOrDefault(targetChemical, 0) + targetQuantity
        }
    }

    data class Reaction(val output: Reactant, val input: List<Reactant>) {
        companion object {
            fun fromString(reaction: String): Reaction {
                val (input, output) = reaction.split(" => ")
                val inputs = input.split(", ").map(Reactant::fromString)
                return Reaction(Reactant.fromString(output), inputs)
            }
        }
    }

    data class Reactant(val quantity: Long, val chemical: String) {

        fun isOre() = chemical == "ORE"

        companion object {
            fun fromString(input: String): Reactant {
                val (quantity, chemical) = input.split(" ")
                return Reactant(quantity.toLong(), chemical)
            }
        }
    }

    class OutOfOreException: Exception()
}

fun main() {
    val spaceStoichiometry = SpaceStoichiometry()
    val input = File("src/main/resources/day14/input.txt").readText().trim().lines()
    println("Ore required: ${spaceStoichiometry.calculateOreNeeded(input)}")
//    println("Ore required: ${spaceStoichiometry.calculateFuelFromOreLimit(input)}")
}