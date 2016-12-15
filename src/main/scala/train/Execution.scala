package train

import org.scalajs.dom.html.Select
import org.scalajs.dom.raw.Element
import org.scalajs.dom.window.console
import org.scalajs.dom.window
import org.scalajs.dom.{MouseEvent, document}

object Execution {

  var isRunning = false

  sealed trait Command

  case object Empty extends Command

  case object GoForward extends Command

  case object Wait extends Command

  case class GoTo(line: Int) extends Command

  case class IfStation(command: Command) extends Command

  def init(): Unit = {
    val runButtonEl = document.getElementById("run-button")
    runButtonEl.addEventListener("click", (_: MouseEvent) => {
      if (runButtonEl.classList.contains("waiting")) {
        isRunning = true
        changeButtonViewToRunning(runButtonEl)
        onStarted()
        run(1, commandList)
      } else {
        isRunning = false
        changeButtonViewToWaiting(runButtonEl)
        onStopped()
        stop()
      }
    })
  }

  def onStopped(): Unit = {

  }

  def onStarted(): Unit = {

  }

  def run(step: Int, commands: Map[Int, Command]): Unit = if (isRunning) {
    def executeCommand(command: Command, secondTime: Boolean = false): Unit = command match {
      case Empty =>
        window.setTimeout(() => {
          run(step + 1, commands)
        }, 100)
      case GoForward =>
        console.log("here")
        State.goForward()
        window.setTimeout(() => {
          run(step + 1, commands)
        }, 1000)
      case Wait =>
        window.setTimeout(() => {
          run(step + 1, commands)
        }, 1000)
      case GoTo(line) =>
        window.setTimeout(() => {
          run(line, commands)
        }, 100)
      case IfStation(Empty) =>
        if (secondTime) console.error("unexpected case in executeCommand, IfStation received second time")
        window.setTimeout(() => {
          run(step + 1, commands)
        }, 100)
      case IfStation(nextCmd) =>
        if (secondTime) console.error("unexpected case in executeCommand, IfStation received second time")
        window.setTimeout(() => {
          executeCommand(nextCmd, secondTime = true)
        }, 100)
    }



    commands.get(step) match {
      case None => onStopped()
      case Some(cmd) =>
        executeCommand(cmd)
    }
  }

  def commandList: Map[Int, Command] = {
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

    val cmds = document.querySelectorAll(".code-lines > li").toList.map(_.asElement).map { line =>
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
    cmds.zipWithIndex.map(x => x._2 + 1 -> x._1).toMap
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
