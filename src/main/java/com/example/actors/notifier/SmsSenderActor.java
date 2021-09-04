package com.example.actors.notifier;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public class SmsSenderActor extends AbstractBehavior<NotifyActionActor.NotifyAction> {

    public SmsSenderActor(ActorContext<NotifyActionActor.NotifyAction> context) {
        super(context);
    }

    @Override
    public Receive<NotifyActionActor.NotifyAction> createReceive() {
        return newReceiveBuilder()
                .onMessage(NotifyActionActor.NotifyAction.class, this::sendSms)
                .build();
    }

    private Behavior<NotifyActionActor.NotifyAction> sendSms(NotifyActionActor.NotifyAction notification) {
        getContext().getLog().info("Sms sent to : " + notification.getPhone());
        return this;
    }
}
