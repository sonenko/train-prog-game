import org.scalajs.dom.Node
import org.scalajs.dom.raw.{DOMList, DOMTokenList, Element}

import scala.scalajs.js

package object train {

  implicit class DOMListToList[T](collection: DOMList[T]) {
    def toList: List[T] = (0 until collection.length).toList.map(i => collection(i))
  }

  implicit class DOMTokenListToList(collection: DOMTokenList) {
    def toList: List[String] = (0 until collection.length).toList.map(i => collection(i))
  }

  implicit class NodeToExtension(node: Node) {
    def asElement: Element = node.asInstanceOf[Element]
  }

  def toDictionary[T](x: Any): js.Dictionary[T] = x.asInstanceOf[js.Dictionary[T]]
}
