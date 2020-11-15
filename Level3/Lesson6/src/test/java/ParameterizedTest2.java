import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.moore.Task1;
import ru.moore.Task2;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

@RunWith(value = Parameterized.class)
public class ParameterizedTest2 {

    private static Task2 task2 = null;

    private int[] x1;
    private boolean x2;

    public ParameterizedTest2(int[] x1, boolean x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Parameterized.Parameters
    public static Collection abc() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 1, 1, 1, 1, 4}, true},
                {new int[]{1, 2, 4, 1, 4, 1}, false},
                {new int[]{1, 4, 1, 4, 4, 1, 1}, false}
        });
    }

    @Test
    public void test1Task1() {
        Assert.assertEquals(task2.arrayCheck(x1), x2);
    }

    @Before
    public void init() {
        System.out.println("init calc");
        task2 = new Task2();
    }

}
