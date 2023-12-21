package day20

import org.slf4j.LoggerFactory
import utils.readInputLines

class PulsePropagation(input: List<String>) {

    val modules = readModuleConfig(input)
    private val logger = LoggerFactory.getLogger(PulsePropagation::class.java)

    fun part1(): Long {
        initialiseConjunctionModules()
        var highStatesSent = 0L
        var lowStatesSent = 0L
        val buttonModule = ButtonModule()
        repeat(1000) {
            val (highCount, lowCount) = processMessages(listOf(buttonModule to false), 0, 0)
            highStatesSent += highCount
            lowStatesSent += lowCount
            logger.debug("-----------")
        }
        return highStatesSent * lowStatesSent
    }

    private tailrec fun processMessages(modulesToProcess: List<Pair<Module, Boolean>>, highCount: Long, lowCount: Long): Pair<Long, Long> {
        if (modulesToProcess.isEmpty()) {
            return Pair(highCount, lowCount)
        }

        val results = modulesToProcess.map { (module, state) ->
            module.propagateMessage(state)
        }
        val newModulesToProcess = results.flatMap { it.modules }
        val newHighCount = highCount + results.sumOf { it.highCount }
        val newLowCount = lowCount + results.sumOf { it.lowCount }
        return processMessages(newModulesToProcess, newHighCount, newLowCount)
    }

    private fun initialiseConjunctionModules() {
        modules.values.forEach { module ->
            module.destinationModules
                .mapNotNull { modules[it] }
                .filterIsInstance<ConjunctionModule>()
                .forEach { it.registerConnectedModule(module.name) }
        }
    }

    private fun readModuleConfig(input: List<String>) = input.map { line ->
        val (nameAndType, modulesStr) = line.split(" -> ")
        val name = nameAndType.drop(1)
        val modules = modulesStr.split(", ")
        when {
            nameAndType.startsWith('%') -> FlipFlopModule(name, modules)
            nameAndType.startsWith('&') -> ConjunctionModule(name, modules)
            nameAndType == "broadcaster" -> BroadcasterModule(nameAndType, modules)
            else -> throw IllegalStateException("Unknown module type $name")
        }
    }.associateBy { it.name }

    abstract inner class Module(val name: String, val destinationModules: List<String>) {
        abstract fun process(input: Boolean, sourceModule: String): Boolean?

        override fun toString(): String {
            return name
        }

        fun propagateMessage(state: Boolean): MessagePropagationResult {
            var highStatesSent = 0L
            var lowStatesSent = 0L
            val modulesToPropagate = destinationModules.mapNotNull { destModName ->
                logger.debug("$name ${if (state) "high" else "low"}-> $destModName")
                if (state) {
                    highStatesSent++
                } else {
                    lowStatesSent++
                }
                val destMod = modules[destModName]
                if (destMod != null) {
                    val newState = destMod.process(state, name)
                    if (newState != null) {
                        destMod to newState
                    } else null
                } else null
            }

            return MessagePropagationResult(modulesToPropagate, highStatesSent, lowStatesSent)
        }
    }

    data class MessagePropagationResult(val modules: List<Pair<Module, Boolean>>, val highCount: Long, val lowCount: Long)

    /**
     * Flip-flop modules (prefix %) are either on or off; they are initially off.
     * If a flip-flop module receives a high pulse, it is ignored and nothing happens.
     * However, if a flip-flop module receives a low pulse, it flips between on and off. If it was off,
     * it turns on and sends a high pulse.
     * If it was on, it turns off and sends a low pulse.
     */
    inner class FlipFlopModule(name: String, destinationModules: List<String>) : Module(name, destinationModules) {
        private var state = false

        override fun process(input: Boolean, sourceModule: String): Boolean? {
            if (!input) {
                state = !state
                return state
            } else return null
        }
    }

    /**
     * Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
     * they initially default to remembering a low pulse for each input. When a pulse is received,
     * the conjunction module first updates its memory for that input.
     * Then, if it remembers high pulses for all inputs, it sends a low pulse; otherwise, it sends a high pulse.
     */
    inner class ConjunctionModule(name: String, destinationModules: List<String>) : Module(name, destinationModules) {
        private val connectedInputStates = mutableMapOf<String, Boolean>()

        override fun process(input: Boolean, sourceModule: String): Boolean {
            connectedInputStates[sourceModule] = input
            return !connectedInputStates.values.all { it }
        }

        fun registerConnectedModule(moduleName: String) {
            connectedInputStates[moduleName] = false
        }
    }

    inner class BroadcasterModule(name: String, destinationModules: List<String>) : Module(name, destinationModules) {
        override fun process(input: Boolean, sourceModule: String) = input
    }

    inner class ButtonModule: Module("button", listOf("broadcaster")) {
        override fun process(input: Boolean, sourceModule: String) = false
    }
}

fun main() {
    val pulsepropagation = PulsePropagation(readInputLines(20))
    println(pulsepropagation.part1()) // 738596340 too low
    //println(pulsepropagation.part2())
}
