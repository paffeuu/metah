package metah.model;

public class Shop extends Location {
    private int demand;

    public Shop(int number, int x, int y) {
        super(number, x, y);
    }

    public int getDemand() {
        return demand;
    }

    @Override
    public boolean isDepot() {
        return false;
    }

    @Override
    public boolean isShop() {
        return true;
    }
}
