package dp.k2;

class House {
    private int count;
    private boolean isEndZone;
    private boolean isNorth;
    private House next;
    private House opposite;

    House(int count, boolean isEndZone, boolean isNorth) {
        this.count = count;
        this.isEndZone = isEndZone;
        this.isNorth = isNorth;
        this.next = null;
        this.opposite = null;
    }

    House(House h) {
        this.count = h.count;
        this.isEndZone = h.isEndZone;
        this.isNorth = h.isNorth;
        this.next = null;
        this.opposite = null;
    }

    int getCount() { return this.count; }

    boolean isEndZone() { return this.isEndZone; }

    boolean isNorth() { return this.isNorth; }

    House getNext() { return this.next; }

    House getOpposite() { return this.opposite; }

    void setCount(int c) { this.count = c; }

    void setNext(House h) { this.next = h; }

    void setOpposite(House h) { this.opposite = h; }
}
