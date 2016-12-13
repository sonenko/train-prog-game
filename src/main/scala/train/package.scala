import org.scalajs.dom
import org.scalajs.dom.{Node, NodeList, html}
import org.scalajs.dom.raw.{DOMList, DOMTokenList, Element, HTMLCollection}

import scala.scalajs.js

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


  implicit class DOMListToList[T](collection: DOMList[T]) {
    def toList: List[T] = (0 until collection.length).toList.map(i => collection(i))
  }

  implicit class DOMTokenListToList(collection: DOMTokenList) {
    def toList: List[String] = (0 until collection.length).toList.map(i => collection(i))
  }

  implicit class NodeToExtension(node: Node) {
    def asElement: Element = node.asInstanceOf[Element]
  }

  def toDictionary[T](x: Any): js.Dictionary[T] = x.asInstanceOf[js.Dictionary[T]]
}
