package ru.moore;

public class Cat implements Participants{

    private int run;
    private int jump;

    public Cat(int run, int jump) {
        this.run = run;
        this.jump = jump;
    }

    @Override
    public int run() {
        System.out.println(getClass().getSimpleName()+" is run");
        return run;
    }

    @Override
    public int jump() {
        System.out.println(getClass().getSimpleName()+" is jump");
        return jump;
    }

}
