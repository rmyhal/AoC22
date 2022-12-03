fun main() {
  fun part1(input: List<String>): Int {
    return priorities(input)
  }

  fun part2(input: List<String>): Int {
    return groupPriorities(input)
  }

  val input = readInput("Day03")
  println(part1(input))
  println(part2(input))
}

private fun priorities(input: List<String>) = input.sumOf { findPriorityChar(it).toPriorityNumber() }

private fun groupPriorities(input: List<String>): Int {
  return input
    .chunked(3)
    .sumOf { group ->
      group
        .map { it.toSet() }
        .reduce { acc, chars -> acc.intersect(chars) }
        .first()
        .toPriorityNumber()
    }
}

private fun Char.toPriorityNumber(): Int {
  return if (this.isLowerCase()) {
    this - 'a' + 1
  } else {
    this - 'A' + 27
  }
}

private fun findPriorityChar(rucksack: String): Char {
  val (first, second) = rucksack.chunked(rucksack.length / 2).map { it.toSet() }
  return first.intersect(second).first()
}
