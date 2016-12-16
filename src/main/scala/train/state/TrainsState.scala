package train
package state

object TrainsState {
  var rearTrain: Train = new Train(50, "#425275", "rear")
  var leadTrain: Train = new Train(400, "#625252", "lead")

  def reset(): Unit = {
    val commands = Commands.extract
    rearTrain.reset(commands)
    leadTrain.reset(commands)
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

