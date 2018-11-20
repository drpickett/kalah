package dp.k2;

class State {
    private int houseCount;
    private int seedCount;
    private House northEndZone;
    private House southEndZone;
    private House[] northHouses;
    private House[] southHouses;
    private boolean anotherTurn;

    public enum Outcome {NO_DECISION, NORTH_WINS, SOUTH_WINS, TIE}

    // Construct a Kalah board of arbitrary size
    //
    State(int houseCount, int seedCount) {
        this.houseCount = houseCount;
        this.seedCount = seedCount;
        this.northEndZone = new House(0, true, true);
        this.southEndZone = new House(0, true, false);
        this.anotherTurn = false;
        this.northHouses = new House[this.houseCount];
        this.southHouses = new House[this.houseCount];
        for(int i=0; i<this.houseCount; i++) {
            this.northHouses[i] = new House(this.seedCount, false, true);
            this.southHouses[i] = new House(this.seedCount, false, false);
        }
        connectHouses();
    }

    // Copy Constructor
    //
    private State(State s) {
        this.houseCount = s.houseCount;
        this.seedCount = s.seedCount;
        this.northEndZone = new House(s.northEndZone);
        this.southEndZone = new House(s.southEndZone);
        this.anotherTurn = s.anotherTurn;
        this.northHouses = new House[this.houseCount];
        this.southHouses = new House[this.houseCount];
        for(int i=0; i<this.houseCount; i++) {
            this.northHouses[i] = new House(s.northHouses[i]);
            this.southHouses[i] = new House(s.southHouses[i]);
        }
        connectHouses();
    }

    // Connect the houses with their opposite and next houses
    //
    private void connectHouses() {
        for(int i=0; i<this.houseCount; i++) {
            this.northHouses[i].setOpposite(this.southHouses[this.houseCount - i - 1]);
            this.southHouses[i].setOpposite(this.northHouses[this.houseCount - i - 1]);
            if( i == this.houseCount - 1) {
                this.northHouses[i].setNext(this.northEndZone);
                this.southHouses[i].setNext(this.southEndZone);
            } else {
                this.northHouses[i].setNext(this.northHouses[i+1]);
                this.southHouses[i].setNext(this.southHouses[i+1]);
            }
        }
        this.northEndZone.setNext(this.southHouses[0]);
        this.southEndZone.setNext(this.northHouses[0]);
        this.northEndZone.setOpposite(null);
        this.southEndZone.setOpposite(null);
    }

    int getHouseCount() {return this.houseCount;}

    House getNorthEndZone() {return this.northEndZone;}

    House getSouthEndZone() {return this.southEndZone;}

    House[] getNorthHouses() {return this.northHouses;}

    House[] getSouthHouses() {return this.southHouses;}

    boolean isAnotherTurn() {return this.anotherTurn;}

    State applyMove(int house, boolean isNorth) {
        State newState = new State(this);
        int count;
        House currentHouse;

        // Set the anotherTurn flag to FALSE, empty the house that is being played, and set the count
        //
        currentHouse = isNorth ? newState.northHouses[house] : newState.southHouses[house];
        newState.anotherTurn = false;
        count = currentHouse.getCount();
        currentHouse.setCount(0);
        currentHouse = currentHouse.getNext();

        while (count > 0) {
            currentHouse.setCount(currentHouse.getCount() + 1);
            count -= 1;

            // When we get to the last seed, there are two cases to evaluate
            //
            if( 0 == count ) {
                // 1) If currentHouse is the turn maker's own endZone, signal another move
                //
                if( currentHouse.isEndZone() ) {
                    if( currentHouse.isNorth() == isNorth ) {
                        newState.anotherTurn = true;
                    }
                } else {
                    // 2) If the currentHouse is on the turn makers own side, and has a count of 1 now, and the opposite
                    //    house is not empty, then add the count of the opposite House to the endZone, and set the count
                    //    of the opposite house to 0 - Add the one in the currentHouse to the endZone, and set the
                    //    currentHouse count to 0
                    //
                    if (currentHouse.isNorth() == isNorth) {
                        if (1 == currentHouse.getCount()) {
                            House opposite = currentHouse.getOpposite();
                            if (opposite.getCount() > 0) {
                                House ez = isNorth ? newState.northEndZone : newState.southEndZone;
                                ez.setCount(ez.getCount() + opposite.getCount() + 1);
                                opposite.setCount(0);
                                currentHouse.setCount(0);
                            }
                        }
                    }
                }
            }
            currentHouse = currentHouse.getNext();
        }

        // If this turn has emptied on the houses on either side, then the game is over.  Empty the
        // houses and add them to their respective endzone
        //
        int northCount = 0;
        int southCount = 0;
        for(int i=0; i<this.houseCount; i++) {
            northCount += newState.northHouses[i].getCount();
            southCount += newState.southHouses[i].getCount();
        }
        if(( northCount == 0 ) || ( southCount == 0)) {
            newState.northEndZone.setCount(newState.northEndZone.getCount() + northCount);
            newState.southEndZone.setCount(newState.southEndZone.getCount() + southCount);
            newState.anotherTurn = false;
            for(int i=0; i<this.houseCount; i++) {
                newState.northHouses[i].setCount(0);
                newState.southHouses[i].setCount(0);
            }
        }

        return newState;
    }

    Outcome evaluate() {
        int northTotal = 0;
        int southTotal = 0;
        for(int i=0; i<this.houseCount; i++) {
            northTotal += this.northHouses[i].getCount();
            southTotal += this.southHouses[i].getCount();
        }
        if( ( 0 == northTotal ) || ( 0 == southTotal ) ) {
            int northScore = this.northEndZone.getCount();
            int southScore = this.southEndZone.getCount();
            if( northScore > southScore ) {
                return Outcome.NORTH_WINS;
            }
            if( southScore > northScore ) {
                return Outcome.SOUTH_WINS;
            }
            return Outcome.TIE;
        }
        return Outcome.NO_DECISION;
    }

}
