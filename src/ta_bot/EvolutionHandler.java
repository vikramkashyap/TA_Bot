package ta_bot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Purpose: This runs a genetic algorithm
 * 
 * @author Carson Cummins
 * @version 0.0
 */
public class EvolutionHandler {

public static <T extends Member<T>> T train(int populationSize, int maxGenerations,float crossoverRatio,float elitismRatio,float mutationRatio, double err, T parent) throws IOException{
	Population<T> pop = new Population<>(populationSize, crossoverRatio, elitismRatio, mutationRatio,parent);
	// Start evolving the population, stopping when the maximum number of
	// generations is reached, or when we find a solution.
	String scrstr = "";
	int i = 0;
	Holder<T> best = pop.getPopulation().get(0);
	long sum = 0;
	while (true) {
		if(i++ >= maxGenerations) {
			if(best.getScore()<-4500) {
				break;
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\301968\\Desktop\\tabot.log"));
		System.out.println("Generation " + i + ": " + best.getScore());
		if(best.getScore()<err) {
			break;
		}
		best.getMember().serialize("C:\\Users\\301968\\Desktop\\BestNetwork.tabot");
		long start = System.currentTimeMillis();
		pop.evolve();
		ArrayList<Holder<T>> popstuff = pop.getPopulation();
		best = popstuff.get(0);
		double scoresum = 0;
		for(Holder<T> tt : popstuff) {
			scoresum-=tt.getScore();
		}
		scoresum = scoresum/popstuff.size();
		scrstr+=scoresum+",";
		writer.write(scrstr);
		writer.close();
		sum+=System.currentTimeMillis()-start;
	}
	System.out.println("average generation time = "+sum/i);
	return best.getMember();
}

}
