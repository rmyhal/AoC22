fun main() {
  fun part1(input: List<String>): String {
    return crateMover9000(input)
  }

  fun part2(input: List<String>): String {
    return crateMover9001(input)
  }

  val input = readInput("Day05")
  println(part1(input))
  println(part2(input))
}

private fun crateMover9000(input: List<String>): String {
  val crates = crates(input)
  return input.asCommands()
    .forEach { command ->
      for (i in 0 until command.count) {
        crates[command.to].addFirst(
          element = crates[command.from].removeFirst()
        )
      }
    }
    .let { crates }
    .joinToString("") { crate -> crate.first() }
}

private fun crateMover9001(input: List<String>): String {
  val crates = crates(input)
  return input.asCommands()
    .forEach { command ->
      val toMove = crates[command.from].subList(0, command.count)
      toMove
        .reversed()
        .forEach { element ->
          crates[command.to].addFirst(element)
          crates[command.from].removeFirst()
        }
    }
    .let { crates }
    .joinToString("") { crate -> crate.first() }
}

private const val cratesAmount = 9 // based on input
private const val crateSize = 7 // based on input
private const val commandsStartIndex = 10

private fun crates(input: List<String>): Array<ArrayDeque<String>> {
  val crates = Array<ArrayDeque<String>>(cratesAmount) { ArrayDeque() }
  input
    .slice(0..crateSize)
    .forEach { line ->
      val chunks = line
        .chunked(4)
        .map { it.replace("[", "").replace("]", "").replace(" ", "") }
      for (i in crates.indices) {
        if (chunks.getOrNull(i) != null && chunks[i].isNotEmpty()) {
          crates[i].apply { add(chunks[i]) }
        }
      }
    }
  return crates
}

private fun List<String>.asCommands() = this
  .slice(commandsStartIndex until size)
  .map { it.toCommand() }

private fun String.toCommand(): Command {
  val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
  val (count, from, to) = regex.matchEntire(this)!!.destructured
  return Command(
    from = from.toInt() - 1, // shift to adjust to input indexes
    to = to.toInt() - 1, // shift to adjust to input indexes
    count = count.toInt(),
  )
}

private data class Command(val from: Int, val to: Int, val count: Int)