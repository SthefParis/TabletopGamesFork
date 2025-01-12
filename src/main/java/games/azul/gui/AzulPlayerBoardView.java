package games.azul.gui;

import core.components.Component;
import core.components.GridBoard;
import games.azul.AzulGameState;
import games.azul.AzulParameters;
import games.azul.components.AzulFactoryBoard;
import games.azul.components.AzulPlayerBoard;
import gui.GUI;
import gui.IScreenHighlight;
import gui.views.ComponentView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static gui.AbstractGUIManager.defaultItemSize;

public class AzulPlayerBoardView extends ComponentView implements IScreenHighlight {

    AzulGameState gs;

    Rectangle[] rects;
    ArrayList<Rectangle> highlight;

    AzulPlayerBoard azulPlayerBoard;

    final static int offsetX = 50;
    final static int offsetY = 50;

    final static int marginBetweenBoards = 50; // Space between the boards

    final static int colorFontSize = 8;
    final static int numberFontSize = 12;

    int defaultWidth = GUI.defaultItemSize/2;
    int defaultHeight = GUI.defaultItemSize/2;


    public AzulPlayerBoardView(AzulPlayerBoard azulPlayerBoard, AzulGameState gs) {
        super(null, 0, 0); //What goes in c??
        this.gs = gs;

        rects = new Rectangle[azulPlayerBoard.playerBoard.length*azulPlayerBoard.playerTempBoard.length];
        highlight = new ArrayList<>();
        this.azulPlayerBoard = azulPlayerBoard;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Left click, highlight cell
                    for (Rectangle r : rects) {
                        if (r != null && r.contains(e.getPoint())) {
                            highlight.clear();
                            highlight.add(r);

                            repaint();
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
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        // Calculate width of the temporary board
        int tempBoardWidth = azulPlayerBoard.playerTempBoard.length * GUI.defaultItemSize / 2;

        // Calculate height of the boards
        int tempBoardHeight = azulPlayerBoard.playerTempBoard.length * GUI.defaultItemSize / 2;
        int playerBoardHeight = azulPlayerBoard.playerBoard.length * GUI.defaultItemSize / 2;
        int totalBoardHeight = Math.max(tempBoardHeight, playerBoardHeight);

        int scoreTrackHeight = azulPlayerBoard.playerScoreTrack.length * GUI.defaultItemSize /2;

        // Calculate the x-offset for the player board (to the right of the temp board)
        int playerBoardOffsetX = offsetX + tempBoardWidth + marginBetweenBoards;

        // Calculate the x-offset for the floor line (below player and temp board)
        int floorLineOffsetY = offsetY + totalBoardHeight + marginBetweenBoards;

        // Calculates the y-offset for the score track (above player and temp board)
        int scoreTrackOffsetY = GUI.defaultItemSize/3;

        // Calculate the y-offset for the player board (below the score track)
        int playerBoardOffsetY = offsetY + scoreTrackOffsetY  + 10;

        // Draw the temporary board (left)
        drawPlayerTempBoard(g, this.azulPlayerBoard, offsetX, playerBoardOffsetY);

        // Draw the mosaic wall (right)
        drawPlayerBoard(g, this.azulPlayerBoard, playerBoardOffsetX, playerBoardOffsetY);

        // Draw the floor line (above temo board and wall)
        drawFloorLine(g, this.azulPlayerBoard,offsetX, floorLineOffsetY);

        drawScoreTrack(g, this.azulPlayerBoard, offsetX, scoreTrackOffsetY);

        if (!highlight.isEmpty()){
            g.setColor(Color.green);
            Stroke s = g.getStroke();
            g.setStroke(new BasicStroke(3));

            Rectangle r = highlight.get(0);
            g.drawRect(r.x, r.y, r.width, r.height);
            g.setStroke(s);
        }
    }

    // Draws Wall
    private void drawPlayerBoard(Graphics2D g, AzulPlayerBoard playerBoard, int x, int y) {

        // Draw cells
        for (int i = 0; i < playerBoard.playerBoard.length; i++) {
            for (int j = 0; j < playerBoard.playerBoard.length; j++) {
                int xC = x + j * GUI.defaultItemSize/2;
                int yC = y + i * GUI.defaultItemSize/2;
                drawCell(g, playerBoard, xC, yC, defaultWidth, defaultHeight, null);
            }
        }
    }

    // Draws Pattern Line from right to left
    private void drawPlayerTempBoard(Graphics2D g, AzulPlayerBoard tempBoard, int x, int y) {
//        System.out.println("Printing temp board");
        for (int i = 0; i < tempBoard.playerTempBoard.length; i++) {
            // Calculate the starting x-coordinate for the current row (right-aligned)
            int startX = x + (5 - 1 - i) * GUI.defaultItemSize/2;

            for (int j = 0; j <= i; j++) {
                int xC = startX + j * GUI.defaultItemSize/2; // Position cells from right to left
                int yC = y + i * GUI.defaultItemSize/2;      // Row positioning
                drawCell(g, tempBoard, xC, yC, defaultWidth, defaultHeight, null);

                // Save rect where cell is drawn
                int idx = i * tempBoard.playerTempBoard.length + j;
                if (rects[idx] == null) {
                    rects[idx] = new Rectangle(xC, yC, GUI.defaultItemSize/2, GUI.defaultItemSize/2);
                }
            }
        }
    }

    private void drawFloorLine(Graphics2D g, AzulPlayerBoard playerFloorLine,int x, int y) {
        AzulParameters params =(AzulParameters) gs.getGameParameters();
        int[] floorPenalties = params.getFloorPenalties();

        for (int i = 0; i < playerFloorLine.playerFloorLine.length; i++) {
            int startX = x + i * GUI.defaultItemSize/2;

            drawCell(g, playerFloorLine, startX, y, defaultWidth, defaultHeight, null);

            int textY = y + (GUI.defaultItemSize/4);

            g.setFont(new Font("Arial", Font.PLAIN, numberFontSize));
            g.drawString(String.valueOf(floorPenalties[i]), startX+10, textY);
        }
    }

    private void drawScoreTrack(Graphics2D g, AzulPlayerBoard playerScoreTrack, int x, int y) {
        AzulParameters params =(AzulParameters) gs.getGameParameters();

        int cellsPerRow = 20;
        int cellWidth = defaultWidth/3;
        int cellHeight = defaultHeight/3;

        // Draw cell 0 in the first row
        drawCell(g, playerScoreTrack, x, y, cellWidth, cellHeight, Color.orange);

        for (int i = 1; i < playerScoreTrack.playerScoreTrack.length; i++) {
            // Calculate row and column based on index and cells per row
            int row = (i - 1) / cellsPerRow  + 1;
            int col = (i - 1) % cellsPerRow;

            // Calculate position of cells without spacing
            int startX = x + col * cellWidth;
            int startY = y + row * cellHeight;

            if (i % 5 == 0){
                // If cell is a multiple of 5, cell should be orange
                drawCell(g, playerScoreTrack, startX, startY, cellWidth, cellHeight, Color.orange);

            }
            else {
                // Other cells should remain grey
                drawCell(g, playerScoreTrack, startX, startY, cellWidth, cellHeight, null);
            }
        }
    }

    private void drawCell(Graphics2D g, AzulPlayerBoard element, int x, int y, int width, int height, Color bgColor){
        // Paint cell background
        if (bgColor == null) {
            g.setColor(Color.lightGray);
        }
        else{
            g.setColor(bgColor);
        }

        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);

        // Set the font and color for the text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, colorFontSize));
    }

    public ArrayList<Rectangle> getHighlight() { return highlight; }

    @Override
    public void clearHighlights() { highlight.clear(); }

}