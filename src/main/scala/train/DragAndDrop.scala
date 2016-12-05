package train

import org.scalajs.dom.console
import org.scalajs.dom
import org.scalajs.dom.raw.{Element, Event, HTMLCollection, Node, MouseEvent}

object DragAndDrop {

  def init(): Unit = {}

  private val dragZoneEl = dom.document.getElementById("commands-box")
  private val dropZoneEl = dom.document.getElementById("code-content")
  val dropPlaceholders: List[Element] = dropZoneEl.children.toList
  private val bodyEl = dom.document.body

  // drag
  val dragElements: List[Element] = dragZoneEl.children.toList
  dragElements.foreach(el => {
    el.addEventListener("dragstart", (event: MouseEvent) => {
      dropZoneEl.classList.add("highlight")
      el.addEventListener("dragend", (event: MouseEvent) => {
        dropZoneEl.classList.remove("highlight")
      })
    })
  })


  // drop
  dropPlaceholders.foreach( el => {
    el.addEventListener("dragenter", (event: Event) => {
      el.classList.add("over")
      el.addEventListener("dragleave", (event: Event) => {
        el.classList.remove("over")
      })
      // actual drop

    })

    el.addEventListener("drop", (event: Event) => {
      console.log("fucking shit")
      el.innerHTML = "hello world"
    })
  })
}
