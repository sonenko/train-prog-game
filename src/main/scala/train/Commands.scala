package train

import org.scalajs.dom.document
import org.scalajs.dom.html.Select
import org.scalajs.dom.raw.Element
import org.scalajs.dom.window.console

sealed trait Command
case object Empty extends Command
case object GoForward extends Command
case object Wait extends Command
case class GoTo(line: Int) extends Command
case class IfStation(command: Command) extends Command

object Commands {
  def extract: Map[Int, Command] = {

    def extractCommand(commandEl: Element): Command = {
      if (commandEl.classList.contains("goforward")) {
        GoForward
      } else if (commandEl.classList.contains("wait")) {
        Wait
      } else if (commandEl.classList.contains("goto")) {
        val value = commandEl.querySelector("select").asInstanceOf[Select].value
        GoTo(value.toInt)
      } else if (commandEl.classList.contains("ifstation")) {
        IfStation(Empty)
      } else {
        console.warn("Unexpected element at Execution.run()")
        Empty
      }
    }

    val commands: Seq[Command] = document.querySelectorAll(".code-lines > li").toList.map(_.asElement).map { line =>
      val optEl = Option(line.querySelector(".command-placeholder-1 .command"))
      optEl match {
        case None => Empty
        case Some(el) => extractCommand(el) match {
          case IfStation(_) =>
            val optEl2 = Option(line.querySelector(".command-placeholder-2 .command"))
            IfStation(optEl2.map(extractCommand).getOrElse(Empty))
          case x => x
        }
      }
    }

    commands.zipWithIndex.map(x => x._2 + 1 -> x._1).toMap
  }
}
