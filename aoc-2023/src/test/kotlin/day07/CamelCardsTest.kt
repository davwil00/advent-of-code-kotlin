package day07

import day07.CamelCards.Card.*
import day07.CamelCards.Hand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

class CamelCardsTest {
    private val testInput = """32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""".lines()

    private val testSubject = CamelCards(testInput)

    @Test
    fun `compare hands`() {
        val fiveOfAKind = Hand(listOf(ACE, ACE, ACE, ACE, ACE))
        val fourOfAKind = Hand(listOf(TEN, ACE, ACE, ACE, ACE))
        val fullHouse = Hand(listOf(ACE, ACE, ACE, TEN, TEN))
        val threeOfAKind = Hand(listOf(ACE, ACE, ACE, TEN, NINE))
        val twoPair = Hand(listOf(ACE, ACE, TEN, TEN, NINE))
        val onePair = Hand(listOf(ACE, ACE, TEN, NINE, EIGHT))
        val highCard = Hand(listOf(ACE, TEN, NINE, EIGHT, SEVEN))
        assertThat(listOf(fullHouse, onePair, fiveOfAKind, threeOfAKind, highCard, fourOfAKind, twoPair).sorted()).containsExactly(
            highCard, onePair, twoPair, threeOfAKind, fullHouse, fourOfAKind, fiveOfAKind
        )
    }

    @Test
    fun `compare hands with type tie`() {
        val hand1 = Hand(listOf(ACE, TEN))
        val hand2 = Hand(listOf(ACE, NINE))
        assertThat(listOf(hand1, hand2).sorted()).containsExactly(
            hand2, hand1
        )
    }

    @Test
    fun `should calculate total winnings`() {
        assertThat(testSubject.part1()).isEqualTo(6440)
    }

    @Test
    fun `should calculate total winnings with best possible hand`() {
        assertThat(testSubject.part2()).isEqualTo(5905)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(CamelCards(readInputLines(7)).part1())
            .isEqualTo(251545216)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(CamelCards(readInputLines(7)).part2())
            .isEqualTo(250384185)
    }
}
