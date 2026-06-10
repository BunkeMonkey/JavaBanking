package bankprojekt.intervalle;

/**
 * Repraesentiert ein Intervall
 * @param <T> der Typ der Intervallgrenzen
 */
public class Intervall<T extends Comparable<? super T> > {

    /**
     * die untere Grenze des Intervalls
     */
    private T lowerLimit;

    /**
     * die obere Grenze des Intervalls
     */
    private T upperLimit;


    /**
     * Erzeugt ein Intervall mit den gegebenen Grenzen
     * @param unterGrenze die untere Grenze
     * @param oberGrenze die obere Grenze
     * @throws IllegalArgumentException wenn eine der Grenzen null ist
     */
    public Intervall(T unterGrenze, T oberGrenze) throws IllegalArgumentException{
        if (unterGrenze == null || oberGrenze == null){
            throw new IllegalArgumentException("Grenzen duerfen nicht null sein");
        }
        this.lowerLimit = unterGrenze;
        this.upperLimit = oberGrenze;
    }

    /**
     * Gibt zurueck, ob das Intervall leer ist
     * @return true, wenn das Intervall leer ist, sonst false
     */
    public boolean isLeer(){
        if (upperLimit.compareTo(lowerLimit) > 0 ) {
            return false;
        }
        return true;
    }

    /**
     * Gibt zurueck, ob der gegebene Wert im Intervall enthalten ist
     * @param wert der Wert, der geprueft werden soll
     * @param <T1> der Typ des Wertes
     * @return true, wenn der Wert im Intervall enthalten ist, sonst false
     *
     */
    //write the type of the T1 parameter here so that i can compare a TimeStamp object to a Time
    public <T1 extends Comparable<? super T>> boolean enthaelt(T1 wert) {
        if (wert == null){
            return false;
        }

        if (wert.compareTo(lowerLimit) >= 0 && wert.compareTo(upperLimit) <= 0){
            return true;
        }
        return false;
    }



    /**
     * Gibt den Schnitt dieses Intervalls mit einem anderen zurueck
     * @param anderes das andere Intervall
     * @return das Intervall, das den Schnitt repraesentiert
     * @param <T2> der Typ des anderen Intervalls
     * @throws IllegalArgumentException wenn das andere Intervall null ist
     */

    public <T2 extends T> Intervall<T> schnitt( Intervall<T2> anderes) throws IllegalArgumentException {
        if (anderes == null){
            throw new IllegalArgumentException("Intervall darf nicht null sein");
        }

        T newLowerLimit;
        if (anderes.lowerLimit.compareTo(this.lowerLimit) > 0) {
            newLowerLimit = anderes.lowerLimit;

        } else {
            //if the other one wasnt the limit then it is either this one or theyre equal

            newLowerLimit = this.lowerLimit;
        }
        T newUpperLimit;


        if (anderes.upperLimit.compareTo(this.upperLimit) < 0) {
            newUpperLimit = anderes.upperLimit;

        } else {
            //if the other one wasnt the limit then it is either this one or theyre equal
            newUpperLimit = this.upperLimit;
        }

        return new Intervall<>(newLowerLimit,newUpperLimit);


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lower: " + lowerLimit);
        sb.append(System.lineSeparator());
        sb.append("Upper: " + upperLimit);
        sb.append(System.lineSeparator());
        sb.append("---------------");
        return sb.toString();
    }
}
