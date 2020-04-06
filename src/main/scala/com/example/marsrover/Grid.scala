package com.example.marsrover

import eu.timepit.refined.types.numeric.PosInt

final case class Grid(width: PosInt, height: PosInt)
