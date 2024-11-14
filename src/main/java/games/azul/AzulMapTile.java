package games.azul;

import core.CoreConstants;
import core.components.Component;

import static core.CoreConstants.ComponentType.BOARD_NODE;

public class AzulMapTile extends Component {

    int x, y;
    AzulTypes.Tile tilePlaced;

    int taken = -1;

    public AzulMapTile(int x, int y) {
        super(BOARD_NODE, "Tile");
        this.x = x;
        this.y = y;
    }

    public AzulMapTile(int x, int y, int componentID) {
        super(BOARD_NODE, "Tile", componentID);
        this.x = x;
        this.y = y;
    }

    public boolean isTaken() { return taken != -1; }

    public int getTaken() { return taken; }

    public void setTaken(int taken) { this.taken = taken; }

    public AzulTypes.Tile getTilePlaced() { return tilePlaced; }

    public int getX() { return x; }

    public int getY() {return y; }

    public void setTilePlaced(AzulTypes.Tile tile) { this.tilePlaced = tile; }

    public boolean placeTile(AzulTypes.Tile tile, AzulGameState gs) {
        if (!isTaken()) {
            setTilePlaced(tile);
            this.taken = gs.getCurrentPlayer();
            return true;
        }
        return false;
    }

    @Override
    public Component copy() {
        AzulMapTile copy = new AzulMapTile(x, y, this.componentID);
        copy.tilePlaced = this.tilePlaced;
        copy.taken = this.taken;
        return copy;
    }
}
