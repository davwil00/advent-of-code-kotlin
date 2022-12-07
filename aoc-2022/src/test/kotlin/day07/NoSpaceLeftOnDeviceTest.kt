package day07

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NoSpaceLeftOnDeviceTest {
    private val testInput = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent().lines()

    private val testSubject = NoSpaceLeftOnDevice(testInput)

    @Test
    fun `should find total space for files greater than 100000`() {
        testSubject.part1()
    }

    @Test
    fun `should get correct answer for part 1`() {
        // assertThat(NoSpaceLeftOnDevice(readInputLines(7)).part1())
        //    .isEqualTo()
    }

    @Test
    fun `should get correct answer for part 2`() {
        // assertThat(NoSpaceLeftOnDevice(readInputLines(7)).part2())
        //    .isEqualTo()
    }
}
