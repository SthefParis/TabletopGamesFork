package games.azul;

import core.AbstractGameState;
import core.AbstractParameters;
import core.components.Component;
import core.components.GridBoard;
import games.GameType;

import java.util.List;

public class AzulGameState extends AbstractGameState {

    GridBoard<AzulCell> grid;

    /**
     * @param gameParameters - game parameters.
     * @param nPlayers
     */
    public AzulGameState(AbstractParameters gameParameters, int nPlayers) {
        super(gameParameters, nPlayers);
    }


    @Override
    protected GameType _getGameType() {
        return null;
    }

    // Returns a list of all the compoennts o fthe game state
    @Override
    protected List<Component> _getAllComponents() {
        return List.of();
    }

    // Defines a reduced, player-speciffic, copy of your game state
    @Override
    protected AbstractGameState _copy(int playerId) {
        return null;
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
