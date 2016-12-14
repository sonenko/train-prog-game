package train

import org.scalajs.dom.html.Select
import org.scalajs.dom.raw.Element
import org.scalajs.dom.window.console
import org.scalajs.dom.{MouseEvent, document}

object Execution {

  var isRunning = false

  sealed trait Command
  case object GoForward extends Command
  case object Wait extends Command
  case class GoTo(line: Int) extends Command
  case class IfStation(command: Option[Command]) extends Command

  def init(): Unit = {
    val runButtonEl = document.getElementById("run-button")
    runButtonEl.addEventListener("click", (_: MouseEvent) => {
      if (runButtonEl.classList.contains("waiting")) {
        isRunning = true
        changeButtonViewToRunning(runButtonEl)
        run()
      } else {
        isRunning = false
        changeButtonViewToWaiting(runButtonEl)
        stop()
      }
    })
  }

  def run(): Unit = {
    console.log("RUNNING")
    val commands: List[Option[Command]] =
      document.querySelectorAll(".code-lines > li").toList.map(_.asElement).map{ line =>
      val opt = Option(line.querySelector(".command-placeholder-1 .command"))
      opt.flatMap( el =>
        extractCommand(el).map {
          case IfStation(None) =>
            val opt2 = Option(line.querySelector(".command-placeholder-2 .command"))
            IfStation(opt2.flatMap(extractCommand))
          case x => x
        }
      )
    }

    println(commands)

  }

  def extractCommand(commandEl: Element): Option[Command] = {
    if (commandEl.classList.contains("goforward")) {
      Some(GoForward)
    } else if (commandEl.classList.contains("wait")) {
      Some(Wait)
    } else if (commandEl.classList.contains("goto")) {
      val value = commandEl.querySelector("select").asInstanceOf[Select].value
      Some(GoTo(value.toInt))
    } else if (commandEl.classList.contains("ifstation")) {
      Some(IfStation(None))
    } else {
      console.warn("Unexpected element at Execution.run()")
      None
    }
  }

  def stop(): Unit = {
    console.log("STOPPED")
  }

  def changeButtonViewToRunning(runButtonEl: Element): Unit = {
    runButtonEl.classList.remove("waiting")
    runButtonEl.classList.add("running")
  }

  def changeButtonViewToWaiting(runButtonEl: Element): Unit = {
    runButtonEl.classList.remove("running")
    runButtonEl.classList.add("waiting")
  }
}
