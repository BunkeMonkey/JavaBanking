package bankprojekt.mathe;

/**
 * Klasse zum finden von Nullstellen

 */
public class Nullstellen {

    /**
     * Findet eine Nullstelle einer Funktion in einem Intervall
     *
     * @param function die Funktion
     * @param lower    untere Grenze des Intervalls
     * @param upper    obere Grenze des Intervalls
     * @return die Nullstelle (falls existent)
     * @throws IllegalArgumentException wenn das Intervall leer ist
     * @throws ArithmeticException      wenn keine Nullstelle gefunden wurde
     */
    public static double nullStellenFinden(MatheFunktion function, double lower, double upper)
            throws IllegalArgumentException, ArithmeticException {
        if (upper < lower) {
            throw new IllegalArgumentException("intervall ist leer");
        }
        boolean guaranteedRoot = false;
        //only negative when exactly one of them is negative
        if (function.f(lower) * function.f(upper) < 0) {
            guaranteedRoot = true;
        }


        while (upper - lower > 0.01) {

            double middle = (upper + lower) / 2;

            if (function.f(middle) == 0) {
                return middle;
                //if this is less than zero we know that only one was negative and the sign has changed
                //that means we have to lower the upper bound down to it
            } else if (function.f(lower) * function.f(middle) < 0) {
                upper = middle;

                //means the sign didnt change so we move the lower bound closer to the upper
            } else {
                lower = middle;
            }

        }

        if (guaranteedRoot) {
            return upper;
        } else {
            if (function.f(upper) < 0.1 && function.f(upper) > -0.1) {
                return upper;
            } else {
                throw new ArithmeticException("keine nullstelle gefunden");
            }

        }
    }

}
