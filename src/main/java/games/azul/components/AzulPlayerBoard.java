package games.azul.components;

import core.CoreConstants;
import core.components.Component;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.tiles.AzulTile;

/**
 * <p>Components represent a game piece, or encompass some unit of game information (e.g. cards, tokens, score counters, boards, dice etc.)</p>
 * <p>Components in the game can (and should, if applicable) extend one of the other components, in package {@link core.components}.
 * Or, the game may simply reuse one of the existing core components.</p>
 * <p>They need to extend at a minimum the {@link Component} super class and implement the {@link Component#copy()} method.</p>
 * <p>They also need to include {@link Object#equals(Object)} and {@link Object#hashCode()} methods.</p>
 * <p>They <b>may</b> keep references to other components or actions (but these should be deep-copied in the copy() method, watch out for infinite loops!).</p>
 */
public class AzulPlayerBoard extends Component {
    AzulTile[][] playerBoard;
    AzulTile[][] playerTempBoard;

    public AzulPlayerBoard() {
        super(CoreConstants.ComponentType.BOARD, "AzulPlayerBoard");
    }

    protected AzulPlayerBoard(int componentID) {
        super(CoreConstants.ComponentType.BOARD, "AzulPlayerBoard", componentID);
    }

    public void initialise(AzulGameState gs){
        int boardSize = ((AzulParameters) gs.getGameParameters()).getBoardSize();
        this.playerBoard = new AzulTile[boardSize][boardSize];
        this.playerTempBoard = new AzulTile[boardSize][];

        for(int i = 0; i < boardSize; i++){
            playerTempBoard[i] = new AzulTile[i+1];
        }
    }

    /**
     * @return Make sure to return an exact <b>deep</b> copy of the object, including all of its variables.
     * Make sure the return type is this class (e.g. GTComponent) and NOT the super class Component.
     * <p>
     * <b>IMPORTANT</b>: This should have the same componentID
     * (using the protected constructor on the Component super class which takes this as an argument).
     * </p>
     * <p>The function should also call the {@link Component#copyComponentTo(Component)} method, passing in as an
     * argument the new copy you've made.</p>
     * <p>If all variables in this class are final or effectively final, then you can just return <code>`this`</code>.</p>
     */
    @Override
    public AzulPlayerBoard copy() {
        AzulPlayerBoard copy = new AzulPlayerBoard(componentID);
        // TODO: copy here all non-final class variables.
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
