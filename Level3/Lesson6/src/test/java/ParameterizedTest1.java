import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.moore.Task1;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class ParameterizedTest1 {

    private static Task1 task1 = null;

    private int[] x1;
    private int[] x2;

    public ParameterizedTest1(int[] x1, int[] x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Parameters
    public static Collection abc() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 4, 2, 4, 3, 1, 7}, new int[]{3, 1, 7}},
                {new int[]{1, 2, 4, 4, 4, 3, 3, 1, 7}, new int[]{3, 3, 1, 7}},
                {new int[]{1, 2, 5, 4, 2, 0, 3, 1, 7}, new int[]{2, 0, 3, 1, 7}}
        });
    }

    @Test
    public void test1Task1() {
        Assert.assertArrayEquals(task1.arrayChange(x1), x2);
    }

    @Test(expected = RuntimeException.class)
    public void test2Task1() {
        task1.arrayChange(new int[]{1, 2, 5, 5, 2, 3, 6, 0, 7});
    }

    @Before
    public void init() {
        System.out.println("init calc");
        task1 = new Task1();
    }
}
