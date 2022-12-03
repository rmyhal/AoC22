import java.util.*
import kotlin.math.max

fun main() {
  fun part1(input: List<String>): Long {
    return maxCalories(input)
  }

  fun part2(input: List<String>): Long {
    return max3Calories(input)
  }

  val input = readInput("Day01")
  println(part1(input))
  println(part2(input))
}

private fun maxCalories(input: List<String>): Long {
  var elfIndex = 0
  var elfCalories = 0L
  var maxCalories = 0L
  for (i in input.indices) {
    if (input[i].isEmpty()) {
      maxCalories = max(maxCalories, elfCalories)
      elfIndex++
      elfCalories = 0
    } else {
      elfCalories += input[i].toLong()
    }
  }
  maxCalories = max(maxCalories, elfCalories)
  return maxCalories
}

private fun max3Calories(input: List<String>): Long {
  val queue = PriorityQueue<Long>(3)
  var elfIndex = 0
  var elfCalories = 0L
  for (i in input.indices) {
    if (input[i].isEmpty()) {
      if (queue.size < 3) {
        queue.offer(elfCalories)
      } else if (elfCalories > queue.peek()) {
        queue.poll()
        queue.offer(elfCalories)
      }
      elfIndex++
      elfCalories = 0L
    } else {
      elfCalories += input[i].toLong()
    }
  }
  return queue.sum()
}