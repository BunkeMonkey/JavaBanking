package bankprojekt.verarbeitung;

import java.util.Comparator;

public class KundenComparator implements Comparator<Kunde> {

    @Override
    public int compare(Kunde k1, Kunde k2) {
        if (k1.getGeburtstag().isBefore(k2.getGeburtstag() ) ){
            return -1;
        }else if (k1.getGeburtstag().isAfter(k2.getGeburtstag())){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
