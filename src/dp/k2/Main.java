package dp.k2;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int DEFAULT_HOUSE_COUNT = 6;
    private static final int DEFAULT_SEED_COUNT = 4;

    private static State humanTurn(State s, boolean isNorth) {
        System.out.println("Human is playing " + (isNorth ? "North" : "South"));
        Scanner scan = new Scanner(System.in);
        int move = scan.nextInt();
        return s.applyMove(move, isNorth);
    }

    private static State computerTurn(State s, boolean isNorth) {
        System.out.println("Computer is playing " + (isNorth ? "North" : "South"));
        int move = Analyze.analyze(s, isNorth);
        System.out.println("Computer plays " + move);
        return s.applyMove(move, isNorth);
    }

    public static void main(String[] args) {
        State s = new State(DEFAULT_HOUSE_COUNT, DEFAULT_SEED_COUNT);
        Random r = new Random();
        boolean player = (r.nextInt(2)) == 0;
        boolean humanIsNorth = (r.nextInt(2)) == 0;

        if( player ) {
            System.out.println("Human goes first");
        } else {
            System.out.println("Computer goes first");
        }
        System.out.println(Render.renderText(s));
        while( true ) {
            do {
                if( player ) {
                    s = humanTurn(s, humanIsNorth);
                } else {
                    s = computerTurn(s, !humanIsNorth);
                }
                System.out.println(Render.renderText(s));
                if( s.evaluate() != State.Outcome.NO_DECISION) {
                    break;
                }
            } while( s.isAnotherTurn() );
            if( s.evaluate() != State.Outcome.NO_DECISION) {
                break;
            }
            player = !player;
        }
        System.out.println("-----");
        System.out.println(s.evaluate());
        System.out.println(Render.renderText(s));
    }
}
