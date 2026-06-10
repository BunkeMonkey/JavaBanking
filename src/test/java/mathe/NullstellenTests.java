package mathe;

import bankprojekt.mathe.MatheFunktion;
import bankprojekt.mathe.Nullstellen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NullstellenTests {

    @Test
    @DisplayName("f(x) = x^2 - 25")
    public void firstFunctionTest()  {
        MatheFunktion function = (x) -> x*x -25;
        double nullStelle = Nullstellen.nullStellenFinden(function, -10, 10 );
        assertEquals(0, function.f(nullStelle), 0.3);
    }

    @Test
    @DisplayName("g(x) = e^3x - 7")
    public void secondFunctionTest(){
        MatheFunktion function = (x) -> Math.pow(Math.E,3*x) - 7;
        double nullStelle = Nullstellen.nullStellenFinden(function, -50, 50);
        assertEquals(0, function.f(nullStelle), 0.3);
    }

    @Test
    @DisplayName("h(x) = sin(x^2) - 0,5")
    public void thirdFunctionTest(){
        MatheFunktion function = (x) -> Math.sin(x*x) - 0.5;
        double nullStelle = Nullstellen.nullStellenFinden(function,-10,10);
        assertEquals(0, function.f(nullStelle), 0.3);
    }

    @Test
    @DisplayName("k(x) = x^2 + 1")
    public void fourthFunctionTest(){
        MatheFunktion function = (x) -> x*x + 1;
        //double nullStelle = Nullstellen.nullStellenFinden(function,-50,50);
        Exception e = assertThrows(ArithmeticException.class, () -> Nullstellen.nullStellenFinden(function, -50, 50));
        assertEquals(e.getMessage(), "keine nullstelle gefunden");
    }


}
