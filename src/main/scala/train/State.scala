package train

object State {

  case class Train(nowX: Int, toX: Int, color: String)

  var rearTrain = Train(50, 50, "#425275")
  var leadTrain = Train(400, 400, "#625252")
  def trainMoved(tr: Train): Train = {
    if (tr.nowX <= tr.toX) tr.copy(nowX = tr.nowX + 3)
    else tr
  }

  def tick(): Unit = {
    rearTrain = trainMoved(rearTrain)
    leadTrain = trainMoved(leadTrain)
  }

  def goForward(): Unit = {
    rearTrain = rearTrain.copy(toX = rearTrain.nowX + 50)
    leadTrain = leadTrain.copy(toX = leadTrain.nowX + 50)
  }
}