package ta_bot;
/*
 * 
 * 
 * This version runs a mcuh less complex NN so it should converge faster i think
 */
import java.io.IOException;

public class Main {
	public final int POP_SIZE = 20;
	public final int MAX_GENS = 1500;
	public final float CROSSOVER_RATE = .2f;
	public final float ELITE_RATE = .1f;
	public final float MUTATE_RATE = .02f;
	public final double REQ_ERROR = -5000;
	
	public Main() throws IOException {
		NeuralNetwork network = EvolutionHandler.train(POP_SIZE, MAX_GENS, CROSSOVER_RATE, ELITE_RATE, MUTATE_RATE, REQ_ERROR, new NeuralNetwork());
		network.serialize("C:\\Users\\301968\\git\\TA_Bot\\bin\\ta_bot\\NeuralNetwork.tabot");
		System.out.println("Training is done my sirs, find the serialized network at "
		+ NeuralNetwork.SERIALIZE_FILEPATH);

	}

	public static void main(String[] args) {
		try {
			new Main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

