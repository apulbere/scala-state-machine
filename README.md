# About
state machine ... 
experimenting with scala ...

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