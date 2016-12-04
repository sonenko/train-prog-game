package train

import org.scalajs.dom

object CodeEditor {
  def addLineNumbers():Unit = {
    val linesEl = dom.document.getElementById("code-lines")
    (1 to 20).foreach(n => {
      val lineNumberEl = dom.document.createElement("div")
      lineNumberEl.innerHTML = n.toString
      linesEl.appendChild(lineNumberEl)
    })
  }
}
