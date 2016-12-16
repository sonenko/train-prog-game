package train
package state

import scala.annotation.tailrec

class Train(initialX: Int, val color: String, commandList: Map[Int, Command]) {
  var nowX: Int = initialX
  var toX: Int = initialX
  var line: Int = 1

  override def toString = s"Train($nowX, $toX, $color, $line)"

  def isStation: Boolean = nowX < 265 && nowX > 235

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
        nextLine()
        goForward()
      case Wait =>
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

    commandList.get(line) match {
      case None => Player.stop()
      case Some(cmd) => executeCommand(cmd)
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