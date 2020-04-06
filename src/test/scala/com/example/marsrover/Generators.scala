package com.example.marsrover

import cats.scalatest.EitherValues

import com.example.marsrover.Direction.{East, North, South, West}
import eu.timepit.refined.api.{Refined, RefType}
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric.Positive
import org.scalacheck.Gen

object Generators extends EitherValues {
  val grid: Gen[Grid] =
    for {
      width <- Gen
        .choose(1, 100)
        .map(RefType[Refined].unsafeWrap[Int, Positive])
      height <- Gen
        .choose(1, 100)
        .map(RefType[Refined].unsafeWrap[Int, Positive])
    } yield Grid(width, height)

  def coordinatesInGrid(grid: Grid): Gen[Coordinates] =
    for {
      x <- Gen.choose(0, grid.width - 1)
      y <- Gen.choose(0, grid.height - 1)
    } yield Coordinates(x, y)

  val direction: Gen[Direction] = Gen.oneOf(North, East, South, West)

  val rover: Gen[Rover] = for {
    grid <- grid
    coordinates <- coordinatesInGrid(grid)
    direction <- direction
  } yield Rover(grid, coordinates, direction).value
}
