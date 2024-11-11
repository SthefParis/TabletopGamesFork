package games.azul;

import core.AbstractForwardModel;
import core.AbstractGameState;
import core.actions.AbstractAction;

import java.util.List;

public class AzulForwardModel extends AbstractForwardModel {
    // Performs the initial game setup according to rules, initialising all components and variables in the given game state
    @Override
    protected void _setup(AbstractGameState firstState) {

    }

    // Applyy the given action to the given game state, execute any other non-action-dependent game rules
    @Override
    protected void _next(AbstractGameState currentState, AbstractAction action) {

    }

    // Return a list with all actions available for the current player
    @Override
    protected List<AbstractAction> _computeAvailableActions(AbstractGameState gameState) {
        return List.of();
    }

    // Return a new instance of the Forward Model object with any necessary variables copied
    @Override
    protected AbstractForwardModel _copy() {
        return null;
    }

    @Override
    protected void endPlayerTurn(AbstractGameState state) {

    }
}
