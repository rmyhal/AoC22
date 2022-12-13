import kotlin.math.abs

fun main() {
  fun part1(input: List<String>): Int {
    return first(input)
  }

  fun part2(input: List<String>): String {
    return second(input)
  }

  val input = readInput("Day10")
  println(part1(input))
  println(part2(input))
}


private fun first(input: List<String>): Int {
  var x = 1
  var cycle = 0
  var strength = 0

  fun handleCycle() {
    cycle++
    if (cycle % 40 == 20) {
      strength += cycle * x
    }
  }

  input.forEach { line ->
    handleCycle()
    if (line != "noop") {
      handleCycle()
      x += line.split(" ").last().toInt()
    }
  }
  return strength
}

private fun second(input: List<String>): String {
  var x = 1
  var cycle = 1
  val sprite = Array(6) { Array(39) { '.' } }

  fun handleCycle() {
    val (col, row) = (cycle - 1) % 40 to (cycle - 1) / 40
    if (abs(x - col) <= 1) sprite[row][col] = '#'
    cycle++
  }

  input.forEach { line ->
    handleCycle()
    if (line != "noop") {
      handleCycle()
      x += line.split(" ").last().toInt()
    }
  }

  return sprite.joinToString("\n") { it.joinToString("")  }
}