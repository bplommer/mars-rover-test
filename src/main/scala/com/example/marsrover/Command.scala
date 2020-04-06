package com.example.marsrover

sealed trait Command
case object MoveForward extends Command
case object RotateLeft extends Command
case object RotateRight extends Command
