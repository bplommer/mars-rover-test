package com.example.marsrover

import com.example.marsrover.Direction.{ East, North, South, West }

/*
 Represents a compass direction. `dx` and `dy` are the offsets from moving one
 grid square in that direction, respectively along the x-axis (east) and y-axis (south).
 */
sealed abstract class Direction(val dx: Int, val dy: Int) {
  def rotateLeft: Direction = this match {
    case North => West
    case West  => South
    case South => East
    case East  => North
  }

  def rotateRight: Direction = this match {
    case North => East
    case East  => South
    case South => West
    case West  => North
  }
}

object Direction {
  case object North extends Direction(0, -1)
  case object East extends Direction(1, 0)
  case object South extends Direction(0, 1)
  case object West extends Direction(-1, 0)
}
