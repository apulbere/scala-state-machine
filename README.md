# About
Scala state machine
## Features
* fluent builder for state machine setup
* comprehensive error message when setup is incomplete or faulty, e. g.
```
Invalid state machine:
    initial state is not defined
    transition config
        direct transition
            target is not defined
            source is not defined
            event is not defined
```
* transitions
    * direct transition - from state `A` to state `B`
    * choice transition - from state `A` to state `B` if `guard == true`, otherwise to state `C`
* state listener
* transition action
* transition error action
* [unit tests](https://apulbere.github.io/scala-state-machine/Test%20Results%20-%20ScalaTests_in_'test'.html)

## Example
```
+------+     +------+     +------+
|  S1  |     |  S2  |     |  S3  |
|      |     |      |     |      |
|      |     |      |     |      |
|      +-E1-->      +-E4-->      |
+---+--+     +------+     +--^---+
    |                        |
    +-----------E2-----------+
```

```
val stateMachine = StateMachineBuilder()
            .initialState("S1")
            .configureTransitions()
                .withTransition()
                    .source("S1")
                    .target("S2")
                    .event("E1")
                .and()
                .withTransition()
                    .source("S2")
                    .target("S3")
                    .event("E4")
                .and()
                .withTransition()
                    .source("S1")
                    .target("S3")
                    .event("E2")
                .end()
            .build()
            
stateMachine.sendEvent("E1")
```