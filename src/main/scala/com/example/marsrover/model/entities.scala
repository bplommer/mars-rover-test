package com.example.marsrover.model

import com.example.marsrover.model.Direction.{ East, North, South, West }
import eu.timepit.refined.types.numeric.PosInt

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

final case class World(width: PosInt, height: PosInt)

final case class Position(x: Int, y: Int, direction: Direction)
