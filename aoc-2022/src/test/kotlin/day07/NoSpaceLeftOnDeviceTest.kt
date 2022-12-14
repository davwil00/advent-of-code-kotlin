package day07

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import utils.readInputLines

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
    fun `should find total space for files greater than or equal to 100000`() {
        assertThat(testSubject.part1())
            .isEqualTo(95437)
    }

    @Test
    fun `should find size of folders to delete`() {
        assertThat(testSubject.part2())
            .isEqualTo(24933642)
    }

    @Test
    fun `should get correct answer for part 1`() {
         assertThat(NoSpaceLeftOnDevice(readInputLines(7)).part1())
            .isEqualTo(1642503)
    }

    @Test
    fun `should get correct answer for part 2`() {
         assertThat(NoSpaceLeftOnDevice(readInputLines(7)).part2())
            .isEqualTo(6999588)
    }
}
