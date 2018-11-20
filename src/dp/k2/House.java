package dp.k2;

class House {
    private int count;
    private boolean isEndZone;
    private boolean isNorth;
    private House next;
    private House opposite;

    House(final int count, final boolean isEndZone, final boolean isNorth) {
        this.count = count;
        this.isEndZone = isEndZone;
        this.isNorth = isNorth;
        this.next = null;
        this.opposite = null;
    }

    House(final House h) {
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

    void setNext(final House h) { this.next = h; }

    void setOpposite(final House h) { this.opposite = h; }
}
