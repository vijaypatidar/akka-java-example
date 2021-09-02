package com.example.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class UserCreatorActor extends AbstractBehavior<UserCreatorActor.User> {

    private UserCreatorActor(ActorContext<User> context){
        super(context);
    }

    static abstract class User{

        private final String name;

        protected User(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }

    static class Student extends User{

        protected Student(String name) {
            super(name);
        }
    }

    static class Admin extends User{

        protected Admin(String name) {
            super(name);
        }
    }


    public static Behavior<User> create(){
        return Behaviors.setup(UserCreatorActor::new);
    }

    @Override
    public Receive<User> createReceive() {
        return newReceiveBuilder()
                .onMessage(Student.class,this::createStudent)
                .onMessage(Admin.class,this::createAdmin)
                .build();
    }

    private Behavior<User> createAdmin(Admin admin) {
        getContext().getLog().info("Admin created "+admin.getName());
        //save Admin to some persistence database
        return this;
    }

    private Behavior<User> createStudent(Student student) {
        getContext().getLog().info("Student created "+student.getName());
        //save Student to some persistence database
        return this;
    }

}
