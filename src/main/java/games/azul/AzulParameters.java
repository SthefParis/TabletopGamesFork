package games.azul;

import core.AbstractGameState;
import core.AbstractParameters;
import evaluation.optimisation.TunableParameters;
import games.azul.tiles.AzulTile;

import java.util.HashMap;

/**
 * <p>This class should hold a series of variables representing game parameters (e.g. number of cards dealt to players,
 * maximum number of rounds in the game etc.). These parameters should be used everywhere in the code instead of
 * local variables or hard-coded numbers, by accessing these parameters from the game state via {@link AbstractGameState#getGameParameters()}.</p>
 *
 * <p>It should then implement appropriate {@link #_copy()}, {@link #_equals(Object)} and {@link #hashCode()} functions.</p>
 *
 * <p>The class can optionally extend from {@link TunableParameters} instead, which allows to use
 * automatic game parameter optimisation tools in the framework.</p>
 */
public class AzulParameters extends AbstractParameters {
    // Board and factory parameters
    int playerBoardSize = 5;
    int factoryBoardHeight = 1;
    int factoryBoardWidth;
    int nTilesPerFactory = 4;
    int nFactories;
    int maxRounds = 5;
    int maxPoints = 240;
    int nTiles = 5;

    // Tile counts by colour
    HashMap<AzulTile, Integer> tileCounts = new HashMap<AzulTile, Integer>() {{
        put(AzulTile.White, 20);
        put(AzulTile.Orange, 20);
        put(AzulTile.Red, 20);
        put(AzulTile.Black, 20);
        put(AzulTile.Blue, 20);
    }};

    // Scoring parameters
    int rowBonusPoints = 2;
    int columnBonusPoints = 7;
    int colorSetBonusPoints = 10;
    int adjacencyBasePoints = 1;

    // Penalty parameters
    int[] floorPenalties = {-1,-1,-2,-2,-2,-3,-3};

//    /**
//     * Constructor initializes the parameters based on the number of players.
//     * @param nPlayers Number of players in the game.
//     */
//    public AzulParameters() {}

    public int getBoardSize() { return playerBoardSize; }
    public int getNTilesPerFactory(){ return nTilesPerFactory; }
    public int getNFactories() { return nFactories; }
    public int getMaxRounds() { return maxRounds; }
    public int getRowBonusPoints() { return rowBonusPoints; }
    public int getColumnBonusPoints() { return columnBonusPoints; }
    public int getColorSetBonusPoints() { return colorSetBonusPoints; }
    public int getAdjacencyBasePoints() { return adjacencyBasePoints; }
    public int[] getFloorPenalties() { return floorPenalties; }
    public int getNTiles() { return nTiles; }

    public void initialise(int nPlayers){
        this.nFactories = ((nPlayers)*2) + 1;
        this.factoryBoardWidth = this.nFactories * this.nTilesPerFactory;
    }

    @Override
    protected AbstractParameters _copy() {
        AzulParameters copy = new AzulParameters();
        copy.playerBoardSize = this.playerBoardSize;
        copy.nTilesPerFactory = this.nTilesPerFactory;
        copy.maxRounds = this.maxRounds;
        copy.maxPoints = this.maxPoints;
        copy.rowBonusPoints = this.rowBonusPoints;
        copy.columnBonusPoints = this.columnBonusPoints;
        copy.colorSetBonusPoints = this.colorSetBonusPoints;
        copy.adjacencyBasePoints = this.adjacencyBasePoints;
        copy.floorPenalties = this.floorPenalties.clone();
        copy.tileCounts = new HashMap<>(this.tileCounts); // Deep copy
        return copy;
    }

    @Override
    protected boolean _equals(Object o) {
        return false;
//        if (this == o) return true;
//        if (!(o instanceof AzulParameters)) return false;
//        AzulParameters that = (AzulParameters) o;
//        return this.boardSize == that.boardSize &&
//                this.nTilesPerFactory == that.nTilesPerFactory &&
//                this.nFactories == that.nFactories &&
//                this.maxRounds == that.maxRounds &&
//                this.maxPoints == that.maxPoints &&
//                this.rowBonusPoints == that.rowBonusPoints &&
//                this.columnBonusPoints == that.columnBonusPoints &&
//                this.colorSetBonusPoints == that.colorSetBonusPoints &&
//                this.adjacencyBasePoints == that.adjacencyBasePoints &&
//                java.util.Arrays.equals(this.floorPenalties, that.floorPenalties) &&
//                this.tileCounts.equals(that.tileCounts);
    }

//    @Override
//    public int hashCode() {
//        int result = Integer.hashCode(boardSize);
//        result = 31 * result + Integer.hashCode(nTilesPerFactory);
//        result = 31 * result + Integer.hashCode(nFactories);
//        result = 31 * result + Integer.hashCode(maxRounds);
//        result = 31 * result + Integer.hashCode(maxPoints);
//        result = 31 * result + Integer.hashCode(rowBonusPoints);
//        result = 31 * result + Integer.hashCode(columnBonusPoints);
//        result = 31 * result + Integer.hashCode(colorSetBonusPoints);
//        result = 31 * result + Integer.hashCode(adjacencyBasePoints);
//        result = 31 * result + java.util.Arrays.hashCode(floorPenalties);
//        result = 31 * result + tileCounts.hashCode();
//        return result;
//    }
}
