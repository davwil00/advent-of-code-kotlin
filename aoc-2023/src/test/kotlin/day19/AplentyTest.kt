package day19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AplentyTest {
    private val testInput = """px{a<2006:qkq,m>2090:A,rfg}
pv{a>1716:R,A}
lnx{m>1548:A,A}
rfg{s<537:gd,x>2440:R,A}
qs{s>3448:A,lnx}
qkq{x<1416:A,crn}
crn{x>2662:A,R}
in{s<1351:px,qqz}
qqz{s>2770:qs,m<1801:hdj,R}
gd{a>3333:R,R}
hdj{m>838:A,pv}

{x=787,m=2655,a=1222,s=2876}
{x=1679,m=44,a=2067,s=496}
{x=2036,m=264,a=79,s=2244}
{x=2461,m=1339,a=466,s=291}
{x=2127,m=1623,a=2188,s=1013}"""

    private val testSubject = Aplenty(testInput)

    @Test
    fun `should`() {
        assertThat(testSubject.part1()).isEqualTo(19114)
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(Aplenty(readInputLines(19)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(Aplenty(readInputLines(19)).part2())
        //    .isEqualTo()
    }
}
