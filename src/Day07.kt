fun main() {
  fun part1(input: List<String>): Long {
    return dirsSize(input)
  }

  fun part2(input: List<String>): Long {
    return dirSizeToDelete(input)
  }

  val input = readInput("Day07")
  println(part1(input))
  println(part2(input))
}


private fun dirsSize(input: List<String>): Long {
  return getDirsTree(input).values
    .sumOf { if (it <= 100_000) it else 0L }
}

private fun dirSizeToDelete(input: List<String>): Long {
  val dirs = getDirsTree(input)

  val usedSpace = dirs.getValue(Directory("/", null))
  val unusedSpace = 70000000 - usedSpace
  val toClean = 30000000 - unusedSpace

  return dirs.values
    .sorted()
    .first { it > toClean }
}

private fun getDirsTree(input: List<String>): MutableMap<Directory, Long> {
  var dir: Directory? = null
  val dirs = mutableMapOf<Directory, Long>()
  input
    .forEach { line ->
      if (line.startsWith("$")) {
        val cmd = line.substringAfter("$ ")
        // don't care about ls
        if (cmd.startsWith("cd")) {
          val dirName = cmd.substringAfter("cd ") // cd
          dir = if (dirName != "..") {
            Directory(name = dirName, parent = dir)
          } else {
            dir?.parent
          }
        }
      } else {
        // count file
        if (line[0].isDigit()) {
          val size = line.split(" ")[0].toLong()
          dirs[dir!!] = dirs.getOrDefault(dir, 0L) + size
          var parent = dir?.parent
          while (parent != null) {
            dirs[parent] = dirs.getOrDefault(parent, 0L) + size
            parent = parent.parent
          }
        }
      }
    }
  return dirs
}

private data class Directory(
  val name: String = "/",
  var parent: Directory?,
)