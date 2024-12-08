package games.azul.gui;

import core.components.GridBoard;
import games.azul.AzulGameState;
import games.azul.components.AzulFactoryBoard;
import gui.IScreenHighlight;
import gui.views.ComponentView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static gui.GUI.defaultItemSize;

public class AzulFactoryBoardView extends ComponentView implements IScreenHighlight {

    AzulGameState gs;

    Rectangle[] rects; // Use for highlights
    ArrayList<Rectangle> highlight;

    AzulFactoryBoard azulFactory;

    int offsetX = 10;
    int offsetY = 10;

    public AzulFactoryBoardView(AzulFactoryBoard azulFactory, AzulGameState gs) {
        super(null, 0,0);
        this.gs = gs;

        rects = new Rectangle[azulFactory.factoryBoard.length];
        highlight = new ArrayList<>();
        this.azulFactory = azulFactory;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Left click, highlight cell
                    for (Rectangle r : rects) {
                        if (r != null && r.contains(e.getPoint())) {
                            highlight.clear();
                            highlight.add(r);
                            break;
                        }
                    }
                } else {
                    highlight.clear(); // Remove highlight
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g1){
        Graphics2D g = (Graphics2D) g1;

        drawFactoryBoard(g, this.azulFactory, offsetX, offsetY);

        if (highlight.size() > 0){
            g.setColor(Color.green);
            Stroke s = g.getStroke();
            g.setStroke(new BasicStroke(3));

            Rectangle r = highlight.get(0);
            g.drawRect(r.x, r.y, r.width, r.height);
            g.setStroke(s);
        }
    }

    private void drawFactoryBoard(Graphics2D g, AzulFactoryBoard azulFactory, int x, int y) {
//        int width = gridBoard.getWidth() * defaultItemSize;
//        int height = gridBoard.getHeight() * defaultItemSize;
        System.out.println("Drawing factory board");
        int numRows = 1;
        int numColumns = 4;
        int[] onlyTiles;
//        System.out.println("Before for loop");

        for (int i=0; i < numRows; i++) {
            for (int j=0; j < numColumns; j++){
                int index = i * numColumns + j; // Checks that index is within list bound
//                System.out.println("after for loop");
//                System.out.println("index: " + index);
//                System.out.println("size: " + azulFactoryList.size());
                if (index < azulFactory.factoryBoard.length) {
//                    System.out.println("Should be calling draw");
                    int xC = x + j * defaultItemSize/2; // Calculates x-coordinate
                    int yC = y + i * defaultItemSize/2; // Calculates y-coordinate
                    onlyTiles = getTiles(azulFactory.factoryBoard);

                    drawCell(g, onlyTiles, j, xC, yC);

                    if (rects[index] == null) {
                        rects[index] = new Rectangle(xC, yC, defaultItemSize/2, defaultItemSize/2);

                    }
                }
            }
        }
        // Draw cells
//        for (int i=0; i < azulFactoryList.getHeight(); i++){
//            for(int j=0; j < azulFactoryList.getWidth(); j++){
//                int xC = x + j * defaultItemSize;
//                int yC = y + i * defaultItemSize;
//                drawCell(g, azulFactoryList.getElement(j, i), xC, yC);
//
//                // Save rect where cell is drawn
//                int idx = i * azulFactoryList.getWidth() + j;
//                if (rects[idx] == null) {
//                    rects[idx] = new Rectangle(xC, yC, defaultItemSize, defaultItemSize);
//                }
//            }
//        }
    }

