package games.azul.gui;

import core.components.Component;
import core.components.GridBoard;
import games.azul.AzulGameState;
import games.azul.components.AzulFactoryBoard;
import games.azul.components.AzulPlayerBoard;
import gui.GUI;
import gui.IScreenHighlight;
import gui.views.ComponentView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static gui.AbstractGUIManager.defaultItemSize;

public class AzulPlayerBoardView extends ComponentView implements IScreenHighlight {

    AzulGameState gs;

    Rectangle[] rects;
    ArrayList<Rectangle> highlight;

    int offsetX = 50;
    int offsetY = 50;

    int marginBetweenBoards = 50; // Space between the boards


    public AzulPlayerBoardView(GridBoard<AzulPlayerBoard> gridBoard, AzulGameState gs) {
        super(gridBoard, 0, 0);
        this.gs = gs;

        rects = new Rectangle[gridBoard.getWidth() * gridBoard.getHeight()];
        highlight = new ArrayList<>();

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
    protected void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        GridBoard<AzulPlayerBoard> gridBoard = (GridBoard<AzulPlayerBoard>) component;

        // Calculate individual board dimensions
        int boardWidth = gridBoard.getWidth() * GUI.defaultItemSize;
        int boardHeight = gridBoard.getHeight() * GUI.defaultItemSize;

        // Calculate combined width of the two boards and center position
        int combinedWidth = boardWidth * 2 + marginBetweenBoards;
        int startX = (this.getWidth() - combinedWidth) / 2;
        int centerY = (this.getHeight() - boardHeight) / 2;

        // Calculate positions for each board
        int tempBoardOffsetX = startX;
        int playerBoardOffsetX = startX + boardWidth + marginBetweenBoards;

        // Draw the temporary board (left)
        drawPlayerTempBoard(g, gridBoard, tempBoardOffsetX, centerY);

        // Draw the main player board (right)
        drawPlayerBoard(g, gridBoard, playerBoardOffsetX, centerY);

        if (highlight.size() > 0){
            g.setColor(Color.green);
            Stroke s = g.getStroke();
            g.setStroke(new BasicStroke(3));

            Rectangle r = highlight.get(0);
            g.drawRect(r.x, r.y, r.width, r.height);
            g.setStroke(s);
        }
    }

    // Draws Wall
    private void drawPlayerBoard(Graphics2D g, GridBoard<AzulPlayerBoard> gridBoard, int x, int y) {

        // Draw cells
        for (int i = 0; i < gridBoard.getHeight(); i++) {
            for (int j = 0; j < gridBoard.getWidth(); j++) {
                int xC = x + j * GUI.defaultItemSize;
                int yC = y + i * GUI.defaultItemSize;
                drawCell(g, gridBoard.getElement(j, i), xC, yC);

                // Save rect where cell is drawn
                int idx = i * gridBoard.getWidth() + j;
                if (rects[idx] == null) {
                    rects[idx] = new Rectangle(xC, yC, GUI.defaultItemSize, GUI.defaultItemSize);
                }
            }
        }
    }

    // Draws Pattern Line from right to left
    private void drawPlayerTempBoard(Graphics2D g, GridBoard<AzulPlayerBoard> gridBoard, int x, int y) {
        for (int i = 0; i < gridBoard.getHeight(); i++) {
            // Calculate the starting x-coordinate for the current row (right-aligned)
            int startX = x + (gridBoard.getHeight() - 1 - i) * GUI.defaultItemSize;

            for (int j = 0; j <= i; j++) {
                int xC = startX + j * GUI.defaultItemSize; // Position cells from right to left
                int yC = y + i * GUI.defaultItemSize;      // Row positioning
                drawCell(g, gridBoard.getElement(j, i), xC, yC);

                // Save rect where cell is drawn
                int idx = i * gridBoard.getWidth() + j;
                if (rects[idx] == null) {
                    rects[idx] = new Rectangle(xC, yC, GUI.defaultItemSize, GUI.defaultItemSize);
                }
            }
        }
    }

    private void drawCell(Graphics2D g, AzulPlayerBoard element, int x, int y){
        // Paint cell background
        g.setColor(Color.lightGray);
        g.fillRect(x, y, GUI.defaultItemSize, GUI.defaultItemSize);
        g.setColor(Color.black);
        g.drawRect(x, y, GUI.defaultItemSize, GUI.defaultItemSize);

        // Set the font and color for the text
        g.setColor(Color.BLACK);
        Font originalFont = g.getFont();
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Paint element in cell
        if (element != null) {
            Font f = g.getFont();
            g.setFont(new Font(f.getName(), Font.BOLD, GUI.defaultItemSize * 3 / 2));
            g.drawString(element.toString(), x + GUI.defaultItemSize / 16, y + GUI.defaultItemSize - GUI.defaultItemSize / 16);
            g.setFont(f);
        }
        else{
            g.drawString("Empty", x + GUI.defaultItemSize / 16, y + GUI.defaultItemSize - GUI.defaultItemSize / 16 );
        }
    }

    public ArrayList<Rectangle> getHighlight() { return highlight; }

    @Override
    public void clearHighlights() { highlight.clear(); }

}