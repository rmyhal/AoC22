fun main() {
  fun part1(input: List<String>): Int {
    return process(input, 4)
  }

  fun part2(input: List<String>): Int {
    return process(input, 14)
  }

  val input = readInput("Day06")
  println(part1(input))
  println(part2(input))
}

private fun process(input: List<String>, markerSize: Int): Int {
  return input[0]
    .windowed(markerSize)
    .indexOfFirst { it.toSet().size == markerSize } + markerSize
}