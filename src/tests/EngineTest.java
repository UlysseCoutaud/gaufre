
package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import util.Logger;
import engine.Engine;

public class EngineTest {

    public static void main(String[] args) throws IOException {
        Logger.enableEngineLog = true;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("number of human players : ");
        int nbOfPlayers = Integer.valueOf(br.readLine());

        Engine engine;

        int width = 16, height = 9;

        engine = new Engine(width, height, nbOfPlayers);

        if (nbOfPlayers == 0) {
            engine.startAIMatch();
        } else {
            while (!engine.getCurrentGameState().mustLose()) {
                System.out.println("\n # Player " + engine.getCurrentPlayer() + " :");
                System.out.print("  -  choose x : ");
                int x = Integer.valueOf(br.readLine());
                System.out.print("  -  choose y : ");
                int y = Integer.valueOf(br.readLine());
                engine.play(x, y);
            }
        }
    }
}
