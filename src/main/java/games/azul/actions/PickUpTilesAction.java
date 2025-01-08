package games.azul.actions;

import core.AbstractGameState;
import core.actions.AbstractAction;
import core.interfaces.IPrintable;
import games.azul.AzulGameState;
import games.azul.tiles.AzulTile;

public class PickUpTilesAction extends AbstractAction implements IPrintable {
    public boolean fromCenter;

    public AzulTile tile;
    public AzulGameState gs;


    public PickUpTilesAction(int tileNum, boolean fromCenter) {
        this.fromCenter = fromCenter;
    }

    public void PickUpTilesFromCenter(){}
    public void PickUpTilesFromFactory(){}
    private void setTile(AzulTile tile, int playerRow){

    }

    @Override
    public boolean execute(AbstractGameState gs) {
        AzulGameState state = (AzulGameState) gs;
        int playerID = gs.getCurrentPlayer();

        // This means player is picking up tile from factory
        if (!this.fromCenter){
            System.out.println("Picking up tile from center");
        }
        else{ // This means player is picking up tile from center
            System.out.println("Picking up tile from factories");
        }
        return true;
    }

    @Override
    public AbstractAction copy() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getString(AbstractGameState gameState) {
        return "";
    }
}
