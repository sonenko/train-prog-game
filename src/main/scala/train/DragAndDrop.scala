package train

import org.scalajs.dom.console
import org.scalajs.dom.document
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, Event, HTMLCollection, Node, MouseEvent, DragEvent}

object DragAndDrop {

  val codeEl = document.getElementById("code")

  def init(): Unit = {
    generateTextEditor()
  }

  def generateTextEditor(): Unit = {
    val contents = (1 to 20).map { n =>
      s"""<li class="command-line">
         |  <div class="line-number">$n</div>
         |  <div class="command-placeholder-1"></div>
         |  <div class="command-placeholder-2"></div>
         |</li>
        """.stripMargin
    }.mkString("")
    codeEl.innerHTML = contents
  }


  //
  //  private val dragZoneEl = dom.document.getElementById("commands-box")
  //  private val dropZoneEl = dom.document.getElementById("code-content")
  //  val dropPlaceholders: List[Element] = dropZoneEl.children.toList
  //  val dropPlaceholdersExample: Element = dropPlaceholders.head.cloneNode(true).asInstanceOf[Element]
  //  private val bodyEl = dom.document.body
  //
  //  // drag
  //  val dragElements: List[Element] = dragZoneEl.children.toList
  //  dragElements.foreach(addDragListener)
  //
  //  def addDragListener(gragEl: Element): Unit =
  //    gragEl.addEventListener("dragstart", (event: DragEvent) => {
  //      console.log(gragEl)
  //      dropZoneEl.classList.add("highlight")
  //      console.log(gragEl.getAttribute("data-index"))
  //      event.dataTransfer.setData("index", gragEl.getAttribute("data-index"))
  //      gragEl.addEventListener("dragend", (event: MouseEvent) => {
  //        dropZoneEl.classList.remove("highlight")
  //      })
  //    })
  //
  //  // drop
  //  dropPlaceholders.foreach(addDropListener)
  //
  //  def addDropListener(palceholderEl: Element): Unit = {
  //    palceholderEl.addEventListener("drop", (event: DragEvent) => {
  //      event.preventDefault()
  //      val index = event.dataTransfer.getData("index").toInt
  //      val command = dragElements(index).cloneNode(true).asInstanceOf[Element]
  //
  //      if (index != 3) {
  ////        el.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
  //        palceholderEl.appendChild(command)
  //        palceholderEl.classList.remove("over")
  //        addDragListener(command)
  //        addDropListener(command)
  //      } else {
  //        // el
  ////        el.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
  //        palceholderEl.appendChild(command)
  //        palceholderEl.classList.remove("over")
  //        // right placeholder
  //        val placeholder = document.createElement("div")
  //        placeholder.classList.add("ifstation-placeholder")
  //        palceholderEl.appendChild(placeholder)
  //        addDropListener(placeholder)
  //        addDragListener(command)
  //        addDropListener(command)
  //      }
  //    })
  //
  //    palceholderEl.addEventListener("dragenter", (event: Event) => {
  //      palceholderEl.classList.add("over")
  //      palceholderEl.addEventListener("dragleave", (event: Event) => {
  //        palceholderEl.classList.remove("over")
  //      })
  //    })
  //
  //    palceholderEl.addEventListener("dragover", (event: Event) => event.preventDefault())
  //  }
}
