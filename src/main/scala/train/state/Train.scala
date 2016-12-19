package train
package state

import train.editor.Editor

import scala.annotation.tailrec

class Train(initialX: Int, val color: String, val name: String) {
  var nowX: Int = _
  var toX: Int = _
  var line: Int = _
  var commands: Map[Int, Command] = _

  def reset(commands: Map[Int, Command]): Unit = {
    this.commands = commands
    nowX = initialX
    toX = initialX
    line = 1
  }

  reset(Map())

  override def toString = s"Train($nowX, $toX, $color, $line)"

  def isStation: Boolean = nowX < 265 && nowX > 235

  def isAtTheEnd: Boolean = nowX > 1300

  def goForward(): Unit = {
    toX = toX + 50
  }

  def nextLine(): Unit = {
    line += 1
  }

  def goTo(line: Int): Unit = {
    this.line = line
  }

  def turn(): Unit = {
    @tailrec
    def executeCommand(command: Command): Unit = command match {
      case Empty =>
        nextLine()
        turn()
      case GoForward =>
        Editor.onTrainAction(this)
        nextLine()
        goForward()
      case Wait =>
        Editor.onTrainAction(this)
        nextLine()
      case GoTo(line) =>
        goTo(line)
        turn()
      case IfStation(rightCmd) =>
        if (isStation) executeCommand(rightCmd)
        else {
          nextLine()
          turn()
        }
    }

    if (isAtTheEnd) {
      Player.stop()
      Modals.lose()
    } else {
      commands.get(line) match {
        case None =>
          Player.stop()
          Modals.lose()
        case Some(cmd) => executeCommand(cmd)
      }
    }
  }

  def tick(): Unit = {
    if (nowX + 3 < toX) {
      nowX += 3
      if (nowX > toX) {
        nowX = toX
      }
    }
  }
}