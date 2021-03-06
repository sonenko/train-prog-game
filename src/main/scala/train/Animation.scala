package train

import org.scalajs.dom
import org.scalajs.dom.window
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.Element
import train.Hacks.CanvasSettings
import train.state.TrainsState

object Animation {

  val CanvasSettings(width, height, ctx) = Hacks.scaleToRetinaSize(dom.document.getElementById("canvas"))
  val commandsEl: Element = dom.document.getElementById("commands")
  val codeEl: Element = dom.document.getElementById("code")
  private val railsY = height / 10 * 9
  private var interval: Int = -1

  def init (): Unit = {
    ctx.fillStyle = "#d5d5d5"
    ctx.fillRect(0, 0, width, height)
    drawRails()
    draw()
  }

  def play(): Unit = {
    if (interval != -1) {
      window.clearInterval(interval)
    }
    interval = window.setInterval(() => draw(), 30)
  }

  def stop(): Unit = {
    window.clearInterval(interval)
  }

  def clear(): Unit = {
    ctx.fillStyle = "#d5d5d5"
    ctx.fillRect(0, 0, width, height - 14)
  }

  def draw(): Unit = {
    window.console.log("animating")
    TrainsState.tick()
    clear()
    drawStation(258)
    // lead train
    drawTrain(TrainsState.leadTrain.nowX, TrainsState.leadTrain.color)
    // rear train
    drawTrain(TrainsState.rearTrain.nowX, TrainsState.rearTrain.color)
  }

  def drawRails(): Unit = {
    drawHorizontalRail(railsY - 1)
    drawHorizontalRail(railsY + 1)
    for {x <- 2 to width by 5} {
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
    ctx.moveTo(x + 10,railsY - 2)
    ctx.lineTo(x + 10,railsY - 6)
    ctx.lineTo(x + 60,railsY - 6)
    ctx.lineTo(x + 60,railsY - 2)
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
    ctx.moveTo(x + 20, railsY - 11)
    ctx.lineTo(x + 54, railsY - 11)
    ctx.lineTo(x + 58, railsY - 10)
    ctx.lineTo(x + 64, railsY - 7)
    ctx.bezierCurveTo(
      x + 67, railsY - 4,
      x + 70, railsY - 2,
      x + 64, railsY - 2)
    ctx.lineTo(x + 20, railsY - 2)
    ctx.fill()
  }
}



