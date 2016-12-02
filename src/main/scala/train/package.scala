import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.Element

package object train {

  case class CanvasSettings(width: Int, height:Int, canvas: dom.CanvasRenderingContext2D)

  def scaleToRetinaSize(canvasEl: Element): CanvasSettings = {
    val oldWidth = canvasEl.clientWidth
    val oldHeight = canvasEl.clientHeight
    val ratio = dom.window.devicePixelRatio
    val newWidth = (oldWidth * ratio).toInt
    val newHeight = (oldHeight * ratio).toInt
    canvasEl.setAttribute("width", newWidth.toString)
    canvasEl.setAttribute("height", newHeight.toString)
    canvasEl.setAttribute("style", s"width: ${oldWidth}px; height: ${oldHeight}px")
    val canvas = canvasEl
      .asInstanceOf[html.Canvas]
      .getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.scale(ratio, ratio)
    CanvasSettings(oldWidth, oldHeight, canvas)
  }
}
