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
  val line = input[0]
  for (i in 0 until line.length - markerSize) {
    if (line.substring(i until i + markerSize).toSet().size == markerSize) {
      return i + markerSize
    }
  }
  throw IllegalArgumentException()
}