    private int[] getTiles(int[][] factory){
        List<Integer> tileList = new ArrayList<>();
//        List<int> tile

//        for (int[] ints : factory) {
//            if (ints[1] > 0) {
//                if (ints[1] > 1) {
//                    for (int j = 0; j < ints[1]; j++) {
//                        tileList.add(ints[0]);
//                    }
//                } else {
//                    tileList.add(ints[0]);
//                }
//
//            }
//        }

        for (int i = 0; i < factory.length; i++) {
            if (factory[i][1] > 0) {
                if (factory[i][1] > 1) {
                    for (int j = 0; j < factory[i][1]; j++) {
                        tileList.add(factory[i][0]);
                    }
                }
                else{
                    tileList.add(factory[i][0]);
                }

            }
        }

        while (tileList.size() < 4) {
            tileList.add(-1);
        }

        int[] tiles = new int[4];
        for (int i = 0; i < tileList.size(); i++) {
            tiles[i] = tileList.get(i);
        }

//        for (int i = 0; i < tiles.length; i++) {
//            if (tiles[i] == null){
//                tiles[i] = -1;
//            }
//        }

//        for(int i = 0; i < factory.length; i++){
//            if (factory[i][1] != 0){
//                tiles[i] = factory[i];
//            }
//        }
//
//        for(int j = 0; j < tiles.length; j++){
//            if (tiles[j][1] == 0){
//                tiles[j][1] = -1;
//            }
//        }

        return tiles;
    }

