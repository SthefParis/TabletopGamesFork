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

                            AzulFactoryBoardManager.clearAllHighlights(AzulFactoryBoardView.this);
                            repaint();
                            break;
                        }
                    }
                } else {
                    highlight.clear(); // Remove highlight
                    repaint();
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


        for (int i=0; i < numRows; i++) {
            for (int j=0; j < numColumns; j++){
                int index = i * numColumns + j; // Checks that index is within list bound
                if (index < azulFactory.factoryBoard.length) {
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

        return tiles;
    }

    private void drawCell(Graphics2D g, int[] tiles, int cellNum, int x, int y){
        // Paint cell background
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