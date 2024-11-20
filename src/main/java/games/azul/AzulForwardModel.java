package games.azul;

import core.AbstractGameState;
import core.StandardForwardModel;
import core.actions.AbstractAction;
import games.azul.components.AzulFactoryBoard;
import games.azul.components.AzulPlayerBoard;
import gametemplate.actions.GTAction;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>The forward model contains all the game rules and logic. It is mainly responsible for declaring rules for:</p>
 * <ol>
 *     <li>Game setup</li>
 *     <li>Actions available to players in a given game state</li>
 *     <li>Game events or rules applied after a player's action</li>
 *     <li>Game end</li>
 * </ol>
 */
public class AzulForwardModel extends StandardForwardModel {

    /**
     * Initializes all variables in the given game state. Performs initial game setup according to game rules, e.g.:
     * <ul>
     *     <li>Sets up decks of cards and shuffles them</li>
     *     <li>Gives player cards</li>
     *     <li>Places tokens on boards</li>
     *     <li>...</li>
     * </ul>
     *
     * @param firstState - the state to be modified to the initial game state.
     */
    @Override
    protected void _setup(AbstractGameState firstState) {
        AzulGameState gs = (AzulGameState) firstState;
        AzulParameters params = (AzulParameters) firstState.getGameParameters();

        gs.playerBoards = new ArrayList<>();
        gs.factoryBoards = new ArrayList<>();

        params.initialise(gs.getNPlayers());

        for(int i=0; i < gs.getNPlayers(); i++){
            AzulPlayerBoard playerBoard = new AzulPlayerBoard();
            playerBoard.initialise(gs);

            gs.playerBoards.add(playerBoard);
        }

        for(int i=0; i < params.nFactories; i++){
            AzulFactoryBoard factoryBoard = new AzulFactoryBoard();
            factoryBoard.initialise(gs);

            gs.factoryBoards.add(factoryBoard);
        }
    }

    /**
     * Calculates the list of currently available actions, possibly depending on the game phase.
     * @return - List of AbstractAction objects.
     */
    @Override
    protected List<AbstractAction> _computeAvailableActions(AbstractGameState gameState) {
        List<AbstractAction> actions = new ArrayList<>();
        // TODO: create action classes for the current player in the given game state and add them to the list. Below just an example that does nothing, remove.
        actions.add(new GTAction());
        return actions;
    }
}
