import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Calc{
    public int add(int a, int b){
        return a+b;
    }
}

public class TestCalc {
    @Test
    public void testAddition(){
        Calc calc = new Calc();
        Assertions.assertEquals(3, calc.add(1,2));
    }
}
