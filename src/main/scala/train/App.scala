package train


import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.Element

class App(ctx: CanvasRenderingContext2D, width: Int, height: Int, commandsEl: Element, codeEl: Element) {
  private val railsY = height / 10 * 9

  init()

  def init(): Unit = {
    ctx.fillStyle = "#d5d5d5"
    ctx.fillRect(0, 0, width, height)
    drawRails()
  }

  def clear(): Unit = {
    ctx.fillStyle = "#d5d5d5"
    ctx.fillRect(0, 0, width, height - 14)
  }

  def draw(): Unit = {
    State.tick()
    clear()
    drawStation(250)
    // lead train
    drawTrain(State.leadTrain.nowX, State.leadTrain.color)
    // rear train
    drawTrain(State.rearTrain.nowX, State.rearTrain.color)
  }

  def drawRails(): Unit = {
    drawHorizontalRail(railsY - 1)
    drawHorizontalRail(railsY + 1)
    for {x <- 0 to (width - 20) by 5} {
      drawJumper(x)
    }

    def drawHorizontalRail(h: Double): Unit = {
      ctx.beginPath()
      ctx.moveTo(0, h)
      ctx.lineTo(width, h)
      ctx.stroke()
    }

    def drawJumper(x: Double): Unit = {
      ctx.beginPath()
      ctx.moveTo(x, railsY - 2)
      ctx.lineTo(x, railsY + 3)
      ctx.stroke()
    }
  }

  def drawStation(x: Int): Unit = {
    // foundation
    ctx.fillStyle = "#353535"
    ctx.beginPath()
    ctx.moveTo(x,railsY - 2)
    ctx.lineTo(x + 10,railsY - 6)
    ctx.lineTo(x + 60,railsY - 6)
    ctx.lineTo(x + 70,railsY - 2)
    ctx.fill()
    // columns
    def drawColumn(x: Int): Unit = {
      ctx.beginPath()
      ctx.moveTo(x,railsY - 6)
      ctx.lineTo(x,railsY - 20)
      ctx.lineTo(x + 2,railsY - 20)
      ctx.lineTo(x + 2,railsY - 6)
      ctx.fill()
    }
    (10 to 50 by 10).foreach{ c => drawColumn(x + c + 4)}
    // roof
    ctx.beginPath()
    ctx.moveTo(x + 14, railsY - 20)
    ctx.lineTo(x + 14,railsY - 22)
    ctx.bezierCurveTo(
      x + 14, railsY - 30,
      x + 56, railsY - 30,
      x + 56,railsY - 22)
    ctx.lineTo(x + 56,railsY - 20)
    ctx.fill()
  }

  def drawTrain(x: Int, color: String): Unit = {
//    println(x)
    ctx.fillStyle = color
    ctx.beginPath()
    ctx.moveTo(x, railsY - 13)
    ctx.lineTo(x + 54, railsY - 13)
    ctx.lineTo(x + 57, railsY - 12)
    ctx.lineTo(x + 64, railsY - 7)
    ctx.bezierCurveTo(
      x + 67, railsY - 4,
      x + 70, railsY - 2,
      x + 64, railsY - 2)
    ctx.lineTo(x , railsY - 2)
    ctx.fill()
  }

  dom.window.setInterval(() => draw(), 50)
  dom.window.setInterval(() => State.update(), 1000)
}

case class Train(nowX: Int, toX: Int, color: String)

object State {

  var rearTrain = Train(50, 50, "#425275")
  var leadTrain = Train(400, 400, "#625252")
  def trainMoved(tr: Train): Train = {
    if (tr.nowX != tr.toX) tr.copy(nowX = tr.nowX + 3)
    else tr
  }


  def tick(): Unit = {
    rearTrain = trainMoved(rearTrain)
    leadTrain = trainMoved(leadTrain)
  }

  def update(): Unit = {
    rearTrain = rearTrain.copy(toX = rearTrain.nowX + 50)
  }
}


