import kotlin.math.max

fun main() {
  fun part1(input: List<String>): Int {
    return visibleTrees(input)
  }

  fun part2(input: List<String>): Int {
    return highestScenic(input)
  }

  val input = readInput("Day08")
  println(part1(input))
  println(part2(input))
}

private fun visibleTrees(input: List<String>): Int {
  val topBottom = input[0].length * 2
  val leftRight = input.size * 2 - 4
  val exterior = topBottom + leftRight

  var interior = 0

  for (i in 1 until input.size - 1) {
    for (j in 1 until input[i].length - 1) {
      val tree = input[i][j].digitToInt()

      var hasTopBlocking = false
      var hasLeftBlocking = false
      var hasRightBlocking = false
      var hasBottomBlocking = false
      for (ti in i - 1 downTo  0) {
        // to top
        val top = input[ti][j].digitToInt()
        if (top >= tree) {
          hasTopBlocking = true
        }
      }

      for (ti in j - 1 downTo 0) {
        // to left
        val left = input[i][ti].digitToInt()
        if (left >= tree) {
          hasLeftBlocking = true
        }
      }

      for (ti in j + 1 until input.size) {
        // to right
        val right = input[i][ti].digitToInt()
        if (right >= tree) {
          hasRightBlocking = true
        }
      }

      for (ti in i + 1 until input[i].length) {
        // to bottom
        val bottom = input[ti][j].digitToInt()
        if (bottom >= tree) {
          hasBottomBlocking = true
        }
      }

      if (!hasTopBlocking || !hasLeftBlocking || !hasBottomBlocking || !hasRightBlocking) {
        interior++
      }
    }
  }

  return exterior + interior
}

private fun highestScenic(input: List<String>): Int {
  var maxScenic = 0
  for (i in 1 until input.size - 1) {
    for (j in 1 until input[i].length - 1) {
      val tree = input[i][j].digitToInt()

      var topCount = 0
      var leftCount = 0
      var rightCount = 0
      var bottomCount = 0
      for (k in i - 1 downTo  0) {
        // to top
        val top = input[k][j].digitToInt()
        topCount++
        if (top >= tree) {
          break
        }
      }

      for (k in j - 1 downTo 0) {
        // to left
        val left = input[i][k].digitToInt()
        leftCount++
        if (left >= tree) {
          break
        }
      }

      for (k in j + 1 until input.size) {
        // to right
        val right = input[i][k].digitToInt()
        rightCount++
        if (right >= tree) {
          break
        }
      }

      for (k in i + 1 until input[i].length) {
        // to bottom
        val bottom = input[k][j].digitToInt()
        bottomCount++
        if (bottom >= tree) {
          break
        }
      }

      val scenic = topCount * leftCount * rightCount * bottomCount
      maxScenic = max(maxScenic, scenic)
    }
  }

  return maxScenic
}