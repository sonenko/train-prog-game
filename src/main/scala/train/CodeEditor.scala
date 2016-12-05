package train

import org.scalajs.dom

object CodeEditor {
  private val numLines = 20

  def init(): Unit = {
    addLineNumbers()
    addCodePlaceholders()
  }

  private def addLineNumbers(): Unit = {
    val linesEl = dom.document.getElementById("code-lines")
    (1 to numLines).foreach(n => {
      val lineNumberEl = dom.document.createElement("div")
      lineNumberEl.innerHTML = n.toString
      linesEl.appendChild(lineNumberEl)
    })
  }

  private def addCodePlaceholders(): Unit = {
    val codeContentEl = dom.document.getElementById("code-content")
    (1 to numLines).foreach(_ => {
      val contentPlaceHolder = dom.document.createElement("div")
      contentPlaceHolder.classList.add("command")
      codeContentEl.appendChild(contentPlaceHolder)
    })
  }
}
