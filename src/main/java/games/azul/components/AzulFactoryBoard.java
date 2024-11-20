package games.azul.components;

import core.CoreConstants;
import core.components.Component;
import games.azul.AzulGameState;
import games.azul.tiles.AzulTile;

import java.util.HashMap;

public class AzulFactoryBoard extends Component {
    public HashMap<AzulTile, Integer> factoryBoard;
    int defaultTileCount = 0;

    public AzulFactoryBoard() {
        super(CoreConstants.ComponentType.BOARD, "AzulFactoryBoard");
    }

    public AzulFactoryBoard(int componentID) {
        super(CoreConstants.ComponentType.BOARD, "AzulFactoryBoard", componentID);
    }

    public void initialise(AzulGameState gs){
        this.factoryBoard = new HashMap<>();

        for (AzulTile tile : AzulTile.values()){
            factoryBoard.put(tile, defaultTileCount);
        }
    }

    @Override
    public Component copy() {
        AzulFactoryBoard copy = new AzulFactoryBoard(componentID);
        copyComponentTo(copy);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        // TODO: compare all class variables (if any).
        return (o instanceof AzulPlayerBoard) && super.equals(o);
    }

    @Override
    public int hashCode() {
        // TODO: include all class variables (if any).
        return super.hashCode();
    }
}
