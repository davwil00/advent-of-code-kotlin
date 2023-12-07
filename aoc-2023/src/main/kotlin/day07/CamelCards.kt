package day07

import utils.readInputLines
import utils.splitToString
import java.lang.IllegalStateException

class CamelCards(private val input: List<String>) {

    fun part1(): Long {
        val handsAndBids = parseInput()
        val sortedHands = handsAndBids.sortedBy { it.hand }
        return sortedHands.mapIndexed { idx, handAndBid ->
            (idx + 1) * handAndBid.bid
        }.sum()
    }

    fun part2(): Long {
        val handsAndBids = parseInput(true)
        val sortedHands = handsAndBids.sortedBy { it.hand }
        return sortedHands.mapIndexed { idx, handAndBid ->
            (idx + 1) * handAndBid.bid
        }.sum()
    }

    private fun parseInput(handleJokers: Boolean = false): List<HandAndBid> {
        return input.map { line ->
            val (hand, bid) = line.split(" ")
            HandAndBid(Hand(hand.splitToString().map { Card.fromValue(it, handleJokers) }), bid.toLong())
        }
    }

    enum class Card(val strVal: String) {
        JOKER("J"),
        TWO("2"),
        THREE("3"),
        FOUR("4"),
        FIVE("5"),
        SIX("6"),
        SEVEN("7"),
        EIGHT("8"),
        NINE("9"),
        TEN("T"),
        JACK("J"),
        QUEEN("Q"),
        KING("K"),
        ACE("A");

        fun isJoker() = this == JOKER

        companion object {
            fun fromValue(value: String, includeJokers: Boolean = false) =
                entries.first {
                    if (includeJokers) {
                        it.strVal == value
                    } else {
                        it != JOKER && it.strVal == value
                    }
                }
        }
    }

    data class Hand(private val cards: List<Card>): List<Card> by cards, Comparable<Hand> {
        private val cardCount = groupingBy { it }.eachCount()
        private val handType = determineHandType()

        private fun determineHandType(): HandType = when {
            cardCount.size == 5 -> HandType.HIGH_CARD
            cardCount.size == 4 -> HandType.ONE_PAIR
            cardCount.size == 3 && cardCount.values.max() == 3 -> HandType.THREE_OF_A_KIND
            cardCount.size == 3 -> HandType.TWO_PAIR
            cardCount.size == 2 && cardCount.values.max() == 4 -> HandType.FOUR_OF_A_KIND
            cardCount.size == 2 -> HandType.FULL_HOUSE
            else -> HandType.FIVE_OF_A_KIND
        }

        private fun findBestPossibleHand(): Hand {
            if (!this.contains(Card.JOKER)) return this
            val bestPossibleCards = when {
                cards.count { it.isJoker() } == 5 -> listOf(Card.ACE, Card.ACE, Card.ACE, Card.ACE, Card.ACE)
                !contains(Card.JOKER) -> cards
                // if there's 1 card value there's more of than other cards, jokers = that
                cardCount.filterKeys { !it.isJoker() }.maxByOrNull { it.value } != null ->
                    replaceJokersWith(cardCount.filterKeys { !it.isJoker() }.maxBy { it.value }.key)
                // replace with highest card
                else -> replaceJokersWith(cards.filterNot { it.isJoker() }.maxBy { it.ordinal })
            }

            return Hand(bestPossibleCards)
        }



        private fun replaceJokersWith(replacement: Card) = map { if (it.isJoker()) replacement else it }

        override fun compareTo(other: Hand): Int {
            val thisBestPossibleHandType = this.findBestPossibleHand().handType
            val otherBestPossibleHandType = other.findBestPossibleHand().handType

            return if (thisBestPossibleHandType == otherBestPossibleHandType) {
                // compare the values in order
                val (card1, card2) = this
                    .zip(other)
                    .dropWhile { (card1, card2) -> card1 == card2 }
                    .firstOrNull() ?: throw IllegalStateException("$this, $other")
                card1.ordinal - card2.ordinal
            } else {
                otherBestPossibleHandType.ordinal.compareTo(thisBestPossibleHandType.ordinal)
            }
        }
    }

    data class HandAndBid(val hand: Hand, val bid: Long)

    enum class HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }
}

fun main() {
    val camelCards = CamelCards(readInputLines(7))
    println(camelCards.part1())
    println(camelCards.part2())
}
