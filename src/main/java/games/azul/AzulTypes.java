package games.azul;

import core.components.GridBoard;
import org.apache.hadoop.shaded.com.nimbusds.jose.shaded.json.parser.JSONParser;

import java.awt.*;
import java.util.HashSet;

public class AzulTypes {

    //Enums

    public enum ActionType {
        PickUpTiles,
        PlaceTiles
    }

    public enum PickUpTilesAction {
        PickUpWhite,
        PickUpBlack,
        PickUpRed,
        PickUpOrange,
        PickUpBlue
    }

    public enum PlaceTilesAction {
        FirstRow,
        SecondRow,
        ThirdRow,
        FourthRow,
        FifthRow
    }

    public enum ScoringType {
        WallTile,
        CompletedRow,
        CompletedColumn,
        CompletedColor,
        FloorPenalty
    }

    public enum FloorPenalty {
        Position1(-1),
        Position2(-1),
        Position3(-2),
        Position4(-2),
        Position5(-2),
        Position6(-3),
        Position7(-3);

        int penaltyPoints;

        FloorPenalty(int penaltyPoints) { this.penaltyPoints = penaltyPoints; }
    }

    // Map for scoring tiles
    public enum MapScoreTrackTileType{
        White (Color.white),
        Orange (Color.orange);

        Color outline; //Does it need to be final?
        MapScoreTrackTileType(Color outline) { this.outline = outline; }

        public Color getOutlineColor() { return outline; }
    }

//    public enum MapPatternLinesTileType {
//        White (Color.white);
//
//        Color outline;
//        MapPatternLinesTileType(Color outline) { this.outline = outline; }
//    }
//
//    public enum MapWallTileTypes {
//
//    }
//
//    public enum MapFloorLineType {
//
//    }

    public enum Tile {
        // starting player tile needed?????
        White ("data/azul/images/tiles/White.jpg"),
        Black ("data/azul/images/tiles/Black.jpg"),
        Red ("data/azul/images/tiles/Red.jpg"),
        Orange ("data/azul/images/tiles/Orange.jpg"),
        Blue ("data/azul/images/tiles/Blue.jpg");

        String imagePath; //Does it need to be final?

        Tile(String imagePath) { this.imagePath = imagePath; }

        public String getImagePath() { return imagePath; }
    }
}
