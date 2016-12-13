package train

import org.scalajs.dom.{console, document}
import org.scalajs.dom.raw.{DragEvent, Element}

import scala.scalajs.js

object DragAndDrop {

  def init(): Unit = {
    generateTextEditor()
    initDragAndDrop()
  }

  def generateTextEditor(): Unit = {
    val codeLinesEl: Element = document.querySelector(".code-lines")
    codeLinesEl.innerHTML = (1 to 20).map { n =>
      s"""<li>
         |  <div class="line-number">$n</div>
         |  <div class="command-placeholder command-placeholder-1 droppable"></div>
         |  <div class="command-placeholder command-placeholder-2"></div>
         |</li>
      """.stripMargin
    }.mkString("")
  }

  def initDragAndDrop(): Unit = {
    val allDropZoneEls = document.querySelectorAll(".code-lines .command-placeholder").toList.map(_.asElement)
    val dragEls = document.querySelectorAll("#commands > div").toList.map(_.asElement)

    def addDragListeners(dragEl: Element): Unit = {
      def dropElems() =
        if (dragEl.classList.contains("ifstation")) allDropZoneEls.filter(_.classList.contains("command-placeholder-1"))
        else allDropZoneEls.filter(_.classList.contains("droppable"))

      val onDragEnd: js.Function1[DragEvent, Unit] = _ => {
        dropElems().foreach(dropEl => {
          dropEl.classList.remove("highlight")
          val dropElDict = toDictionary[js.Function0[Unit]](dropEl)
          dropElDict("removeDropListeners")()
        })
      }
      val onDragStart: js.Function1[DragEvent, Unit] = event => {
        dropElems().foreach(dropEl => {
          dropEl.classList.add("highlight")
          val dropElDict = toDictionary[js.Function0[Unit]](dropEl)
          dropElDict("addDropListeners")()
        })
        event.dataTransfer.setData("index", dragEl.getAttribute("data-index"))
      }
      dragEl.addEventListener("dragstart", onDragStart)
      dragEl.addEventListener("dragend", onDragEnd)
    }

    def setupDropListeners(dropZoneEl: Element): Unit = {
      val dropZoneDict = toDictionary[js.Function0[Unit]](dropZoneEl)
      dropZoneDict.update("addDropListeners", addListeners)
      dropZoneDict.update("removeDropListeners", removeListeners)

      lazy val addListeners: js.Function0[Unit] = () => {
        dropZoneEl.addEventListener("drop", onDrop)
        dropZoneEl.addEventListener("dragenter", onDragEnter)
        dropZoneEl.addEventListener("dragleave", onDragLeave)
        dropZoneEl.addEventListener("dragover", onDragOver)
      }
      lazy val removeListeners: js.Function0[Unit] = () => {
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
            nextDropZone.classList.remove("droppable")
            toDictionary[js.Function0[Unit]](nextDropZone)("removeDropListeners")()
          } else {
            // ifStation
            nextDropZone.classList.add("droppable")
          }
        } else if (dropZoneEl.classList.contains("command-placeholder-2")) {
          // second column
          if (index != 3) {
            // any
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
    }

    allDropZoneEls.foreach(setupDropListeners)
    dragEls.foreach(addDragListeners)
  }
}