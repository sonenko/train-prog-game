package train

import org.scalajs.dom.raw.{Element, Event}
import org.scalajs.dom.{document, window}

trait Modal {
  val contents: Element

  private val modalClass = "modal"

  lazy val modalEl: Element = {
    val box = document.createElement("div")
    box.classList.add(modalClass)
    box.addEventListener("click", (_: Event) => {
      destroy()
    })
    val modal = document.createElement("h3")
    modal.classList.add(s"$modalClass-contents")
    modal.addEventListener("click", (e:Event) => {
      e.stopPropagation()
    })
    modal.appendChild(contents)
    box.appendChild(modal)
    box
  }

  def show(): Unit = {
    document.body.appendChild(modalEl)
  }

  def destroy(): Unit = {
    document.querySelectorAll(s".$modalClass").toList.foreach{ el =>
      el.parentNode.removeChild(el)
    }
  }
}

object Modals {
  class WinModal extends Modal {
    override val contents: Element = {
      val html =
        """
          |Wow, Victory :)<br>
          |IMHO you are capable to learn programming languages :)<br>
          |share:<br>
          |<div id="socials">
          |    <a class="clickable" target="_blank" href="http://www.facebook.com/share.php?u=https%3A%2F%2Fsonenko.github.io%2Ftrain-prog-game%2Findex-opt.html&title=I%27m%20winner%20at%20train-programming-game"><i class="fa fa-facebook-square" aria-hidden="true"></i></a>
          |    <a class="clickable" target="_blank" href="http://www.linkedin.com/shareArticle?mini=true&url=https%3A%2F%2Fsonenko.github.io%2Ftrain-prog-game%2Findex-opt.html&title=I%27m%20winner%20at%20train-programming-game" target="_blank"><i class="fa fa-linkedin-square" aria-hidden="true"></i></a>
          |</div>
        """.stripMargin

      val el = document.createElement("div")
      el.innerHTML = html
      el
    }
  }

  class LooseModal extends Modal {
    override val contents: Element = {
      val html =
        """
          |You loose
          |<span class="clickable" id="new-game">start new game <i class="fa fa-refresh"></i></span>
          | or
          |<span class="clickable" id="continue-game">continue current game <i class="fa fa-play"></i></span>
        """.stripMargin

      val el = document.createElement("div")
      el.innerHTML = html
      val newGameEl = el.querySelector("#new-game")
      newGameEl.addEventListener("click", (_: Event)=> {
        window.location.reload()
      })
      val continueGameEl = el.querySelector("#continue-game")
      continueGameEl.addEventListener("click", (_: Event)=> {
        destroy()
      })
      el
    }
  }

  def lose(): Unit = (new LooseModal).show()
  def win(): Unit = (new WinModal).show()
}