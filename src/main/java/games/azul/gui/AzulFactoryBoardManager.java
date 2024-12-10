package games.azul.gui;

import java.util.ArrayList;
import java.util.List;

public class AzulFactoryBoardManager {
    private static List<AzulFactoryBoardView> boardViews = new ArrayList<>();

    public static void register(AzulFactoryBoardView view) {
        boardViews.add(view);
    }

    public static void clearAllHighlights(AzulFactoryBoardView activeView) {
        for (AzulFactoryBoardView view : boardViews) {
            if (view != activeView) {
                view.clearHighlights();
                view.repaint();
            }
        }
    }
}
