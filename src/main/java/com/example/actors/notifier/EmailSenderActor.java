package com.example.actors.notifier;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public class EmailSenderActor extends AbstractBehavior<NotifyActionActor.NotifyAction> {

    public EmailSenderActor(ActorContext<NotifyActionActor.NotifyAction> context) {
        super(context);
    }

    @Override
    public Receive<NotifyActionActor.NotifyAction> createReceive() {
        return newReceiveBuilder()
                .onMessage(NotifyActionActor.NotifyAction.class,this::sendEmail)
                .build();
    }

    private Behavior<NotifyActionActor.NotifyAction> sendEmail(NotifyActionActor.NotifyAction notification) {
        getContext().getLog().info("Email sent to : "+notification.getEmail());
        return this;
    }
}
