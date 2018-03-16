# About
Scala state machine
## Features (described by unit tests)

* TransitionFlowSpec
    * the state machine
        * should acquire S2 status when E1 is sent
        * should acquire S3 status E4 is sent
        * should acquire S3 status after 2 events are sent
        * should preserve status for an unknown event
* GlobalListenerSpec
    * global listener
        * should be called on status change
        * should not be called when status is not changed
* TransitionActionSpec
    * action
        * should be executed when the transition is triggered
        * should not be executed when a transition without action is triggered
    * error action
        * should be executed when an error occurs during transition
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
val stateMachine = new StateMachineBuilder[String, String]()
            .withInitialState("S1")
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