package com.example.marsrover

sealed trait Rover {
  def gridWidth: Int
  def gridHeight: Int

  def x: Int
  def y: Int

  def direction: Direction

  def moveForward: Rover
  def rotateLeft: Rover
  def rotateRight: Rover

  final def followCommand(command: Command): Rover = command match {
    case MoveForward => moveForward
    case RotateLeft => rotateLeft
    case RotateRight => rotateRight
  }

  final def followCommands(commands: List[Command]): Rover = commands.foldLeft(this)(_.followCommand(_))
}
object Rover {

  sealed abstract case class Impl(gridWidth: Int, gridHeight: Int, x: Int, y: Int, direction: Direction) extends Rover {
    private def copy(x: Int = x, y: Int = y, direction: Direction = direction) = new Impl(gridWidth, gridHeight, x, y, direction) {}

    def moveForward: Rover = copy(x = Math.floorMod(x + direction.dx, gridWidth), y = Math.floorMod(y + direction.dy, gridHeight))
    def rotateLeft: Rover = copy(direction = direction.rotateLeft)
    def rotateRight: Rover = copy(direction = direction.rotateRight)
  }

  def apply(gridWidth: Int, gridHeight: Int, x: Int, y: Int, direction: Direction): Either[Throwable, Rover] =
    if (gridWidth <= 0 || gridHeight <= 0) Left(new IllegalArgumentException("Grid width and height must be positive"))
    else if (x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) Left(new IndexOutOfBoundsException("Rover position must be within grid dimensions"))
    else Right(new Impl(gridWidth, gridHeight, x, y, direction) {})
}