    private void drawCell(Graphics2D g, int[] tiles, int cellNum, int x, int y){
        // Paint cell background
//        System.out.println("Drawing cell");
        g.setColor(Color.lightGray);
        g.fillRect(x, y, defaultItemSize/2, defaultItemSize/2);
        g.setColor(Color.black);
        g.drawRect(x, y, defaultItemSize/2, defaultItemSize/2);

        // Set the font and color for the text
        g.setColor(Color.BLACK);
        Font originalFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 10));

        int textX = x + (defaultItemSize/4) -10;
        int textY = y + (defaultItemSize/4) +5;

        String colour = "";

        // Paint element in cell
        if (tiles[cellNum] != -1) {
//            Font f = g.getFont();
//            g.setFont(new Font(f.getName(), Font.BOLD, defaultItemSize * 3 / 2));
//            g.drawString(factoryBoard.toString(), x + defaultItemSize / 16, y + defaultItemSize - defaultItemSize / 16);
//            g.setFont(f);

            switch (tiles[cellNum]) {
                case 0: colour = "white"; break;
                case 1: colour = "black"; break;
                case 2: colour = "red"; break;
                case 3: colour = "orange"; break;
                case 4: colour = "blue"; break;
            }

            g.drawString(colour, textX, textY);
        }
        else{
            g.drawString("Empty", textX, textY);
        }
    }

    public ArrayList<Rectangle> getHighlight() { return highlight; }

    @Override
    public void clearHighlights() { highlight.clear(); }

}
//    AzulGameState gs;
////    HashMap<Rectangle, Color> factoryTileColors;
////    HashMap<Rectangle, String> rects;
//    ArrayList<Rectangle> highlight;
//
//    public AzulFactoryBoardView(AzulGUI gui, AzulGameState gs) {
//        super(gs.getBoard(), 0, 0);
//        this.gs = gs;
////        this.factoryTileColors = new HashMap<>();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        if (gs == null || gs.getFactoryBoards() == null) return;
//
//        // Draw factory boards
//        List<AzulFactoryBoard> factoryBoards = gs.getFactoryBoards();
//        drawFactoryBoard(g2, factoryBoards);
//    }
//
//    // MAKE SURE THIS WORKS FOR TILES > 1!!!
//    /**
//     * Draws the factory boards.
//     *
//     * @param g2             Graphics2D object for rendering
//     * @param factoryBoards  List of AzulFactoryBoard objects
//     */
//    public void drawFactoryBoard(Graphics2D g2, List<AzulFactoryBoard> factoryBoards){
//        int startX = 20;
//        int startY = getHeight() / 4;
//        int tileSpacing = 50;
//        int tileNum = -1;
//
////        int nFactories = ((AzulParameters) gs.getGameParameters()).getNFactories();
////        int nTilesPerFactories = ((AzulParameters) gs.getGameParameters()).getNTilesPerFactory();
////        int totalTiles = nFactories * nTilesPerFactories;
//        int factoryID = 0;
//        int[][] onlyTiles = new int[4][2];
//        int count = 0;
//        int tileCount = 0;
//
//        //for (int x = 0; x < nFactories; x++) {
////            int[][] factory = factoryBoards.get(x).factoryBoard;
////
//        for (int i = 0; i < 8; i++) {
//            if (tileCount == 8) { break; }
//            if (tileNum == 4) factoryID += 1;
//
//            if (i%4 == 0) count = 0;
//
//            if (i % 5 == 0){
//                tileNum = 0;
//                factoryID +=1;
//                onlyTiles = onlyGetTiles(factoryBoards.get(factoryID).factoryBoard);
//            }
//
//            if (onlyTiles[count][1] > 1){
//                for (int s=0; s < 2; s++){
//                    drawCell(g2, startX, startY, onlyTiles[count]);
//                    startX += tileSpacing;
//                    tileNum += 1;
//                    tileCount ++;
//                }
////                i += onlyTiles[count][1];
//            }
//            else{
//                drawCell(g2, startX, startY, onlyTiles[count]);
//                tileNum += 1;
//                startX += tileSpacing;
//                tileCount ++;
//            }
////            tileNum += 1;
//            count += 1;
//        }
////            startX += tileSpacing;
//        //}
//        //}
//
////        for (AzulFactoryBoard factoryBoard : factoryBoards) {
////            int num = 0;
////            System.out.println("Drawing factory " + num);
////            // Iterate over the tiles in the current factory board
////            for (var entry : factoryBoard.factoryBoard.entrySet() ){
////                AzulTile tile = entry.getKey(); // Name of tile
////                int count = entry.getValue(); // Number of tiles in factory
////
////                for (int i=0; i < count; i++){
////                    // Draw a placeholder rectangle for the tile
////                    g2.setColor(Color.LIGHT_GRAY);
////                    g2.fillRect(startX, startY - tileSize / 2, tileSize, tileSize);
////
////                    // Draw the tile's color name in the center of the rectangle
////                    g2.setColor(Color.BLACK);
////                    String colorName = tile.name();
////                    FontMetrics fm = g2.getFontMetrics();
////                    int textX = startX + (tileSize - fm.stringWidth(colorName)) / 2;
////                    int textY = startY + (fm.getAscent() - fm.getDescent()) / 2;
////                    g2.drawString(colorName, textX, textY);
////
////                    // Move to the next position
////                    startX += tileSize + tileSpacing;
////                }
////            }
////
////            // Move to the next factory (add spacing between factories)
////            startX += 3 * tileSpacing; // Adjust for space between factories
////        }
//    }
//
//    // Iterates through factory and copies tiles that have a value greater than 0
//    private int[][] onlyGetTiles(int[][] factory){
//        int[][] tiles = new int[4][2];
//
//
//        for(int i = 0; i < factory.length; i++){
//            if (factory[i][1] != 0){
//                tiles[i] = factory[i];
//            }
//        }
//
//        for(int x=0; x < tiles.length; x++){
//            if (tiles[x][1] == 0){
//                tiles[x][1] = -1;
//            }
//        }
//
//        return tiles;
//    }
//
//    private void drawCell(Graphics2D g, int x, int y, int[] onlyTiles) {
////        AzulTile key;
////        int value;
////        int nTile = -1;
//
//        // Paint cell background
//        g.setColor(Color.lightGray);
//        g.fillRect(x, y, defaultItemSize, defaultItemSize);
//        g.setColor(Color.black);
//        g.drawRect(x, y, defaultItemSize, defaultItemSize);
//
//        // Set the font and color for the text
//        g.setColor(Color.BLACK);
//        Font originalFont = g.getFont();
//        g.setFont(new Font("Arial", Font.PLAIN, 12));
//
////        System.out.println(onlyTiles[1]);
//        //for (int j=0; j<onlyTiles.length;j++){
//        if (onlyTiles[1] == -1){
//            g.drawString("Empty", x + defaultItemSize / 4, y + defaultItemSize / 2);
//        }
//        else{
//            if (onlyTiles[0] == 0){
//                g.drawString("White", x + defaultItemSize / 4, y + defaultItemSize / 2);
//            }
//            if (onlyTiles[0] == 1){
//                g.drawString("Black", x + defaultItemSize / 4, y + defaultItemSize / 2);
//            }
//            if (onlyTiles[0] == 2){
//                g.drawString("Red", x + defaultItemSize / 4, y + defaultItemSize / 2);
//            }
//            if (onlyTiles[0] == 3){
//                g.drawString("Orange", x + defaultItemSize / 4, y + defaultItemSize / 2);
//            }
//            if (onlyTiles[0] == 4){
//                g.drawString("Blue", x + defaultItemSize / 4, y + defaultItemSize / 2);
//            }
//        }
//        //}
//
//        //for (var entry: factory.entrySet()){
////            if (entry. == 0){
////                g.drawString("Empty", x + defaultItemSize / 4, y + defaultItemSize / 2);
////            }
////
////            else {
////                String color = getTileColor(entry.getKey());
////                if (color.equals("White")) {
////                    g.drawString("White", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (color.equals("Black")) {
////                    g.drawString("Black", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (color.equals("Red")) {
////                    g.drawString("Red", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (color.equals("Orange")) {
////                    g.drawString("Orange", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (color.equals("Blue")) {
////                    g.drawString("Blue", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
//            //}
//        //}
//
////        for (int i=0; i<5; i++){
//
//
//
//            //}
////        }
//
////        int[][] factory = factoryBoards.get(factoryID).factoryBoard;
////        int[][] factoryTemp = factoryBoards.get(factoryID).factoryBoard;
////
//////        for (int j=0; j<5; j++) {
////            if (factory[1][tileNum] == 0) {
////                g.drawString("Empty", x + defaultItemSize / 4, y + defaultItemSize / 2);
////            }
////            else {
////                nTile = factory[0][tileNum];
////                if (nTile == 0) {
////                    g.drawString("White", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (nTile == 1) {
////                    g.drawString("Black", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (nTile == 2) {
////                    g.drawString("Red", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (nTile == 3) {
////                    g.drawString("Orange", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////                if (nTile == 4) {
////                    g.drawString("Blue", x + defaultItemSize / 4, y + defaultItemSize / 2);
////                }
////            //}
////        }
//
//    }
//
////        for (var entry : factoryBoards.get(factoryID).factoryBoard.entrySet()){
////            if (entry.getValue() != 0) {
////                key = entry.getKey();
////                value = entry.getValue();
////
//////                String color = getTileColor(key);
//////                System.out.println(color);
//////                g.drawString(color, x + defaultItemSize/4, y + defaultItemSize/2);
////                if (key == AzulTile.White){
////                    g.drawString("White", x + defaultItemSize/4,y + defaultItemSize/2 );
////                }
////                if (key == AzulTile.Black){
////                    g.drawString("Black", x + defaultItemSize/4,y + defaultItemSize/2 );
////                }
////                if (key == AzulTile.Red){
////                    g.drawString("Red", x + defaultItemSize/4,y + defaultItemSize/2 );
////                }
////                if (key == AzulTile.Orange){
////                    g.drawString("Orange", x + defaultItemSize/4,y + defaultItemSize/2 );
////                }
////                if (key == AzulTile.Blue){
////                    g.drawString("Blue", x + defaultItemSize/4,y + defaultItemSize/2 );
////                }
////                break;
////            }
////            else{
////                g.drawString("Empty", x + defaultItemSize/4, y + defaultItemSize/2);
////            }
////        }
////        g.setFont(originalFont);
////    }
//
////    // This needs to be tested!!!
//    private String getTileColor(AzulTile tile) {
//        switch (tile) {
//            case Blue:
//                return "Blue";
//            case Red:
//                return "Red";
//            case White:
//                return "White";
//            case Orange:
//                return "Orange";
//            case Black:
//                return "Black";
//            default:
//                return "Empty";
//        }
//    }
////    }
//
//    @Override
//    public void clearHighlights() {
//
//    }
//}