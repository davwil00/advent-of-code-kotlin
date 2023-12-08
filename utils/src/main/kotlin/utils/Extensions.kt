package utils

inline fun <T> Iterable<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = shouldContinue && predicate(it)
        result
    }
}

fun String.containsAll(other: String) = this.split("").containsAll(other.split(""))
fun String.equalsIgnoringOrder(other: String) = this.toList().sorted() == other.toList().sorted()
fun String.splitToString() = this.chunked(1)
fun String.unique() = this.splitToString().toSet().joinToString("")
fun String.isLowercase() = this == this.lowercase()
fun String?.isDigits() = this?.all { it.isDigit() } ?: false

fun Iterable<Long>.product() = this.reduce(Long::times)
fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long = this.map(selector).product()
fun <T, U> Grouping<T, U>.eachCountToLong() = this.fold(0L) { acc, _ -> acc + 1 }

fun Int.isEven() = this % 2 == 0

fun Char.asInt() = this.toString().toInt()


// @link https://en.wikipedia.org/wiki/Euclidean_algorithm#Implementations
private tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

// @link https://github.com/nickleefly/node-lcm/blob/5d44997/index.js
private fun lcm(a: Long, b: Long) = if (b == 0L) 0 else (a * b) / gcd(a, b)

fun List<Long>.lcm() = reduce { a, b -> lcm(a, b) }
