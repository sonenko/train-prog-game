package train.editor

import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

object Editor {
  def init (): Unit = {
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
}
