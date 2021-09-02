package com.example.actors;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final ActorSystem<UserCreatorActor.User> userActorSystem = ActorSystem.create(UserCreatorActor.create(), "user");

        userActorSystem.tell(new UserCreatorActor.Student("Vijay"));
        userActorSystem.tell(new UserCreatorActor.Student("Ashwin"));
        userActorSystem.tell(new UserCreatorActor.Admin("Ram"));

        try {
            System.out.println("Press any key to exit");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Actor system terminated");
            userActorSystem.terminate();
        }

    }
}
