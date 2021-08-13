interface Command {
    void execute();
}

class Car {

    public void startEngine() {
        System.out.println("Двигатель запущен");
    }

    public void stopEngine() {
        System.out.println("Двигатель остановлен");
    }

}

class StartCar implements Command {

    Car car;

    public StartCar(Car car) {
        this.car = car;
    }

    public void execute() {
        car.startEngine();
    }

}

class StopCar implements Command {

    Car car;

    public StopCar(Car car) {
        this.car = car;
    }

    public void execute() {
        car.stopEngine();
    }

}

class CarInvoker {

    public Command command;

    public CarInvoker(Command command){
        this.command = command;
    }

    public void execute(){
        this.command.execute();
    }

}

public class CommandTest {
    public static void main(String[] args) {
        Car car = new Car();
        StartCar startCar = new StartCar(car);
        StopCar stopCar = new StopCar(car);
        CarInvoker carInvoker = new CarInvoker(startCar);
        carInvoker.execute();
    }
}