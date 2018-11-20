package dp.k2;

class Analyze {
    static int analyze(State s, boolean isNorth) {
        State[] newState = new State[s.getHouseCount()];
        House[] currentHouses = isNorth ? s.getNorthHouses() : s.getSouthHouses();

        // Attempt all possible moves - If a move is not allowed, set the newState to null
        //
        for( int i=0; i < s.getHouseCount(); i++ ) {
            if( currentHouses[i].getCount() > 0) {
                newState[i] = s.applyMove(i, isNorth);
            } else {
                newState[i] = null;
            }
        }

        // Basic strategy:
        //   If a move will result in another turn, take that move
        //   Otherwise take the move that will result in the largest tally in the end zone
        //
        int currentHouse = s.getHouseCount() - 1;
        int highestEz = 0;
        int highestHouse = 0;
        while( currentHouse >= 0 ) {
            State candidateState = newState[currentHouse];
            if( candidateState != null) {
                if( candidateState.isAnotherTurn() ) {
                    return currentHouse;
                }
                int testEz = isNorth ?
                        candidateState.getNorthEndZone().getCount() : candidateState.getSouthEndZone().getCount();
                if( testEz > highestEz ) {
                    highestEz = testEz;
                    highestHouse = currentHouse;
                }
            }
            currentHouse -= 1;
        }

        return highestHouse;
    }
}
