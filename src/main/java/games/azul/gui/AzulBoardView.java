package games.azul.gui;

import core.components.Component;
import games.azul.AzulGameState;
import games.azul.components.AzulFactoryBoard;
import games.azul.tiles.AzulTile;
import gui.IScreenHighlight;
import gui.views.ComponentView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AzulBoardView extends ComponentView implements IScreenHighlight {

    AzulGameState gs;
    HashMap<Rectangle, Color> factoryTileColors;
    HashMap<Rectangle, String> rects;
    ArrayList<Rectangle> highlight;

    int offsetX = 10, offsetY = 10;

    public AzulBoardView(Component c, int width, int height) {
        super(c, width, height);
        this.gs = gs;
        this.factoryTileColors = new HashMap<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (gs == null || gs.getFactoryBoards() == null) return;

        // Draw factory boards
        List<AzulFactoryBoard> factoryBoards = gs.getFactoryBoards();
        drawFactoryBoard(g2, factoryBoards);
    }

    /**
     * Draws the factory boards.
     *
     * @param g2             Graphics2D object for rendering
     * @param factoryBoards  List of AzulFactoryBoard objects
     */
    public void drawFactoryBoard(Graphics2D g2, List<AzulFactoryBoard> factoryBoards){
        int startX = offsetX;
        int startY = getHeight() / 2;
        int tileSize = 50;
        int tileSpacing = 5;

        for (AzulFactoryBoard factoryBoard : factoryBoards) {
            // Iterate over the tiles in the current factory board
            for (var entry : factoryBoard.factoryBoard.entrySet() ){
                AzulTile tile = entry.getKey(); // Name of tile
                int count = entry.getValue(); // Number of tiles in factory

                for (int i=0; i < count; i++){
                    // Draw a placeholder rectangle for the tile
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(startX, startY - tileSize / 2, tileSize, tileSize);

                    // Draw the tile's color name in the center of the rectangle
                    g2.setColor(Color.BLACK);
                    String colorName = tile.name();
                    FontMetrics fm = g2.getFontMetrics();
                    int textX = startX + (tileSize - fm.stringWidth(colorName)) / 2;
                    int textY = startY + (fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(colorName, textX, textY);

                    // Move to the next position
                    startX += tileSize + tileSpacing;
                }
            }

            // Move to the next factory (add spacing between factories)
            startX += 3 * tileSpacing; // Adjust for space between factories
        }
    }

    @Override
    public void clearHighlights() {

    }
}
