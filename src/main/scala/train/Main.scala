package train

import org.scalajs.dom
import org.scalajs.dom.raw.Element

import scala.scalajs.js.Dictionary
import scala.scalajs.js.annotation.JSExport


@JSExport
object Main {
  val canvas2dSettings: CanvasSettings = scaleToRetinaSize(dom.document.getElementById("canvas"))
  val commandsEl: Element = dom.document.getElementById("commands")
  val codeEl: Element = dom.document.getElementById("code")
  CodeEditor.init()

  DragAndDrop.init()

  new App(canvas2dSettings.canvas, canvas2dSettings.width, canvas2dSettings.height, commandsEl, codeEl)

  @JSExport
  val globals = Dictionary[Any]()
}

