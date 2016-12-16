package train

import org.scalajs.dom.{MouseEvent, document, window}
import train.editor.Editor
import train.state._

object Player {
  private val runButtonEl = document.getElementById("run-button")
  private var intervalId = -1
  def isRunning: Boolean = intervalId > 0

  def init(): Unit = {
    runButtonEl.addEventListener("click", (_: MouseEvent) => {
      if (!isRunning) start()
      else stop()
    })
  }

  def start(): Unit = {
    TrainsState.reset()
    intervalId = window.setInterval(() => {
      TrainsState.turn()
    }, 800)
    toggleRunButton()
  }

  def stop(): Unit = {
    window.clearInterval(intervalId)
    intervalId = -1
    window.setTimeout(() => {
      TrainsState.reset()
      Editor.onStop()
      toggleRunButton()
    }, 0)
  }

  def toggleRunButton(): Unit = {
    if (isRunning) {
      runButtonEl.classList.add("running")
    } else {
      runButtonEl.classList.remove("running")
    }
  }
}
