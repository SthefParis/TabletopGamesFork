package games.azul.tiles;

// This script refers to the tiles
public enum AzulTile {
    White("data/azul/images/tiles/White.jpg"),
    Black("data/azul/images/tiles/Black.jpg"),
    Red("data/azul/images/tiles/Red.jpg"),
    Orange("data/azul/images/tiles/Orange.jpg"),
    Blue("data/azul/images/tiles/Blue.jpg");

    final String imagePath;

    AzulTile(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}


