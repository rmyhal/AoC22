import java.util.stream.Collectors
import kotlin.IllegalArgumentException

fun main() {
  fun part1(input: List<String>): Long {
    return regularGame(input)
  }

  fun part2(input: List<String>): Long {
    return strategyGame(input)
  }

  val input = readInput("Day02")
  println(part1(input))
  println(part2(input))
}

private enum class Shape {
  ROCK,
  PAPER,
  SCISSORS;
}

private fun String.toShape(): Shape = when (this) {
  "A", "X" -> Shape.ROCK
  "B", "Y" -> Shape.PAPER
  "C", "Z" -> Shape.SCISSORS
  else -> throw IllegalArgumentException("unknown shape $this")
}

private const val losePoints = 0
private const val drawPoints = 3
private const val winPoints = 6
private val shapePoints = mapOf(Shape.ROCK to 1, Shape.PAPER to 2, Shape.SCISSORS to 3)

// key = shape, value = a set of shapes which will lose to key
private val rulesMap = mapOf(
  Shape.ROCK to setOf(Shape.SCISSORS),
  Shape.PAPER to setOf(Shape.ROCK),
  Shape.SCISSORS to setOf(Shape.PAPER),
)

private fun regularGame(input: List<String>): Long {
  var totalScore = 0L
  input.forEach { guide ->
    val (opponent, me) = guide.split(" ").map { it.toShape() }
    val result = when {
      opponent == me -> drawPoints
      rulesMap.getValue(me).contains(opponent) -> winPoints
      else -> losePoints
    }
    totalScore += result + shapePoints.getValue(me)
  }
  return totalScore
}

private fun strategyGame(input: List<String>): Long {
  var totalScore = 0L
  input
    .map { it.split(" ") }
    .forEach { guide ->
      val opponent = guide[0].toShape()
      val strategy = guide[1].toStrategy()

      val shape = when (strategy) {
        Strategy.DRAW -> opponent
        Strategy.WIN -> getWinShapeFor(`for` = opponent)
        Strategy.LOSE -> rulesMap.getValue(opponent).first()
      }

      totalScore += strategy.toPoints() + shapePoints.getValue(shape)
    }
  return totalScore
}

private fun getWinShapeFor(`for`: Shape): Shape {
  return rulesMap.entries
    .stream()
    .filter { entry -> entry.value.contains(`for`) }
    .map { it.key }
    .collect(Collectors.toSet())
    .first()
}

enum class Strategy {
  LOSE,
  DRAW,
  WIN;
}

private fun String.toStrategy(): Strategy = when (this) {
  "X" -> Strategy.LOSE
  "Y" -> Strategy.DRAW
  "Z" -> Strategy.WIN
  else -> throw IllegalArgumentException("unknown strategy $this")
}

private fun Strategy.toPoints() = when (this) {
  Strategy.LOSE -> losePoints
  Strategy.DRAW -> drawPoints
  Strategy.WIN -> winPoints
}