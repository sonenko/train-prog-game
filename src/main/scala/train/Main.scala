package train

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.{CanvasRenderingContext2D, html}

import scala.scalajs.js.annotation.JSExport

@JSExport
object Main {
  @JSExport
  def main(): Unit = {
    val canvasEl = dom.document.getElementById("canvas")
    val canvas: CanvasRenderingContext2D = canvasEl.asInstanceOf[html.Canvas]
      .getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val box: Element = dom.document.getElementById("box")

    val w = canvasEl.getAttribute("width").toInt
    val h = canvasEl.getAttribute("height").toInt
    val pixelRatio = dom.window.devicePixelRatio
    canvasEl.setAttribute("width", (w * pixelRatio).toString)
    canvasEl.setAttribute("height", (h * pixelRatio).toString)
    canvasEl.setAttribute("style", s"width: ${w}px; height: ${h}px;" )
    canvas.scale(dom.window.devicePixelRatio, dom.window.devicePixelRatio)
    new App(canvas, box, w, h)
  }
}

