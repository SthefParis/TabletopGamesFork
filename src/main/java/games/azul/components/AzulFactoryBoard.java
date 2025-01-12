package games.azul.components;

import core.CoreConstants;
import core.components.Component;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.tiles.AzulTile;
import org.apache.poi.ss.formula.functions.T;

import java.util.*;
import java.util.stream.Collectors;

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
//        this.factoryBoard = new int[5][2];
//        int count = 0;

        this.factoryBoard = new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {4,0}};
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

    public void refill() {
//        public void refill() {
//            AzulParameters params = new AzulParameters();
//            HashMap<AzulTile, Integer> tileCounts = params.tileCounts;
//
//            List<AzulTile> selectedTiles = new ArrayList<>();
//            Random random = new Random();
//
//            // Reset factoryBoard
//            this.factoryBoard = new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}};
//
//            while (selectedTiles.size() < 4) {
//                // Filter and collect available tiles
//                List<AzulTile> availableTiles = tileCounts.entrySet().stream()
//                        .filter(entry -> entry.getValue() > 0)
//                        .map(Map.Entry::getKey)
//                        .collect(Collectors.toList());
//
//                if (availableTiles.isEmpty()) break;
//
//                // Pick a random tile
//                AzulTile randomTile = availableTiles.get(random.nextInt(availableTiles.size()));
//                selectedTiles.add(randomTile);
//
//                // Update tileCounts
//                int currentCount = tileCounts.get(randomTile);
//                if (currentCount > 0) {
//                    tileCounts.put(randomTile, currentCount - 1);
//                }
//            }
//
//            // Populate factoryBoard
//            for (AzulTile tile : selectedTiles) {
//                switch (tile) {
//                    case White:
//                        factoryBoard[0][1]++;
//                        break;
//                    case Black:
//                        factoryBoard[1][1]++;
//                        break;
//                    case Red:
//                        factoryBoard[2][1]++;
//                        break;
//                    case Orange:
//                        factoryBoard[3][1]++;
//                        break;
//                    case Blue:
//                        factoryBoard[4][1]++;
//                        break;
//                }
//            }
//
//            System.out.println("Factory board updated: " + Arrays.deepToString(factoryBoard));
//            System.out.println("Tile counts updated: " + tileCounts);
        AzulParameters params = new AzulParameters();
        HashMap<AzulTile, Integer> tileCounts = params.tileCounts;

        // List to store selected tiles
        List<AzulTile> selectedTiles = new ArrayList<>();
        List<AzulTile> tilesPicked = new ArrayList<>();

        // Random generator
        Random random = new Random();

        // Reset factoryBoard
        this.factoryBoard = new int[][]{{0,0}, {1,0}, {2,0}, {3,0}, {4,0}};

        while (selectedTiles.size() < 4) {
            // Filter and collect available tiles (those with count > 0)
            List<AzulTile> availableTiles = tileCounts.entrySet().stream()
                    .filter(entry -> entry.getValue() > 0) // Keep entries with values > 0
                    .map(Map.Entry::getKey) // Extract keys
                    .collect(Collectors.toList()); // Collect into a list

            // For now, break if there are no more tiles
            if (availableTiles.isEmpty()) {
                break;
            }

            // Pick a random tile from the available ones
            AzulTile randomTile = availableTiles.get(random.nextInt(availableTiles.size()));

            // Add the tile to the selected list
            selectedTiles.add(randomTile);

            // Decrease its count in the tileCounts map
            tileCounts.put(randomTile, tileCounts.get(randomTile) - 1);
        }

        System.out.println("Factory: " + selectedTiles);

        for (int i = 0; i < selectedTiles.size(); i++) {
            int count = 0;
            for (int j = i+1; j < selectedTiles.size(); j++) {
                if (selectedTiles.get(i).equals(selectedTiles.get(j))) {
                    if (tilesPicked.contains(selectedTiles.get(i))) return;
                    if (count == 0) count++;
                    count++;
                }
            }

            // If selected tile is in tiles picked, value in factoryBoard should not change
            if (tilesPicked.contains(selectedTiles.get(i))) continue;
            tilesPicked.add(selectedTiles.get(i));

            if (count == 0) count++;

            AzulTile selected = selectedTiles.get(i);

            switch (selected) {
                case White: this.factoryBoard[0][1] = count; break;
                case Black: this.factoryBoard[1][1] = count; break;
                case Red: this.factoryBoard[2][1] = count; break;
                case Orange: this.factoryBoard[3][1] = count; break;
                case Blue: this.factoryBoard[4][1] = count; break;
            }
        }
    }

    public int getHeight(){
        return 1;
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