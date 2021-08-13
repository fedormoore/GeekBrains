public interface CarsFactory {
    Sedan createSedan();
    Hatchback createHatchback();
}

public class KiaFactory implements CarsFactory {}

public class HyundaiFactory implements CarsFactory {}

public class KiaFactory implements CarsFactory {
    @Override
    public Sedan createSedan() {
        return new  KiaSedan();
    }

    @Override
    public Hatchback createHatchback() {
        return new KiaHatchback();
    }
}

public class HyundaiFactory implements CarsFactory {
    @Override
    public Sedan createSedan() {
        return new  HyundaiSedan();
    }

    @Override
    public Hatchback createHatchback() {
        return new HyundaiHatchback();
    }
}

public interface Sedan {}

public interface Hatchback {}

public class KiaHatchback implements Hatchback {
    public KiaHatchback() {
        System.out.println("Создаем KiaHatchback");
    }
}

public class KiaSedan implements Sedan {
    public KiaSedan() {
        System.out.println("Создаем KiaSedan");
    }
}

public class HyundaiHatchback implements Hatchback {
    public HyundaiHatchback () {
        System.out.println("Создаем HyundaiHatchback");
    }
}

public class HyundaiSedan implements Sedan {
    public HyundaiSedan() {
        System.out.println("Создаем HyundaiSedan");
    }
} 