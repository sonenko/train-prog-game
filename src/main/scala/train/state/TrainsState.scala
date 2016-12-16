package train
package state

object TrainsState {
  var rearTrain: Train = _
  var leadTrain: Train = _

  def reset(): Unit = {
    rearTrain = new Train(50, "#425275", Commands.extract)
    leadTrain = new Train(400, "#625252", Commands.extract)
  }

  reset()

  def tick(): Unit = {
    rearTrain.tick()
    leadTrain.tick()
  }

  def turn(): Unit = {
    leadTrain.turn()
    rearTrain.turn()
  }
}

