package ru.moore;

public class Animal {

    private int run;
    private int swim;
    private double jump;

    private int restrictionsRun;
    private int restrictionsSwim;
    private int restrictionsJump;

    public Animal(int restrictionsRun, int restrictionsSwim, int restrictionsJump) {
        this.restrictionsRun = restrictionsRun;
        this.restrictionsSwim = restrictionsSwim;
        this.restrictionsJump = restrictionsJump;
    }

    public void canRun(int run) {
        this.run = run;
        String can = (run == restrictionsRun) ? "true" : "false";
        System.out.println("Результат:run:" + can);
    }

    public void canSwim(int swim) {
        this.swim = swim;
        String can = (swim == restrictionsSwim) ? "true" : "false";
        System.out.println("Результат:swim:" + can);
    }

    public void canJump(double jump) {
        this.jump = jump;
        String can = (jump == restrictionsJump) ? "true" : "false";
        System.out.println("Результат:jump:" + can);
    }

}
