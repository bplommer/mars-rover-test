package com.example.marsrover

import cats.scalatest.EitherValues

import com.example.marsrover.Direction.{East, North, South, West}
import org.scalatest.matchers.should.Matchers
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.Assertion

class RoverSpec extends AnyFreeSpec with Matchers with EitherValues {
  "a rover when moving forward" - {
    def testMoveForward(
        xBefore: Int,
        yBefore: Int,
        direction: Direction,
        xAfter: Int,
        yAfter: Int
    ): Assertion = {
      val rover = Rover(
        gridWidth = 3,
        gridHeight = 3,
        x = xBefore,
        y = yBefore,
        direction = direction
      ).value
      val result = rover.moveForward
      result should ===(
        Rover(
          gridWidth = 3,
          gridHeight = 3,
          x = xAfter,
          y = yAfter,
          direction = direction
        ).value
      )
    }

    "should change its position in the expected way when not crossing the edge of the map" - {
      "when facing north" in
        testMoveForward(
          xBefore = 1,
          yBefore = 1,
          direction = North,
          xAfter = 1,
          yAfter = 0
        )

      "when facing east" in
        testMoveForward(
          xBefore = 1,
          yBefore = 1,
          direction = East,
          xAfter = 2,
          yAfter = 1
        )

      "when facing south" in
        testMoveForward(
          xBefore = 1,
          yBefore = 1,
          direction = South,
          xAfter = 1,
          yAfter = 2
        )

      "when facing west" in
        testMoveForward(
          xBefore = 1,
          yBefore = 1,
          direction = West,
          xAfter = 0,
          yAfter = 1
        )
    }

    "should wrap around when crossing the edge of the map" - {
      "when moving north" in
        testMoveForward(
          xBefore = 1,
          yBefore = 0,
          direction = North,
          xAfter = 1,
          yAfter = 2
        )

      "when moving east" in
        testMoveForward(
          xBefore = 2,
          yBefore = 1,
          direction = East,
          xAfter = 0,
          yAfter = 1
        )

      "when moving south" in
        testMoveForward(
          xBefore = 1,
          yBefore = 2,
          direction = South,
          xAfter = 1,
          yAfter = 0
        )

      "when moving west" in
        testMoveForward(
          xBefore = 0,
          yBefore = 1,
          direction = West,
          xAfter = 2,
          yAfter = 1
        )
    }
  }

  "a rover when rotating right should change its direction in the expected way" - {
    def testRotateRight(
        directionBefore: Direction,
        directionAfter: Direction
    ): Assertion = {
      val rover = Rover(
        gridWidth = 3,
        gridHeight = 3,
        x = 1,
        y = 1,
        direction = directionBefore
      ).value
      val result = rover.rotateRight
      result should ===(
        Rover(
          gridWidth = 3,
          gridHeight = 3,
          x = 1,
          y = 1,
          direction = directionAfter
        ).value
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
      val rover = Rover(
        gridWidth = 3,
        gridHeight = 3,
        x = 1,
        y = 1,
        direction = directionBefore
      ).value
      val result = rover.rotateLeft
      result should ===(
        Rover(
          gridWidth = 3,
          gridHeight = 3,
          x = 1,
          y = 1,
          direction = directionAfter
        ).value
      )
    }

    "when facing North" in testRotateLeft(North, West)
    "when facing West" in testRotateLeft(West, South)
    "when facing South" in testRotateLeft(South, East)
    "when facing East" in testRotateLeft(East, North)
  }
}
