package com.example.actors.notifier;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final ActorSystem<NotifyActionActor.NotifyAction> actionActorSystem = ActorSystem.create(NotifyActionActor.create(), "Notify");

        actionActorSystem.tell(new NotifyActionActor.NotifyAction(
                "Vijay",
                "vijay@example.com",
                "12345678"
        ));

        try {
            System.out.println("Press any key to exit");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Actor system terminated");
            actionActorSystem.terminate();
        }

    }
}
