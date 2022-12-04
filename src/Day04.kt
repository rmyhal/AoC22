fun main() {
  fun part1(input: List<String>): Int {
    return fullOverlap(input)
  }

  fun part2(input: List<String>): Int {
    return partialOverlap(input)
  }

  val input = readInput("Day04")
  println(part1(input))
  println(part2(input))
}

private fun fullOverlap(input: List<String>): Int {
  var sum = 0
  input
    .map { pair -> pair.split(",").map { it.split("-") } }
    .map { (first, second) -> first.map { it.toInt() }.toRange() to second.map { it.toInt() }.toRange() }
    .forEach { (first, second) ->
      if (first contains second || second contains first) {
        sum++
      }
    }
  return sum
}

private fun partialOverlap(input: List<String>): Int {
  var sum = 0
  input
    .map { pair -> pair.split(",").map { it.split("-") } }
    .forEach { (first, second) ->
      val firstRange = first.map { it.toInt() }.toRange()
      val secondRange = second.map { it.toInt() }.toRange()

      if (firstRange overlaps secondRange || secondRange overlaps firstRange) {
        sum++
      }
    }

  return sum
}

private fun List<Int>.toRange(): IntRange = IntRange(get(0), get(1))

// checks for partial overlap
private infix fun IntRange.overlaps(other: IntRange) = first in other || last in other

// checks for full overlap
private infix fun IntRange.contains(other: IntRange) = first >= other.first && last <= other.last