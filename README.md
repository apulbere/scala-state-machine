# About
Scala state machine
## Features (described by unit tests)

* StateListenerSpec
  * state listener
    * should be called on state change
    * should not be called when state is not changed
* DirectTransitionSpec
  * the state machine
    * should acquire S2 state when E1 is sent
    * should acquire S3 state when E4 is sent
    * should acquire S3 state after 2 events are sent
    * should preserve state for an unknown event
* ChoiceTransitionSpec
  * the state machine
    * should acquire S2 state when first guard returns true
    * should acquire S3 state when first guard returns false and second true
    * should acquire S4 state when both guards return false
    * should acquire S4 state when first guard returns false and second choice is not defined
* TransitionActionSpec
  * action
    * should be executed when a transition is triggered
    * should not be executed when a transition without action is triggered
  * error action
    * should be executed when an error occurs during transition
* ValidatorSpec
  * the state machine builder
    * should throw an exception with a detailed message when building an invalid choice transition
    * should throw an exception with a detailed message when building an invalid direct transition

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