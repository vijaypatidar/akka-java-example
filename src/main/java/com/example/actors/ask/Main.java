package com.example.actors.ask;

import akka.actor.typed.ActorSystem;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.actor.typed.javadsl.AskPattern.ask;

public class Main {
    public static void main(String[] args) {
        ActorSystem<AccountCreatorActor.Command> actorSystem = ActorSystem.create(AccountCreatorActor.create(),"RootActor");

        creteAccount(actorSystem,"");
        creteAccount(actorSystem,"vijay");
        try {
            System.out.println("Enter to exit");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            actorSystem.terminate();
        }
    }
    public static void creteAccount(ActorSystem<AccountCreatorActor.Command> actorSystem,String name){
        CompletionStage<AccountCreatorActor.Reply> completableFuture = ask(actorSystem,
                replyTo->new AccountCreatorActor.RequestCommand(name,replyTo),
                Duration.ofMinutes(1),
                actorSystem.scheduler()
        );

        completableFuture.whenComplete((res,fail)->{
            if (res!=null){
                System.out.println(res.getMessage());
            }else if (fail!=null){
                System.out.println(fail.getMessage());
            }
        });
    }
}
