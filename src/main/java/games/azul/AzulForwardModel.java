package games.azul;

import core.AbstractGameState;
import core.CoreConstants;
import core.StandardForwardModel;
import core.actions.AbstractAction;
import core.actions.ActionSpace;
import core.components.GridBoard;
import games.azul.actions.PickUpTilesAction;
import games.azul.components.AzulFactoryBoard;
import games.azul.components.AzulPlayerBoard;
import games.azul.gui.AzulFactoryBoardManager;
import gametemplate.actions.GTAction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static games.azul.AzulGameState.AzulPhase.*;

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

        // Initialise Player boards
        gs.playerBoards = new ArrayList<>();
        params.initialise(gs.getNPlayers());

        for(int i=0; i < gs.getNPlayers(); i++){
            AzulPlayerBoard playerBoard = new AzulPlayerBoard();
            playerBoard.initialise(gs);
            playerBoard.setOwnerId(i);
            gs.playerBoards.add(playerBoard);
        }

        // Choose a random player to be first
        int startingPlayer = (int) (Math.random() * gs.getNPlayers());

        // Initialise and fill factory boards
        gs.factoryBoards = new ArrayList<>();

        for (int y=0; y< params.nFactories; y++) {
            AzulFactoryBoard factoryBoard = new AzulFactoryBoard();
            factoryBoard.initialise(gs);
            factoryBoard.refill();
            gs.factoryBoards.add(factoryBoard);
        }

        // First state is Factory Offers
        gs.setGamePhase(FactoryOffer);
        for (int i=0; i < gs.getNPlayers(); i++) {

        }

    }


    /**
     * Calculates the list of currently available actions, possibly depending on the game phase.
     * @return - List of AbstractAction objects.
     */
    @Override
    protected List<AbstractAction> _computeAvailableActions(AbstractGameState gameState) {
        return _computeAvailableActions(gameState, ActionSpace.Default);
    }

    @Override
    protected List<AbstractAction> _computeAvailableActions(AbstractGameState gameState, ActionSpace space) {
        AzulGameState ags = (AzulGameState) gameState;
        AzulParameters params = (AzulParameters) gameState.getGameParameters();
        ArrayList<AbstractAction> actions;

        int player = ags.getCurrentPlayer();

        actions = playerActions(ags, player);



        return actions;
    }


    private ArrayList<AbstractAction> playerActions(AzulGameState ags, int playerId){
        ArrayList<AbstractAction> actions = new ArrayList<>();
        List<AzulFactoryBoard> factoryBoards = ags.getFactoryBoards();

        actions.add(new PickUpTilesAction(1, false));

        return actions;
    }
}
