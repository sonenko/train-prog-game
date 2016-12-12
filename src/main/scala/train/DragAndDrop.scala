package train

import org.scalajs.dom.document
import org.scalajs.dom.console
import org.scalajs.dom.raw.{DragEvent, Element}

import scala.scalajs.js

object DragAndDrop {

  val codeLinesEl: Element = document.querySelector(".code-lines")

  def init(): Unit = {
    generateTextEditor()
    initDragAndDrop()
  }

  def generateTextEditor(): Unit = {
    codeLinesEl.innerHTML = (1 to 20).map { n =>
      s"""<li>
         |  <div class="line-number">$n</div>
         |  <div class="command-placeholder command-placeholder-1"></div>
         |  <div class="command-placeholder command-placeholder-2"></div>
         |</li>
      """.stripMargin
    }.mkString("")
  }

  def initDragAndDrop(): Unit = {
    var dropZoneEls = document.querySelectorAll(".code-lines .command-placeholder-1").toList.map(_.asElement)
    val dragEls = document.querySelectorAll("#commands > div").toList.map(_.asElement)

    def addDragListeners(dragEl: Element): Unit = {
      def dropElems() =
        if (dragEl.classList.contains("ifstation")) dropZoneEls.filter(_.classList.contains("command-placeholder-1"))
        else dropZoneEls

      var removeDropListeners = Seq[(Unit) => Unit]()

      val onDragEnd: js.Function1[DragEvent, Unit] = event => {
        console.log("onDragEnd")
        console.log(dropElems().length)
        removeDropListeners.foreach(x => x())
        dropElems().foreach(dropEl => {
          console.log(dropEl)
          dropEl.classList.remove("highlight")
        })
      }
      val onDragStart: js.Function1[DragEvent, Unit] = event => {
        removeDropListeners = dropElems().map(dropEl => {
          dropEl.classList.add("highlight")
          addDropListeners(dropEl)
        })
        removeDropListeners.foreach(x => x())
        event.dataTransfer.setData("index", dragEl.getAttribute("data-index"))
      }
      dragEl.addEventListener("dragstart", onDragStart)
      dragEl.addEventListener("dragend", onDragEnd)
    }

    def addDropListeners(dropZoneEl: Element): Unit => Unit = {
      def addListeners(): Unit = {
        dropZoneEl.addEventListener("drop", onDrop)
        dropZoneEl.addEventListener("dragenter", onDragEnter)
        dropZoneEl.addEventListener("dragleave", onDragLeave)
        dropZoneEl.addEventListener("dragover", onDragOver)
      }
      lazy val removeListeners: Unit => Unit = _ => {
        console.log("removeListeners")
        dropZoneEl.removeEventListener("drop", onDrop)
        dropZoneEl.removeEventListener("dragenter", onDragEnter)
        dropZoneEl.removeEventListener("dragleave", onDragLeave)
        dropZoneEl.removeEventListener("dragover", onDragOver)
      }
      lazy val onDrop: js.Function1[DragEvent, Unit] = event => {
        event.preventDefault()
        val index = event.dataTransfer.getData("index").toInt
        val originalCommand = dragEls(index)
        val command = originalCommand.cloneNode(true).asElement
        command.setAttribute("draggable", "false")
        dropZoneEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
        dropZoneEl.appendChild(command)
        if (dropZoneEl.classList.contains("command-placeholder-1")) {
          val nextDropZone = dropZoneEl.parentNode.asElement.querySelector(".command-placeholder-2")
          nextDropZone.classList.remove("highlight")
          nextDropZone.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
          // first column
          if (index != 3) {
            // any
            dropZoneEls = dropZoneEls.filter(x => x != nextDropZone)
          } else {
            // ifStation
            dropZoneEls = dropZoneEls.filter(x => x != nextDropZone.children(0))
            addDropListeners(nextDropZone)
            dropZoneEls = nextDropZone :: dropZoneEls
          }
        } else if (dropZoneEl.classList.contains("command-placeholder-2")) {
          // second column
          if (index != 3) {
            // any
            dropZoneEl.appendChild(command)
          } else {
            // ifStation
            console.warn("unexpected ifstation command in second column")
          }
        } else {
          console.warn("unexpected placeholder class in dropZone", dropZoneEl)
        }
        dropZoneEl.classList.remove("can-drop")
      }
      lazy val onDragEnter: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.add("can-drop")
      lazy val onDragLeave: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.remove("can-drop")
      lazy val onDragOver: js.Function1[DragEvent, Unit] = event => event.preventDefault()

      addListeners()
      removeListeners
    }



    dragEls.foreach(addDragListeners)
    dropZoneEls.foreach(addDropListeners)
  }
}