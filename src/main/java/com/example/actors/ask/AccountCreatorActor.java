package com.example.actors.ask;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class AccountCreatorActor extends AbstractBehavior<AccountCreatorActor.Command> {

    public AccountCreatorActor(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create(){
        return Behaviors.supervise(Behaviors.setup(AccountCreatorActor::new))
                .onFailure(IllegalArgumentException.class, SupervisorStrategy.restart());
    }

    public interface Command {
    }

    public static class RequestCommand implements Command {
        private final String name;
        private final ActorRef<Reply> replyTo;

        public RequestCommand(String name,ActorRef<Reply> replyTo) {
            this.name = name;
            this.replyTo = replyTo;
        }

        public String getName() {
            return name;
        }

        public ActorRef<Reply> getReplyTo() {
            return replyTo;
        }
    }

    public static class Reply implements Command {
        private final String message;

        public Reply(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(RequestCommand.class,this::handleRequest)
                .build();
    }

    private Behavior<Command> handleRequest(RequestCommand req) {
        if (req.getName().length()!=0){
            ///
            req.getReplyTo().tell(new Reply("Hey! " + req.getName() + " your account is created successfully"));
        }else{
            throw new IllegalArgumentException("Invalid username");
        }
        return this;
    }

}
