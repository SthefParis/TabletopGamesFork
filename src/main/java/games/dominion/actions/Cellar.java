package games.dominion.actions;

import core.actions.AbstractAction;
import core.actions.DoNothing;
import games.dominion.*;
import games.dominion.cards.CardType;
import games.dominion.cards.DominionCard;

import java.util.*;

import static games.dominion.DominionConstants.*;
import static java.util.stream.Collectors.*;

public class Cellar extends ExtendedDominionAction {
    public Cellar(int playerId) {
        super(CardType.CELLAR, playerId);
    }

    int cardsDiscarded = 0;
    boolean executed = false;

    @Override
    boolean _execute(DominionGameState state) {
        state.changeActions(1);
        state.setActionInProgress(this);
        return true;
    }

    /**
     * Create a copy of this action, with all of its variables.
     * NO REFERENCES TO COMPONENTS TO BE KEPT IN ACTIONS, PRIMITIVE TYPES ONLY.
     *
     * @return - new AbstractAction object with the same properties.
     */
    @Override
    public AbstractAction copy() {
        Cellar retValue = new Cellar(player);
        retValue.cardsDiscarded = cardsDiscarded;
        retValue.executed = executed;
        return retValue;
    }

    @Override
    public List<AbstractAction> followOnActions(DominionGameState state) {
        // we can discard any card in hand, so create a DiscardCard action for each
        Set<DominionCard> uniqueCardsInHand = state.getDeck(DeckType.HAND, player).stream().collect(toSet());
        List<AbstractAction> discardActions = uniqueCardsInHand.stream()
                .map(card -> new DiscardCard(card.cardType(), player))
                .collect(toList());
        // and then we can always choose to stop discarding
        discardActions.add(new DoNothing());
        return discardActions;
    }

    @Override
    public void registerActionTaken(DominionGameState state, AbstractAction action) {
        // if the action is DoNothing, then we have stopped
        // else we continue discarding
        if (action instanceof DoNothing) {
            for (int i = 0; i < cardsDiscarded; i++) {
                state.drawCard(player);
            }
            cardsDiscarded = 0;
            executed = true;
        }
        if (action instanceof DiscardCard)
            cardsDiscarded++;
    }

    @Override
    public boolean executionComplete() {
        return executed;
    }
}
