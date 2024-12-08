package games.azul.gui;

import core.AbstractGameState;
import core.AbstractPlayer;
import core.Game;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.components.AzulFactoryBoard;
import gui.AbstractGUIManager;
import gui.GamePanel;
import gui.IScreenHighlight;
import players.human.ActionController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

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

    // Settings for display areas
    final static int playerAreaWidth = 500;
    final static int playerAreaHeight = 200;

    // Factory board views
    private List<AzulFactoryBoardView> factoryBoards;
    // List of player board views
    private List<AzulPlayerBoardView> playerBoards;

    // Current active player
    private int activePlayer = -1;
    // Border highlight of active player
    Border highlightActive = BorderFactory.createLineBorder(new Color(220, 169, 11), 3);
    Border[] playerViewBorders;

    public AzulGUI(GamePanel parent, Game game, ActionController ac, Set<Integer> human) {
        super(parent, game, ac, human);
        if (game == null) return;
        AbstractGameState gs = game.getGameState();
        if (gs == null) return;

        activePlayer = gs.getCurrentPlayer();

        // Find required size of window
        int nPlayers = gs.getNPlayers();
        int nHorizAreas = 1 + (nPlayers <= 3 ? 2 : 3);
        double nVertAreas = 5;
        this.width = playerAreaWidth * nHorizAreas;
        this.height = (int) (playerAreaHeight * nVertAreas) + 20;

        AzulGameState ags = (AzulGameState) gs;
        AzulParameters params = (AzulParameters) gs.getGameParameters();

        // Create main game area that will hold all game views
        factoryBoards = new ArrayList<>();
        playerBoards = new ArrayList<>();
        playerViewBorders = new Border[nPlayers];
        JPanel mainGameArea = new JPanel();
        mainGameArea.setLayout(new BorderLayout());

        //Player boards go on the edges
        String[] locations = new String[]{BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST};
        JPanel[] sides = new JPanel[]{new JPanel(), new JPanel(), new JPanel(), new JPanel()};
        int next = 0;
        for (int i = 0; i < nPlayers; i++) {
            AzulPlayerBoardView playerBoard = new AzulPlayerBoardView(ags.getPlayerBoard(i), ags);

            // Get agent name
            String[] split = game.getPlayers().get(0).getClass().toString().split("\\.");
            String agentName = split[split.length - 1];

            // Create border, layouts and keep track of this view
            TitledBorder title = BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Player " + i + " [" + agentName + "]",
                    TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM
            );

            playerViewBorders[i] = title;
            playerBoard.setBorder(title);
            playerBoard.setPreferredSize(new Dimension(playerAreaWidth, playerAreaHeight));
            sides[next].setLayout(new BoxLayout(sides[next], BoxLayout.Y_AXIS));
            sides[next].add(playerBoard);
            next = (next + 1) % (locations.length);
            playerBoards.add(playerBoard);
        }
        for (int i = 0; i < locations.length; i++) {
            mainGameArea.add(sides[i], locations[i]);
        }

        // Factory in the center
        for(int i = 0; i < params.getNFactories(); i++){
            AzulFactoryBoardView factoryBoard = new AzulFactoryBoardView(ags.getFactoryBoard(i), ags);


            factoryBoards.add(factoryBoard);

        }

        JPanel centerArea = new JPanel();
        centerArea.setLayout(new BoxLayout(centerArea, BoxLayout.Y_AXIS));

        for (int i=0; i < params.getNFactories(); i++) {
            centerArea.add(factoryBoards.get(i));
        }
////        factoryBoard = new AzulFactoryBoardView(ags.getFactoryBoards(),ags);
//        centerArea.add(factoryBoard);
////        JPanel jp = new JPanel();
//////        jp.setLayout(new GridBagLayout());
//////        jp.add(centerArea);
        mainGameArea.add(centerArea, BorderLayout.CENTER);

        // Top area will show state information
        JPanel infoPanel = createGameStateInfoPanel("Azul", gs, width, defaultInfoPanelHeight);
        // Bottom area will show actions available
        JComponent actionPanel = createActionPanel(new IScreenHighlight[0], width, defaultActionPanelHeight, false, true, null, null, null);

        // Add all views to frame
        parent.setLayout(new BorderLayout());
        parent.add(mainGameArea, BorderLayout.CENTER);
        parent.add(infoPanel, BorderLayout.NORTH);
        parent.add(actionPanel, BorderLayout.SOUTH);

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
     * @param gs - current game state to be used in updating visuals.
     */
    @Override
    protected void _update(AbstractPlayer player, AbstractGameState gs) {
        if (gs == null) return;

        if (gs.getCurrentPlayer() != activePlayer) {
            activePlayer = gs.getCurrentPlayer();
        }

        // Update boards and visibility
        AzulGameState ags = (AzulGameState) gs;
        for (int i=0; i< gs.getNPlayers(); i++) {
            playerBoards.get(i).updateComponent(ags.getPlayerBoard(i));
            if (i == gs.getCurrentPlayer() && gs.getCoreGameParameters().alwaysDisplayCurrentPlayer
                    || humanPlayerId.contains(i)
                    || gs.getCoreGameParameters().alwaysDisplayFullObservable) {
//                playerBoards.get(i).setFont(true);
                playerBoards.get(i).setFocusable(true);
            }

            // Highlight active player
            if (i == gs.getCurrentPlayer()) {
                Border compound = BorderFactory.createCompoundBorder(
                        highlightActive, playerViewBorders[i]);
                playerBoards.get(i).setBorder(compound);
            }
            else{
                playerBoards.get(i).setBorder(playerViewBorders[i]);
            }
//            factoryBoard.updateComponent(ags.getFactoryBoards());
//            factoryBoard.setFocusable(true);
        }

//        if (gameState instanceof AzulGameState) {
//            AzulGameState gs = (AzulGameState) gs;
//
//            // Update the board view with the latest factory boards
////            GridBoard<AzulFactoryBoard> factoryBoards = gs.getFactoryBoard();
////            factoryBoardView.repaint();
////            playerBoardView.repaint();
//        }
//        if (gs == null) return;
//
//        AzulGameState ags = (AzulGameState) gs;

//        playerBoardView.repaint();
//        for (int i=0; i < gs.getNPlayers(); i++) {
//            playerBoards.get(i).updateComponent(ags.getPlayerBoard(i));
//        }
//        factoryBoard.repaint();

    }
}