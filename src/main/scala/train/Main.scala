package train

import org.scalajs.dom
import train.Hacks.CanvasSettings
import train.editor.{DragAndDrop, Editor}

import scala.scalajs.js.annotation.JSExport

@JSExport
object Main {
  Editor.init()
  DragAndDrop.init()
  Player.init()
  Animation.init()





//  new Animation(canvas2dSettings.canvas, canvas2dSettings.width, canvas2dSettings.height)


}

