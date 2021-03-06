package com.example.cavesofzircon.extensions

import org.hexworks.zircon.api.data.Position3D

fun Position3D.sameLevelNeighborsShuffled(): List<Position3D> {
    return (-1..1).asSequence().flatMap { dx ->
        (-1..1).asSequence().mapNotNull { dy ->
            if (dx == 0 && dy == 0) null
            else copy(x = this.x + dx, y = this.y + dy)
        }
    }.toCollection(ArrayList(8)).shuffled()
}
//fun Position3D.sameLevelNeighborsShuffled(): List<Position3D> { // 1
//    return (-1..1).flatMap { x ->
//        (-1..1).map { y ->                                      // 2
//            this.withRelativeX(x).withRelativeY(y)              // 3
//        }
//    }.minus(this).shuffled()                                    // 4
//}