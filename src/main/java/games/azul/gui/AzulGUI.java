package games.azul.gui;

import core.AbstractGameState;
import core.AbstractPlayer;
import core.Game;
import core.components.GridBoard;
import games.azul.AzulGameState;
import games.azul.components.AzulFactoryBoard;
import games.azul.components.AzulPlayerBoard;
import gui.AbstractGUIManager;
import gui.GamePanel;
import players.human.ActionController;

import java.awt.*;
import java.util.Set;

/**
 * <p>This class allows the visualisation of the game. The game components (accessible through {@link Game#getGameState()}
 * should be added into {@link javax.swing.JComponent} subclasses (e.g. {@link javax.swing.JLabel},
 * {@link javax.swing.JPanel}, {@link javax.swing.JScrollPane}; or custom subclasses such as those in {@link gui} package).
 * These JComponents should then be added to the <code>`parent`</code> object received in the class constructor.</p>
 *
 * <p>An appropriate layout should be set for the parent GamePanel as well, e.g. {@link javax.swing.BoxLayout} or
 * {@link BorderLayout} or {@link GridBagLayout}.</p>
 *
 * <p>Check the super class for methods that can be overwritten for a more custom look, or
 * {@link games.terraformingmars.gui.TMGUI} for an advanced game visualisation example.</p>
 *
 * <p>A simple implementation example can be found in {@link games.tictactoe.gui.TicTacToeGUIManager}.</p>
 */
public class AzulGUI extends AbstractGUIManager {

//    private AzulFactoryBoardView factoryBoardView;
    private AzulPlayerBoardView playerBoardView;

    public AzulGUI(GamePanel parent, Game game, ActionController ac, Set<Integer> human) {
        super(parent, game, ac, human);
        if (game == null) return;

        AzulGameState gs = (AzulGameState) game.getGameState();

//        // Initialize Azul factory board view
//        GridBoard<AzulFactoryBoard> factoryBoards = gs.getFactoryBoard();
//        factoryBoardView = new AzulFactoryBoardView(gs.getFactoryBoard(), gs);
////        factoryBoardView.setPreferredSize(new Dimension(parent.getWidth(), 200));

        // Initialize Azul player board view
        GridBoard<AzulPlayerBoard> playerBoards = gs.getPlayerBoard();
        playerBoardView = new AzulPlayerBoardView(gs.getPlayerBoard(), gs);

//        JPanel infoPanel = createGameStateInfoPanel("Azul", gs, width, defaultInfoPanelHeight);
//        JComponent actionPanel = createActionPanel(new IScreenHighlight[]{boardView},
//                width, defaultInfoPanelHeight, true);

        // Set up GUI Layout
        parent.setLayout(new BorderLayout());
//        parent.add(factoryBoardView, BorderLayout.CENTER);// Add board view to center of the panel
        parent.add(playerBoardView, BorderLayout.CENTER);
//        parent.add(infoPanel, BorderLayout.NORTH);
//        parent.add(actionPanel, BorderLayout.SOUTH);
        parent.setPreferredSize(new Dimension(width, height + defaultActionPanelHeight + defaultInfoPanelHeight + defaultCardHeight + 20));
        parent.revalidate();
        parent.setVisible(true);
        parent.repaint();
    }

    /**
     * Defines how many action button objects will be created and cached for usage if needed. Less is better, but
     * should not be smaller than the number of actions available to players in any game state.
     *
     * @return maximum size of the action space (maximum actions available to a player for any decision point in the game)
     */
    @Override
    public int getMaxActionSpace() {
        // TODO
        return 10;
    }

    /**
     * Updates all GUI elements given current game state and player that is currently acting.
     *
     * @param player    - current player acting.
     * @param gameState - current game state to be used in updating visuals.
     */
    @Override
    protected void _update(AbstractPlayer player, AbstractGameState gameState) {
        if (gameState instanceof AzulGameState) {
            AzulGameState gs = (AzulGameState) gameState;

            // Update the board view with the latest factory boards
            GridBoard<AzulFactoryBoard> factoryBoards = gs.getFactoryBoard();
//            factoryBoardView.repaint();
            playerBoardView.repaint();
        }
    }
}
