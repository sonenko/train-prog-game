### Programming game "Train" 
Web game, where player should write pseudo code 
that will be used by 2 trains and will entail situation 
when rear train will catch up with the first

### Demo
https://sonenko.github.io/train-prog-game/index-opt.html

#### run in dev mode
```
sbt ~fastOptJS
// navigate browser to http://localhost:12345/target/scala-2.12/classes/index-dev.html
```

#### compile for prod
```
sbt fullOptJS
```

#### About, history
Initially - playing with ScalaJS, and investigating if it is 
comfortable for me to use ScalaJS in production.