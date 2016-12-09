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
      def addListeners(el: Element): Unit = {
        el.addEventListener("drop", onDrop)
        el.addEventListener("dragenter", onDragEnter)
        el.addEventListener("dragleave", onDragLeave)
        el.addEventListener("dragover", onDragOver)
      }
      def removeListeners(el: Element):Unit = {
        console.log(123123)

        el.removeEventListener("drop", onDrop)
        el.removeEventListener("dragenter", onDragEnter)
        el.removeEventListener("dragleave", onDragLeave)
        el.removeEventListener("dragover", onDragOver)
      }
      lazy val onDrop: js.Function1[DragEvent, Unit] = event => {
        event.preventDefault()
        val index = event.dataTransfer.getData("index").toInt
        val originalCommand = dragEls(index)
        val command = originalCommand.cloneNode(true).asElement
        command.setAttribute("draggable", "false")
        if (dropZoneEl.classList.contains("command-placeholder-1")) {
          // first column
          if (index != 3) {
            // any
            console.log("first column: index != 3")
            dropZoneEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
            val nextDropZone = dropZoneEl.parentNode.asElement.querySelector(".command-placeholder-2")
            dropZoneEls = dropZoneEls.filter(x => x != nextDropZone)
            nextDropZone.classList.remove("highlight")
            nextDropZone.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
            removeListeners(nextDropZone)
            addListeners(nextDropZone)
            dropZoneEl.appendChild(command)
            dropZoneEl.classList.remove("can-drop")
          } else {
            // ifStation
            console.log("first column: ifStation")
            dropZoneEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
            val nextDropZone = dropZoneEl.parentNode.asElement.querySelector(".command-placeholder-2")
            dropZoneEls = dropZoneEls.filter(x => x != nextDropZone.children(0))
            nextDropZone.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
            removeListeners(nextDropZone)
            dropZoneEl.appendChild(command)
            dropZoneEl.classList.remove("can-drop")
            // add next drop zone
            dropZoneEls = nextDropZone :: dropZoneEls
            addListeners(nextDropZone)
          }
        } else if (dropZoneEl.classList.contains("command-placeholder-2")) {
          // second column
          if (index != 3) {
            // any
            console.log(123)
            dropZoneEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
            dropZoneEl.appendChild(command)
            dropZoneEl.classList.remove("can-drop")
          } else {
            // ifStation
          }
        } else {
          console.warn("unexpected placeholder class in dropZone", dropZoneEl)
        }
      }
      lazy val onDragEnter: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.add("can-drop")
      lazy val onDragLeave: js.Function1[DragEvent, Unit] = _ => dropZoneEl.classList.remove("can-drop")
      lazy val onDragOver: js.Function1[DragEvent, Unit] = event => event.preventDefault()

      addListeners(dropZoneEl)
    }



    dragEls.foreach(addDragListeners)
    dropZoneEls.foreach(addDropListeners)
  }
}