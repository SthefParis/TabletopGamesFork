package games.azul.components;

import core.CoreConstants;
import core.components.Component;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.tiles.AzulTile;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;

public class AzulFactoryBoard extends Component {
    //public HashMap<AzulTile, Integer> factoryBoard;
    //    int defaultTileCount = 0;
    public int[][] factoryBoard;

    public AzulFactoryBoard() {
        super(CoreConstants.ComponentType.BOARD, "AzulFactoryBoard");
    }

    public AzulFactoryBoard(int componentID) {
        super(CoreConstants.ComponentType.BOARD, "AzulFactoryBoard", componentID);
    }

    public void initialise(AzulGameState gs){
        this.factoryBoard = new int[5][2];
        int count = 0;

        this.factoryBoard = new int[][]{{0,2}, {1,2}, {2,0},{3,0},{4,0}};
//        for (int i=0; i<factoryBoard.length; i++){
//            this.factoryBoard = new int[i][defaultTileCount];
//        }

//        for (AzulTile tile : AzulTile.values()){
//            if (count < 1){
//                factoryBoard.put(tile, 1);
//                count+=1;
//            }
//            else{
//                factoryBoard.put(tile, defaultTileCount);
//            }
//
//            System.out.println("Tile: " + tile + " Value: " + factoryBoard.values());
//        }
//        int nTiles = ((AzulParameters) gs.getGameParameters()).getNTiles();
//        //for (int i=0; i<nTiles; i++){
//            this.factoryBoard = new HashMap<>();
//        //}
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