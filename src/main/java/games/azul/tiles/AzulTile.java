package games.azul.tiles;

// This script refers to the tiles
public enum AzulTile {
    White(0),
    Black(1, "data/azul/images/tiles/Black.jpg"),
    Red(2, "data/azul/images/tiles/Red.jpg"),
    Orange(3, "data/azul/images/tiles/Orange.jpg"),
    Blue(4, "data/azul/images/tiles/Blue.jpg");

    final int colorCode;
    final String imagePath;

    AzulTile(int colorCode){
        this.colorCode = colorCode;
        this.imagePath =null;
    }

    AzulTile(int colorCode, String imagePath) {
        this.colorCode = colorCode;
        this.imagePath = imagePath;
    }

//    public String getColor(){ return this.name(); }

    public int getColorCode() { return colorCode; }

    public String getImagePath() {
        return imagePath;
    }
}


