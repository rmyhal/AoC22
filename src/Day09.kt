import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue

fun main() {
  fun part1(input: List<String>): Int {
    return first(input)
  }

  fun part2(input: List<String>): Int {
    return second(input)
  }

  val input = readInput("Day09")
  println(part1(input))
  println(part2(input))
}


private fun first(input: List<String>): Int {
  val uniqueTailsPosition = mutableSetOf<Pair<Int, Int>>()
  var headPosition = 0 to 0
  var tailPosition = 0 to 0

  input.forEach { move ->
    val line = move.split(" ")
    val direction = line[0].toDir()
    val amount = line[1].toInt()
    for (i in 0 until amount) {
      headPosition = when (direction) {
        Dir.UP -> headPosition.copy(second = headPosition.second - 1)
        Dir.RIGHT -> headPosition.copy(first = headPosition.first + 1)
        Dir.LEFT -> headPosition.copy(first = headPosition.first - 1)
        Dir.DOWN -> headPosition.copy(second = headPosition.second + 1)
      }

      val headX = headPosition.first
      val headY = headPosition.second
      val tailX = tailPosition.first
      val tailY = tailPosition.second

      if ((tailX - headX).absoluteValue > 1 || (tailY - headY).absoluteValue > 1) {
        tailPosition = tailX + headX.compareTo(tailX) to tailY + headY.compareTo(tailY)
      }
      uniqueTailsPosition.add(tailPosition)
    }
  }

  return uniqueTailsPosition.size
}

private fun second(input: List<String>): Int {
  val uniqueTailsPosition = mutableSetOf<Pair<Int, Int>>()
  val rope = mutableListOf<Pair<Int, Int>>()
  for (i in 0 until 10) {
    rope.add(0 to 0)
  }

  input.forEach { move ->
    val line = move.split(" ")
    val direction = line[0].toDir()
    val amount = line[1].toInt()
    for (i in 0 until amount) {
      var headPosition = rope[0]
      headPosition = when (direction) {
        Dir.UP -> headPosition.copy(second = headPosition.second - 1)
        Dir.RIGHT -> headPosition.copy(first = headPosition.first + 1)
        Dir.LEFT -> headPosition.copy(first = headPosition.first - 1)
        Dir.DOWN -> headPosition.copy(second = headPosition.second + 1)
      }
      rope[0] = headPosition

      val iterator = rope.listIterator()
      var first = iterator.next()
      while (iterator.hasNext()) {
        var second = iterator.next()

        if (first == second) break

        val headX = first.first
        val headY = first.second

        val tailX = second.first
        val tailY = second.second

        if ((tailX - headX).absoluteValue > 1 || (tailY - headY).absoluteValue > 1) {
          val temp = tailX + headX.compareTo(tailX) to tailY + headY.compareTo(tailY)
          iterator.set(temp)
          second = temp
        }
        first = second
      }
      uniqueTailsPosition.add(rope.last())
    }
  }

  return uniqueTailsPosition.size
}

private fun String.toDir(): Dir {
  return when (this) {
    "U" -> Dir.UP
    "R" -> Dir.RIGHT
    "L" -> Dir.LEFT
    "D" -> Dir.DOWN
    else -> throw IllegalArgumentException()
  }
}

private enum class Dir {
  UP,
  RIGHT,
  LEFT,
  DOWN
}