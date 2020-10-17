package metah.model;

public class Depot extends Location {
    public Depot(int number, int x, int y) {
        super(number, x, y);
    }

    @Override
    public boolean isDepot() {
        return true;
    }

    @Override
    public boolean isShop() {
        return false;
    }
}
