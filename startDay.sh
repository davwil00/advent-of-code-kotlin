#! /bin/bash

year=$(date +'%Y')
echo 'Enter day: '
read -r inputDay
if [[ ${#inputDay} -eq 2 ]]; then
  day=inputDay
else
  day="0${inputDay}"
fi
echo 'Enter name: '
read -r name

mkdir "aoc-${year}/src/main/kotlin/day$day"
mkdir "aoc-${year}/src/test/kotlin/day$day"
mkdir "aoc-${year}/src/main/resources/day$day"
touch "aoc-${year}/src/main/resources/day$day/input.txt"

printf "package day%s

import utils.readInputLines

class %s(input: List<String>) {
}

fun main() {
    val %s = %s(readInputLines(%d))
    //println(%s.part1())
    //println(%s.part2())
}
" "$day" "$name" "${name,}" "$name" "$day" "${name,}" "${name,}" > "aoc-${year}/src/main/kotlin/day$day/$name.kt"
printf "package day%s

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class %sTest {
    private val testInput = \"\"\"\"\"\".lines()

    private val testSubject = %s(testInput)

    @Test
    fun \`should\`() {
    }

    @Test
    fun \`should get correct answer for part 1\`() {
        // assertThat(%s(readInputLines(%s)).part1())
        //    .isEqualTo()
    }

    @Test
    fun \`should get correct answer for part 2\`() {
        // assertThat(%s(readInputLines(%s)).part2())
        //    .isEqualTo()
    }
}
" "$day" "$name" "$name" "$name" "$inputDay" "$name" "$inputDay" > "aoc-${year}/src/test/kotlin/day$day/${name}Test.kt"
git add .
