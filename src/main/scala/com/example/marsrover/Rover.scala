package com.example.marsrover

import eu.timepit.refined.auto._

sealed trait Rover {
  def grid: Grid
  def coordinates: Coordinates
  def direction: Direction

  def moveForward: Rover
  def rotateLeft: Rover
  def rotateRight: Rover

  final def followCommand(command: Command): Rover = command match {
    case MoveForward => moveForward
    case RotateLeft  => rotateLeft
    case RotateRight => rotateRight
  }

  final def followCommands(commands: List[Command]): Rover =
    commands.foldLeft(this)(_.followCommand(_))
}
object Rover {

  sealed abstract case class Impl(
      grid: Grid,
      coordinates: Coordinates,
      direction: Direction
  ) extends Rover {
    private def copy(
        coordinates: Coordinates = coordinates,
        direction: Direction = direction
    ): Rover = new Impl(grid, coordinates, direction) {}

    def moveForward: Rover =
      copy(
        coordinates = Coordinates(
          Math.floorMod(coordinates.x + direction.dx, grid.width),
          Math.floorMod(coordinates.y + direction.dy, grid.height)
        )
      )
    def rotateLeft: Rover = copy(direction = direction.rotateLeft)
    def rotateRight: Rover = copy(direction = direction.rotateRight)
  }

  def apply(
      grid: Grid,
      coordinates: Coordinates,
      direction: Direction
  ): Either[Throwable, Rover] =
    if (coordinates.x < 0 || coordinates.x >= grid.width || coordinates.y < 0 || coordinates.y >= grid.height)
      Left(
        new IndexOutOfBoundsException(
          "Rover position must be within grid dimensions"
        )
      )
    else Right(new Impl(grid, coordinates, direction) {})
}
