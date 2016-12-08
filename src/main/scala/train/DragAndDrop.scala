package train

import org.scalajs.dom.console
import org.scalajs.dom.document
import org.scalajs.dom
import org.scalajs.dom.raw.{DragEvent, Element, Event, HTMLCollection, MouseEvent, Node}

import scala.scalajs.js

object DragAndDrop {

  val codeLinesEl = document.querySelector(".code-lines")

  def init(): Unit = {
    generateTextEditor()
    initDragAndDrop()
  }

  def generateTextEditor(): Unit = {
    val contents = (1 to 20).map { n =>
      val empty =
        s"""<li>
           |  <div class="line-number">$n</div>
           |  <div class="command-placeholder command-placeholder-1"></div>
           |  <div class="command-placeholder command-placeholder-2"></div>
           |</li>
        """.stripMargin
//
//      val filled =
//        s"""<li>
//        |  <div class="line-number">$n</div>
//        |  <div class="command-placeholder command-placeholder-1"><div class="command wait" draggable="true" data-index="0">Wait</div></div>
//        |  <div class="command-placeholder command-placeholder-2"><div class="command goforward" draggable="true" data-index="0">Wait</div></div>
//        |</li>
//        """.stripMargin
//
//      val canDropEmpty =
//        s"""<li>
//           |  <div class="line-number">$n</div>
//           |  <div class="command-placeholder command-placeholder-1"></div>
//           |  <div class="command-placeholder command-placeholder-2 can-drop"></div>
//           |</li>
//        """.stripMargin
//
//      val canDropFilled =
//        s"""<li>
//           |  <div class="line-number">$n</div>
//           |  <div class="command-placeholder command-placeholder-1"><div class="command wait" draggable="true" data-index="0">Wait</div></div>
//           |  <div class="command-placeholder command-placeholder-2 canDrop"><div class="command goforward" draggable="true" data-index="0">Wait</div></div>
//           |</li>
//        """.stripMargin
//
//      val highlighted =
//        s"""<li>
//           |  <div class="line-number">$n</div>
//           |  <div class="command-placeholder command-placeholder-1 highlight"><div class="command wait" draggable="true" data-index="0">Wait</div></div>
//           |  <div class="command-placeholder command-placeholder-2 highlight"></div>
//           |</li>
//        """.stripMargin
//
//      val highlightedCanDrop =
//        s"""<li>
//           |  <div class="line-number">$n</div>
//           |  <div class="command-placeholder command-placeholder-1"><div class="command ifstation" draggable="true" data-index="0">Wait</div></div>
//           |  <div class="command-placeholder command-placeholder-2 highlight can-drop"></div>
//           |</li>
//        """.stripMargin
//

//      n match {
//        case 1 => empty
//        case 2 => filled
//        case 3 => canDropEmpty
//        case 4 => canDropFilled
//        case 5 => highlighted
//        case _ => highlightedCanDrop
//      }

      empty
    }.mkString("")
    codeLinesEl.innerHTML = contents
  }


  def initDragAndDrop(): Unit = {

    var droppableElements = List[Element]()

    def handleDrag(dragEl: Element): Unit = {
      dragEl.addEventListener("dragstart", (event: DragEvent) => {
        droppableElements.foreach(dropEl => {
          dropEl.classList.add("highlight")
        })
        event.dataTransfer.setData("index", dragEl.getAttribute("data-index"))
        dragEl.addEventListener("dragend", (event: MouseEvent) => {
          droppableElements.foreach(dropEl => {
            dropEl.classList.remove("highlight")
          })
        })
      })
    }



    def HandleDrop(dropEl: Element): Unit = {
      val onDrop: js.Function1[DragEvent, Unit] = event => {
        event.preventDefault()
        val index = event.dataTransfer.getData("index").toInt
        val command = dragEls(index).cloneNode(true).asInstanceOf[Element]
        dropEl.children.toList.foreach(ch => ch.parentNode.removeChild(ch))
        dropEl.appendChild(command)
        dropEl.classList.remove("can-drop")
      }
      val onDragEnter: js.Function1[DragEvent, Unit] = event => dropEl.classList.add("can-drop")
      val onDragLeave: js.Function1[DragEvent, Unit] = event => dropEl.classList.remove("can-drop")
      val onDragOver: js.Function1[DragEvent, Unit] = event => event.preventDefault()

      def addListeners() {
        dropEl.addEventListener("drop", onDrop)
        dropEl.addEventListener("dragenter", onDragEnter)
        dropEl.addEventListener("dragleave", onDragLeave)
        dropEl.addEventListener("dragover", onDragOver)
      }

      def removeListeners(): Unit = {
        dropEl.removeEventListener("drop", onDrop)
        dropEl.removeEventListener("dragenter", onDragEnter)
        dropEl.removeEventListener("dragleave", onDragLeave)
        dropEl.removeEventListener("dragover", onDragOver)
      }

      addListeners()
//      removeListeners()
    }

    lazy val dragEls = document.querySelectorAll("#commands > div").toList.asInstanceOf[List[Element]]
    dragEls.foreach(handleDrag)

    lazy val initialDropEls = document.querySelectorAll(".code-lines .command-placeholder-1").toList.asInstanceOf[List[Element]]
    initialDropEls.foreach(HandleDrop)

    droppableElements = initialDropEls
  }
}
