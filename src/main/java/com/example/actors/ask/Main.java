package com.example.actors.ask;

import akka.actor.typed.ActorSystem;
import static akka.actor.typed.javadsl.AskPattern.ask;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        ActorSystem<AccountCreatorActor.Command> actorSystem = ActorSystem.create(AccountCreatorActor.create(),"RootActor");

        CompletionStage<AccountCreatorActor.Reply> completableFuture = ask(actorSystem,
                replyTo->new AccountCreatorActor.RequestCommand("Vijay",replyTo),
                Duration.ofMinutes(1),
                actorSystem.scheduler()
                );

        try {
            System.out.println(completableFuture.toCompletableFuture().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
