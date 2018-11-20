package ta_bot;

import java.util.Scanner;

public class Main {
	public final int POP_SIZE = 100;
	public final int MAX_GENS = 100000;
	public final float CROSSOVER_RATE = .5f;
	public final float ELITE_RATE = .1f;
	public final float MUTATE_RATE = .05f;
	public final double REQ_ERROR = .001;
	
	public Main() {
		NeuralNetwork network = EvolutionHandler.train(POP_SIZE, MAX_GENS, CROSSOVER_RATE, ELITE_RATE, MUTATE_RATE, REQ_ERROR, new NeuralNetwork());
		network.serialize(NeuralNetwork.SERIALIZE_FILEPATH);
	}

	public static void main(String[] args) {
		new Main();

	}

}

