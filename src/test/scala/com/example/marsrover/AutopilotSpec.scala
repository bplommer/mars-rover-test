package com.example.marsrover

import cats.scalatest.EitherValues

import com.example.marsrover.Direction.North
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import eu.timepit.refined.auto._
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class AutopilotSpec
    extends AnyFreeSpec
    with Matchers
    with EitherValues
    with ScalaCheckDrivenPropertyChecks {

  "autopilot" - {
    "should find the shortest route when this doesn't involve crossing the edge of the map" in {
      val rover = Rover(Grid(10, 10), Coordinates(2, 3), North).value
      val destination = Coordinates(6, 5)
      val route = Autopilot.findShortestRoute(rover, destination).value
      route.count(_ == MoveForward) should ===(6)
    }

    "should find the shortest route when this involves crossing one edge of the map" in {
      val rover = Rover(Grid(10, 10), Coordinates(2, 3), North).value
      val destination = Coordinates(6, 9)
      val route = Autopilot.findShortestRoute(rover, destination).value
      route.count(_ == MoveForward) should ===(8)
    }

    "should find the shortest route when this involves crossing both edges of the map" in {
      val rover = Rover(Grid(10, 10), Coordinates(2, 3), North).value
      val destination = Coordinates(9, 9)
      val route = Autopilot.findShortestRoute(rover, destination).value
      route.count(_ == MoveForward) should ===(7)
    }

    "should always find a correct route" in {
      case class TestCase(rover: Rover, destination: Coordinates)

      val testCaseGen: Gen[TestCase] = for {
        rover <- Generators.rover
        destination <- Generators.coordinatesInGrid(rover.grid)
      } yield TestCase(rover, destination)

      forAll(testCaseGen) { testCase =>
        val route = Autopilot
          .findShortestRoute(testCase.rover, testCase.destination)
          .value
        val resultOfFollowingRoute = testCase.rover.followCommands(route)
        resultOfFollowingRoute.coordinates should ===(testCase.destination)
      }
    }
  }
}
