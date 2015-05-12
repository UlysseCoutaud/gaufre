package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import engine.Engine;

public class EngineTest {

	public static void main(String[] args) throws IOException {
		Engine engine;

		int nbOfPlayers = 2;
		int width = 10, height = 10;

		engine = new Engine(width, height, nbOfPlayers);

		if (nbOfPlayers == 0) {
			engine.startAIMatch();
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			while (!engine.getCurrentGameState().mustLose()) {
				System.out.println("\n # Player " + engine.getCurrentPlayer() + " :");
				System.out.print("  -  choose x : ");
				String str = br.readLine();
				int x = Integer.valueOf(str);
				System.out.print("  -  choose y : ");
				str = br.readLine();
				int y = Integer.valueOf(str);
				engine.play(x, y);
			}
		}
	}
}