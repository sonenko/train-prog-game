package train
package editor

import org.scalajs.dom.document
import org.scalajs.dom.raw.Element
import train.state.Train

object Editor {
  def init (): Unit = {
    val codeLinesEl: Element = document.querySelector(".code-lines")
    codeLinesEl.innerHTML = (1 to 10).map { n =>
      s"""<li>
         |  <div class="line-number">$n</div>
         |  <div class="command-placeholder command-placeholder-1 droppable"></div>
         |  <div class="command-placeholder command-placeholder-2"></div>
         |</li>
      """.stripMargin
    }.mkString("")
  }

  def onTrainAction(train: Train): Unit = {
    val id = "active-dot-" + train.name
    val el = Option(document.getElementById(id)).getOrElse{
      val element = document.createElement("div")
      element.id = id
      element.classList.add("active-dot")
      element.classList.add("active-dot-" + train.name)
      element
    }
    lineNumbers(train.line).appendChild(el)
  }

  def onStop(): Unit = {
    document.getElementsByClassName("active-dot").toList.foreach(el => {
      el.parentNode.removeChild(el)
    })
  }

  private lazy val lineNumbers = document.querySelectorAll(".line-number")
}
