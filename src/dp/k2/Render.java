package dp.k2;

class Render {
    
    private static String renderFrame(State s) {
        StringBuilder ret = new StringBuilder();
        ret.append("+----+----");
        for(int i=0; i< s.getHouseCount(); i++) {
            ret.append("+----");
        }
        ret.append("+\n");
        return ret.toString();
    }

    private static String renderHeader(State s) {
        StringBuilder ret = new StringBuilder();
        ret.append("       ");
        for(int i=s.getHouseCount() - 1; i >= 0; i--) {
            ret.append(String.format("N%1d   ", i));
        }
        ret.append("\n");
        return ret.toString();
    }

    private static String renderNorth(State s) {
        StringBuilder ret = new StringBuilder();
        ret.append(String.format("| %02d ", s.getNorthEndZone().getCount()));
        for(int i=s.getHouseCount() - 1; i >=0 ; i--) {
            ret.append(String.format("| %02d ", s.getNorthHouses()[i].getCount()));
        }
        ret.append("|    |\n");
        return ret.toString();
    }

    private static String renderSouth(State s) {
        StringBuilder ret = new StringBuilder();
        ret.append("|    ");
        for(int i=0; i< s.getHouseCount(); i++) {
            ret.append(String.format("| %02d ", s.getSouthHouses()[i].getCount()));
        }
        ret.append(String.format("| %02d |\n", s.getSouthEndZone().getCount()));
        return ret.toString();
    }

    private static String renderFooter(State s) {
        StringBuilder ret = new StringBuilder();
        ret.append("       ");
        for(int i=0; i< s.getHouseCount(); i++) {
            ret.append(String.format("S%1d   ", i));
        }
        ret.append("\n");
        return ret.toString();
    }

    static String renderText(State s) {
        String ret = "";
        ret += renderHeader(s);
        ret += renderFrame(s);
        ret += renderNorth(s);
        ret += renderSouth(s);
        ret += renderFrame(s);
        ret += renderFooter(s);
        return ret;
    }

}
