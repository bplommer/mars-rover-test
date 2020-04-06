package com.example.marsrover

import cats.scalatest.EitherValues

import com.example.marsrover.Direction.{East, North, South, West}
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.Assertion
import eu.timepit.refined.auto._

class RoverSpec extends AnyFreeSpec with Matchers with EitherValues {
  "a rover when moving forward" - {
    def testMoveForward(
        coordinatesBefore: Coordinates,
        direction: Direction,
        expectedCoordinatesAfter: Coordinates
    ): Assertion = {
      val rover = Rover(Grid(3, 3), coordinatesBefore, direction).value
      val result = rover.moveForward
      result should ===(
        Rover(Grid(3, 3), expectedCoordinatesAfter, direction).value
      )
    }

    "should change its position in the expected way when not crossing the edge of the map" - {
      "when facing north" in
        testMoveForward(Coordinates(1, 1), North, Coordinates(1, 0))

      "when facing east" in
        testMoveForward(Coordinates(1, 1), East, Coordinates(2, 1))

      "when facing south" in
        testMoveForward(Coordinates(1, 1), South, Coordinates(1, 2))

      "when facing west" in
        testMoveForward(Coordinates(1, 1), West, Coordinates(0, 1))
    }

    "should wrap around when crossing the edge of the map" - {
      "when moving north" in
        testMoveForward(Coordinates(1, 0), North, Coordinates(1, 2))

      "when moving east" in
        testMoveForward(Coordinates(2, 1), East, Coordinates(0, 1))

      "when moving south" in
        testMoveForward(Coordinates(1, 2), South, Coordinates(1, 0))

      "when moving west" in
        testMoveForward(Coordinates(0, 1), West, Coordinates(2, 1))
    }
  }

  "a rover when rotating right should change its direction in the expected way" - {
    def testRotateRight(
        directionBefore: Direction,
        directionAfter: Direction
    ): Assertion = {
      val rover = Rover(Grid(3, 3), Coordinates(1, 1), directionBefore).value
      val result = rover.rotateRight
      result should ===(
        Rover(Grid(3, 3), Coordinates(1, 1), directionAfter).value
      )
    }

    "when facing North" in testRotateRight(North, East)
    "when facing East" in testRotateRight(East, South)
    "when facing South" in testRotateRight(South, West)
    "when facing West" in testRotateRight(West, North)
  }

  "a rover when rotating left should change its direction in the expected way" - {
    def testRotateLeft(
        directionBefore: Direction,
        directionAfter: Direction
    ): Assertion = {
      val rover = Rover(Grid(3, 3), Coordinates(1, 1), directionBefore).value
      val result = rover.rotateLeft
      result should ===(
        Rover(Grid(3, 3), Coordinates(1, 1), directionAfter).value
      )
    }

    "when facing North" in testRotateLeft(North, West)
    "when facing West" in testRotateLeft(West, South)
    "when facing South" in testRotateLeft(South, East)
    "when facing East" in testRotateLeft(East, North)
  }

  "a rover when following a sequence of commands should change position in the expected way" in {
    val rover = Rover(Grid(5, 5), Coordinates(1, 1), direction = North).value

    val result = rover.followCommands(
      List(MoveForward, RotateLeft, MoveForward, MoveForward)
    )

    result should ===(Rover(Grid(5, 5), Coordinates(4, 0), West).value)
  }
}
