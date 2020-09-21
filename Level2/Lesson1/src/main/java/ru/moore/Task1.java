package ru.moore;

import java.util.Random;

public class Task1 {

    public static void main(String[] args) {
        Random random = new Random();

        Obstacles[] obstacles = new Obstacles[2];
        obstacles[0] = new Wall();
        obstacles[1] = new RunningTrack();

        Participants[] participants = new Participants[3];
        participants[0] = new Human(random.nextInt(100), random.nextInt(5));
        participants[1] = new Cat(random.nextInt(200), random.nextInt(7));
        participants[2] = new Robot(random.nextInt(300), random.nextInt(10));

        start(participants, obstacles);
    }

    private static void start(Participants[] participants, Obstacles[] obstacles) {
        for (Participants pa : participants) {
            for (Obstacles ob : obstacles) {
                if (!ob.doStart(pa))
                    break;
            }
        }
    }

}
