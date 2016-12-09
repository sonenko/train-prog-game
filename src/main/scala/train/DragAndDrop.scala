package train

import org.scalajs.dom.document
import org.scalajs.dom.raw.{DragEvent, Element}

import scala.scalajs.js

object DragAndDrop {

  val codeLinesEl: Element = document.querySelector(".code-lines")

  def init(): Unit = {
    generateTextEditor()
    initDragAndDrop()
  }

  def generateTextEditor(): Unit = {
    val contents = (1 to 20).map { n =>
      s"""<li>
         |  <div class="line-number">$n</div>
         |  <div class="command-placeholder command-placeholder-1"></div>
         |  <div class="command-placeholder command-placeholder-2"></div>
         |</li>
      """.stripMargin
    }.mkString("")
    codeLinesEl.innerHTML = contents
  }


  def initDragAndDrop(): Unit = {

    val dropZoneEls = document.querySelectorAll(".code-lines .command-placeholder-1").toList.asInstanceOf[List[Element]]
    val dragEls = document.querySelectorAll("#commands > div").toList.asInstanceOf[List[Element]]

    def addDragListeners(dragEl: Element): Unit = {
      val onDragEnd: js.Function1[DragEvent, Unit] = event =>
        dropZoneEls.foreach(dropEl => {
          dropEl.classList.remove("highlight")
        })

      val onDragStart: js.Function1[DragEvent, Unit] = event => {
        dropZoneEls.foreach(dropEl => {
          dropEl.classList.add("highlight")
        })
        event.dataTransfer.setData("index", dragEl.getAttribute("data-index"))
      }

      dragEl.addEventListener("dragstart", onDragStart)
      dragEl.addEventListener("dragend", onDragEnd)
    }

    def addDropListeners(dropZoneEl: Element): Unit = {
      val onDrop: js.Function1[DragEvent, Unit] = event => {
        event.preventDefault()
        val index = event.dataTransfer.getData("index").toInt
        val originalCommand = dragEls(index)
        val command = originalCommand.cloneNode(true).asInstanceOf[Element]
        command.setAttribute("draggable", "false")
        dropZoneEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
        dropZoneEl.appendChild(command)
        dropZoneEl.classList.remove("can-drop")
      }
      val onDragEnter: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.add("can-drop")
      val onDragLeave: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.remove("can-drop")
      val onDragOver: js.Function1[DragEvent, Unit] = event => event.preventDefault()

      dropZoneEl.addEventListener("drop", onDrop)
      dropZoneEl.addEventListener("dragenter", onDragEnter)
      dropZoneEl.addEventListener("dragleave", onDragLeave)
      dropZoneEl.addEventListener("dragover", onDragOver)
    }

    dragEls.foreach(addDragListeners)
    dropZoneEls.foreach(addDropListeners)
  }
}
