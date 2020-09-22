package ru.moore;

public class RunningTrack implements Obstacles{

    public static final int RUN_LENGTH = 100;

    @Override
    public boolean doStart(Participants participants) {
        int runLength = participants.run();
        if (RUN_LENGTH >= runLength){
            System.out.println("I failed to run");
            return true;
        }else{
            System.out.println("I managed to run over");
            return false;
        }
    }

}
