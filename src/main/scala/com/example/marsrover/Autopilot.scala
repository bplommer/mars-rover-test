package com.example.marsrover

import scala.annotation.tailrec

import cats.data.Chain

import com.example.marsrover.Direction.{East, North, South, West}
import eu.timepit.refined.auto._

object Autopilot {
  def findShortestRoute(
      rover: Rover,
      destination: Coordinates
  ): Either[IndexOutOfBoundsException, List[Command]] =
    if (destination.x < 0 || destination.x >= rover.grid.width || destination.y < 0 || destination.y >= rover.grid.height)
      Left(
        new IndexOutOfBoundsException(
          "Destination must be within grid dimensions"
        )
      )
    else {
      val distanceEast = Math.floorMod(
        destination.x - rover.coordinates.x,
        rover.grid.width
      )
      val distanceWest = rover.grid.width - distanceEast

      val horizontalMovements =
        if (distanceEast < distanceWest) List.fill(distanceEast)(East)
        else List.fill(distanceWest)(West)

      val distanceSouth =
        Math.floorMod(destination.y - rover.coordinates.y, rover.grid.height)
      val distanceNorth = rover.grid.height - distanceSouth

      val verticalMovements =
        if (distanceSouth < distanceNorth) List.fill(distanceSouth)(South)
        else List.fill(distanceNorth)(North)

      Right(
        moveCommands(rover.direction, horizontalMovements ::: verticalMovements)
      )
    }

  /*
  Converts a path, expressed as a series of consecutive movements in the given directions, to a set of commands for
  a rover in order to follow that path
   */
  def moveCommands(
      initialDirection: Direction,
      movements: List[Direction]
  ): List[Command] = {
    @tailrec def loop(
        facing: Direction,
        remainingMovements: List[Direction],
        commands: Chain[Command]
    ): List[Command] =
      remainingMovements match {
        case Nil => commands.toList
        case nextMovement :: tl =>
          loop(
            nextMovement,
            tl,
            commands ++ rotateCommands(facing, nextMovement) :+ MoveForward
          )
      }

    loop(initialDirection, movements, Chain.empty)
  }

  /*
   Commands for a rover to rotate from `from` to `to`
   */
  private def rotateCommands(from: Direction, to: Direction): Chain[Command] =
    if (from == to) Chain.empty
    else if (to.dy == -from.dx && to.dx == from.dy) Chain(RotateLeft)
    else if (to.dy == from.dx && to.dx == -from.dy) Chain(RotateRight)
    else Chain(RotateRight, RotateRight)
}
