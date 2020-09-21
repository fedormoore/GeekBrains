package ru.moore;

public class Wall implements Obstacles{

    public static final int WALL_HEIGHT = 1;

    @Override
    public boolean doStart(Participants participants) {
        int jumpHeight = participants.jump();
        if (WALL_HEIGHT >= jumpHeight){
            System.out.println("I failed to jump");
            return true;
        }else{
            System.out.println("I managed to jump over");
            return false;
        }
    }

}
