package com.example.marsrover.model

import com.example.marsrover.model.Direction.{East, North, South, West}
import eu.timepit.refined.types.numeric.PosInt
import eu.timepit.refined.auto._

final case class Displacement(dx: Int, dy: Int)

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

  def displacement: Displacement = Displacement(dx, dy)
}

object Direction {
  case object North extends Direction(0, -1)
  case object East extends Direction(1, 0)
  case object South extends Direction(0, 1)
  case object West extends Direction(-1, 0)
}

final case class World(width: PosInt, height: PosInt) {
  sealed abstract case class Location(x: Int, y: Int) {
    def +(displacement: Displacement): Location = new Location((x + displacement.dx) % width, (y + displacement.dy) % height) {}
  }

  object Location {
    def apply(
        x: Int,
        y: Int
    ): Either[CoordinatesOutOfBoundsException, Location] =
      if (0 <= x && x < width && 0 <= y &&  y < height) Right(new Location(x, y) {})
      else Left(CoordinatesOutOfBoundsException(x, y))

    case class CoordinatesOutOfBoundsException(x: Int, y: Int)
        extends IndexOutOfBoundsException
  }
}
