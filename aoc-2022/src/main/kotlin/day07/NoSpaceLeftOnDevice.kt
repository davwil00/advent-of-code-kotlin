package day07

import utils.readInputLines

class NoSpaceLeftOnDevice(private val input: List<String>) {

    companion object {
        const val TOTAL_SPACE_AVAILABLE = 70000000
        const val FREE_SPACE_REQUIRED = 30000000
    }

    private fun parseInput(): Folder {
        val rootFolder = Folder(null, "/")
        var currentFolder = rootFolder
        input.drop(1).forEach { inputLine ->
            when {
                inputLine.startsWith("$ cd") -> {
                    currentFolder = parseCommand(inputLine, currentFolder)
                }
                inputLine.startsWith("dir") || inputLine.startsWith("$ ls") -> {}
                else -> {
                    val (size, name) = inputLine.split(" ")
                    currentFolder.contents.add(File(name, size.toLong()))
                }
            }
        }

        return rootFolder
    }

    private fun parseCommand(inputLine: String, currentFolder: Folder): Folder {
        val (_, _, dir) = inputLine.split(" ")
        return if (dir == "..") {
            currentFolder.parent!!
        } else {
            Folder(currentFolder, dir).also {
                currentFolder.contents.add(it)
            }
        }
    }

    fun part1(): Long {
        val rootFolder = parseInput()
        return findDirsWithSizeAtMost100000(rootFolder)
    }

    fun part2(): Long {
        val rootFolder = parseInput()
        val freeSpace = TOTAL_SPACE_AVAILABLE - rootFolder.size()
        val additionalSpaceRequired = FREE_SPACE_REQUIRED - freeSpace
        val dirSizes = getDirSizes(rootFolder).sorted()
        return dirSizes.first { it >= additionalSpaceRequired }
    }

    private fun findDirsWithSizeAtMost100000(currentFolder: Folder): Long {
        val folderSize = if (currentFolder.size() <= 100000) {
            currentFolder.size()
        } else {
            0
        }

        return folderSize + currentFolder
            .folders()
            .sumOf { findDirsWithSizeAtMost100000(it) }
    }

    private fun getDirSizes(currentFolder: Folder): List<Long> {
        return currentFolder.folders()
            .flatMap { getDirSizes(it)}
            .toMutableList() + currentFolder.size()
    }

    interface Item {
        fun size(): Long
    }
    data class Folder(val parent: Folder?, val name: String, val contents: MutableList<Item> = mutableListOf()): Item {
        override fun size() = contents.sumOf { it.size() }
        fun folders() = contents.filterIsInstance<Folder>()
    }
    data class File(val name: String, val size: Long): Item {
        override fun size() = size
    }
}
fun main() {
    val noSpaceLeftOnDevice = NoSpaceLeftOnDevice(readInputLines(7))
    println(noSpaceLeftOnDevice.part1())
    println(noSpaceLeftOnDevice.part2())
}
