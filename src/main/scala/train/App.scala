package train


import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.{CanvasRenderingContext2D, html}

class App(ctx: CanvasRenderingContext2D, box: Element, width: Int, height: Int) {
  private val railsY = height / 10 * 9

  println(height)
  println(width)

  def clear(): Unit = {
    ctx.fillStyle = "#e5e5e5"
    ctx.fillRect(0, 0, width, height)
  }

  def draw(): Unit = {
    clear()
    drawRails()
    drawStation()
  }

  def drawRails(): Unit = {
    drawHorizontalRail(railsY - 1)
    drawHorizontalRail(railsY + 1)
    for {x <- 0 to width by 5} {
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

  def drawStation(): Unit = {
    // foundation
    ctx.fillStyle = "#353535"
    ctx.beginPath()
    ctx.moveTo(500,railsY - 2)
    ctx.lineTo(510,railsY - 8)
    ctx.lineTo(560,railsY - 8)
    ctx.lineTo(570,railsY - 2)
    ctx.fill()
    // columns
    def drawColumn(x: Int): Unit = {
      ctx.beginPath()
      ctx.moveTo(x,railsY - 8)
      ctx.lineTo(x,railsY - 23)
      ctx.lineTo(x + 4,railsY - 23)
      ctx.lineTo(x + 4,railsY - 8)
      ctx.fill()
    }
    drawColumn(514)
    drawColumn(524)
    drawColumn(534)
    drawColumn(544)
    drawColumn(554)
    // roof
    ctx.beginPath()
    ctx.moveTo(500, railsY - 23)
    ctx.lineTo(535, railsY - 28)
    ctx.lineTo(570, railsY - 23)
    ctx.fill()
  }

  def drawTrain(x: Int): Unit = {

  }

  dom.window.setInterval(() => draw(), 50)
}