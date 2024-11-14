package games.azul;

import core.AbstractGameState;
import core.AbstractParameters;
import core.components.Component;
import core.components.GridBoard;
import core.interfaces.IGamePhase;
import games.GameType;
import games.azul.AzulTypes.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AzulGameState extends AbstractGameState {

    enum AzulPhase implements IGamePhase {
        FactoryOffer,
        WallTiling,
        PrepNextRnd
    }

    List<GridBoard<AzulMapTile>> playerBoards;
    GridBoard<AzulMapTile> factoryOffers;
    GridBoard<AzulMapTile> centerArea;

    /**
     * @param gameParameters - game parameters.
     * @param nPlayers
     */
    public AzulGameState(AbstractParameters gameParameters, int nPlayers) {
        super(gameParameters, nPlayers);
    }


    @Override
    protected GameType _getGameType() { return GameType.Azul; }

    // Returns a list of all the compoennts o fthe game state
    @Override
    protected List<Component> _getAllComponents() {
        return new ArrayList<Component>() {{
            add(factoryOffers);
            add(centerArea);
            addAll(playerBoards);

        }};
    }

    // Defines a reduced, player-speciffic, copy of your game state
    @Override
    protected AbstractGameState _copy(int playerId) {
        // Create a new AzulGameState instance for the player
        AzulGameState copy = new AzulGameState(gameParameters, getNPlayers());

        // Deep copy each player's board, factoryOffers, and centerArea
        copy.playerBoards = new ArrayList<>();
        for (GridBoard<AzulMapTile> board : playerBoards) {
            copy.playerBoards.add(board.copy());
        }
        copy.factoryOffers = factoryOffers.copy();
        copy.centerArea = centerArea.copy();

        // Set other necessary fields in the copied state
//        copy.setGamePhase(getGamePhase());
//        copy.setTurnOrder(getTurnOrder().copy());
//        copy.setCurrentPlayer(getCurrentPlayer());

        return copy;
    }

    @Override
    protected double _getHeuristicScore(int playerId) {
        return 0;
    }

    @Override
    public double getGameScore(int playerId) {
        return 0;
    }

    @Override
    protected boolean _equals(Object o) {
        return false;
    }

    //Resets any variable of the game state to their state before FM initialisation
    @Override
    protected void reset(){}
}
