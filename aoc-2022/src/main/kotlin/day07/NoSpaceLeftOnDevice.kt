package day07

import utils.readInputLines

class NoSpaceLeftOnDevice(private val input: List<String>) {

    private fun parseInput(): Folder {
        val iterator = input.drop(1).iterator()
        val rootFolder = Folder(null, "/")
        var currentFolder = rootFolder
        while(iterator.hasNext()) {
            val current = iterator.next()
            when {
                current.startsWith("$") -> {
                    val args = current.split(" ")
                    if (args[1] == "cd") {
                        currentFolder = if (args[2] == "..") {
                            currentFolder.parent!!
                        } else {
                            val newFolder = Folder(currentFolder, args[2])
                            currentFolder.contents.add(newFolder)
                            newFolder
                        }
                    }
                }
                current.startsWith("dir") -> continue
                else -> {
                    val (size, name) = current.split(" ")
                    currentFolder.contents.add(File(name, size.toLong()))
                }
            }
        }

        return rootFolder
    }

    fun part1(): Long {
        val rootFolder = parseInput()
        return findDirsWithSizeAtMost100000(rootFolder)
    }

    private fun findDirsWithSizeAtMost100000(currentFolder: Folder): Long {
        val folderSize = if (currentFolder.size() <= 100000) {
            currentFolder.size()
        } else {
            0
        }

        return folderSize + currentFolder.contents
            .filterIsInstance<Folder>()
            .sumOf { findDirsWithSizeAtMost100000(it) }
    }

    interface Item {
        fun size(): Long
    }
    data class Folder(val parent: Folder?, val name: String, val contents: MutableList<Item> = mutableListOf()): Item {
        override fun size() = contents.sumOf { it.size() }
    }
    data class File(val name: String, val size: Long): Item {
        override fun size() = size
    }
}

fun main() {
    val noSpaceLeftOnDevice = NoSpaceLeftOnDevice(readInputLines(7))
    println(noSpaceLeftOnDevice.part1())
    //println(noSpaceLeftOnDevice.part2())
}
