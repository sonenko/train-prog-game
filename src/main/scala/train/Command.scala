package train

sealed trait Command
case object GoForward extends Command
case object Wait extends Command
case class IfStation(command: Option[Command]) extends Command
case class GoTo(line: Option[Int]) extends Command



