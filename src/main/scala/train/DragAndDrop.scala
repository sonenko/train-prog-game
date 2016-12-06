package train

import org.scalajs.dom.console
import org.scalajs.dom.document
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, Event, HTMLCollection, Node, MouseEvent, DragEvent}

object DragAndDrop {

  def init(): Unit = {}

  private val dragZoneEl = dom.document.getElementById("commands-box")
  private val dropZoneEl = dom.document.getElementById("code-content")
  val dropPlaceholders: List[Element] = dropZoneEl.children.toList
  val dropPlaceholdersExample: Element = dropPlaceholders.head.cloneNode(true).asInstanceOf[Element]
  private val bodyEl = dom.document.body

  // drag
  val dragElements: List[Element] = dragZoneEl.children.toList
  dragElements.foreach(addDragListener)

  def addDragListener(el: Element): Unit =
    el.addEventListener("dragstart", (event: DragEvent) => {
      dropZoneEl.classList.add("highlight")
      console.log(el.getAttribute("data-index"))
      event.dataTransfer.setData("index", el.getAttribute("data-index"))
      el.addEventListener("dragend", (event: MouseEvent) => {
        dropZoneEl.classList.remove("highlight")
      })
    })

  // drop
  dropPlaceholders.foreach(addDropListener)

  def addDropListener(el: Element): Unit = {
    el.addEventListener("drop", (event: DragEvent) => {
      event.preventDefault()
      val index = event.dataTransfer.getData("index").toInt
      val command = dragElements(index).cloneNode(true).asInstanceOf[Element]
      console.log(command.outerHTML)
      el.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
      el.appendChild(command)
      el.classList.remove("over")
      addDragListener(command)
      addDropListener(command)
    })

    el.addEventListener("dragenter", (event: Event) => {
      el.classList.add("over")
      el.addEventListener("dragleave", (event: Event) => {
        el.classList.remove("over")
      })
    })

    el.addEventListener("dragover", (event: Event) => event.preventDefault())
  }
}
