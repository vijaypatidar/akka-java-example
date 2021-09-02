package com.example.actors.notifier;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class NotifyActionActor extends AbstractBehavior<NotifyActionActor.NotifyAction> {

    private final ActorRef<NotifyAction> smsSenderActorActorRef;
    private final ActorRef<NotifyAction> emailSenderActorActorRef;
    public NotifyActionActor(ActorContext<NotifyAction> context) {
        super(context);
        smsSenderActorActorRef = context.spawn(Behaviors.setup(SmsSenderActor::new),"SmsSenderActor");
        emailSenderActorActorRef = context.spawn(Behaviors.setup(EmailSenderActor::new),"EmailSenderActor");
    }

    public static Behavior<NotifyAction> create() {
        return Behaviors.setup(NotifyActionActor::new);
    }

    static class NotifyAction{
        private final String name,email,phone;

        NotifyAction(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }

    @Override
    public Receive<NotifyAction> createReceive() {
        return newReceiveBuilder()
                .onMessage(NotifyAction.class,this::handleAction)
                .build();
    }

    private Behavior<NotifyAction> handleAction(NotifyAction notifyAction) {
        getContext().getLog().info("NotifyHandler");
        smsSenderActorActorRef.tell(notifyAction);
        emailSenderActorActorRef.tell(notifyAction);
        return this;
    }
}
