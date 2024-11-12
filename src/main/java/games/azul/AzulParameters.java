package games.azul;

import core.AbstractParameters;
import games.dotsboxes.DBParameters;

public class AzulParameters extends AbstractParameters {
    int gridWidth = 7;
    int gridHeight = 5;

    @Override
    protected AbstractParameters _copy() {
        return null;
    }

    @Override
    protected boolean _equals(Object o) {
        return false;
    }
}
