package games.azul.gui;

import core.components.Component;
import core.components.GridBoard;
import core.components.Token;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.components.AzulFactoryBoard;
import games.azul.tiles.AzulTile;
import gui.IScreenHighlight;
import gui.views.ComponentView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static gui.GUI.defaultItemSize;

public class AzulBoardView extends ComponentView implements IScreenHighlight {

    AzulGameState gs;
//    HashMap<Rectangle, Color> factoryTileColors;
//    HashMap<Rectangle, String> rects;
    ArrayList<Rectangle> highlight;

    public AzulBoardView(AzulGUI gui, AzulGameState gs) {
        super(gs.getBoard(), 0, 0);
        this.gs = gs;
//        this.factoryTileColors = new HashMap<>();
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
        int startX = 20;
        int startY = getHeight() / 4;
        int tileSize = 50;
        int tileSpacing = 50;

        int nFactories = ((AzulParameters) gs.getGameParameters()).getNFactories();
        int nTilesPerFactories = ((AzulParameters) gs.getGameParameters()).getNTilesPerFactory();

        int totalCells = nFactories * nTilesPerFactories;

        for (int x = 0; x < nFactories; x++) {
            for (int i = 0; i < nTilesPerFactories; i++) {
                drawCell(g2, startX, startY, x, factoryBoards);
                startX += tileSpacing;
            }
            startX += 10;
        }
        //}

//        for (AzulFactoryBoard factoryBoard : factoryBoards) {
//            int num = 0;
//            System.out.println("Drawing factory " + num);
//            // Iterate over the tiles in the current factory board
//            for (var entry : factoryBoard.factoryBoard.entrySet() ){
//                AzulTile tile = entry.getKey(); // Name of tile
//                int count = entry.getValue(); // Number of tiles in factory
//
//                for (int i=0; i < count; i++){
//                    // Draw a placeholder rectangle for the tile
//                    g2.setColor(Color.LIGHT_GRAY);
//                    g2.fillRect(startX, startY - tileSize / 2, tileSize, tileSize);
//
//                    // Draw the tile's color name in the center of the rectangle
//                    g2.setColor(Color.BLACK);
//                    String colorName = tile.name();
//                    FontMetrics fm = g2.getFontMetrics();
//                    int textX = startX + (tileSize - fm.stringWidth(colorName)) / 2;
//                    int textY = startY + (fm.getAscent() - fm.getDescent()) / 2;
//                    g2.drawString(colorName, textX, textY);
//
//                    // Move to the next position
//                    startX += tileSize + tileSpacing;
//                }
//            }
//
//            // Move to the next factory (add spacing between factories)
//            startX += 3 * tileSpacing; // Adjust for space between factories
//        }
    }

    private void drawCell(Graphics2D g, int x, int y, int factoryID, List<AzulFactoryBoard> factoryBoards) {
        AzulTile key;
        int value;

        // Paint cell background
        g.setColor(Color.lightGray);
        g.fillRect(x, y, defaultItemSize, defaultItemSize);
        g.setColor(Color.black);
        g.drawRect(x, y, defaultItemSize, defaultItemSize);

        // Set the font and color for the text
        g.setColor(Color.BLACK);
        Font originalFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        for (var entry : factoryBoards.get(factoryID).factoryBoard.entrySet()){
            if (entry.getValue() != 0){
                key = entry.getKey();

                String color = getTileColor(key);
                System.out.println(color);
                g.drawString(color, x + defaultItemSize/4, y + defaultItemSize/2);


//                if (key == AzulTile.Red){
//                    g.drawString("Red", x + defaultItemSize,y + defaultItemSize );
//                }
            }
            else{
                g.drawString("Empty", x + defaultItemSize/4, y + defaultItemSize/2);
            }
        }
        g.setFont(originalFont);
    }

    // This needs to be tested!!!
    private String getTileColor(AzulTile tile){
        switch (tile) {
            case Blue:
                return "Blue";
            case Red:
                return "Red";
            case White:
                return "White";
            case Orange:
                return "Orange";
            case Black:
                return "Black";
            default:
                return "Empty";
        }
    }

    @Override
    public void clearHighlights() {

    }
}
