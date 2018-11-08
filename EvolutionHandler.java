
import java.math.BigDecimal;
import java.util.ArrayList;

public class EvolutionHandler {

	public static <T extends Member<T>> T evolve(ArrayList<ArrayList<Floatl>> in ,ArrayList<ArrayList<Float>> goal, int generations, int startingPool, T parent) {
	//iterate over generations
		//initial population
		Population<T> pop = new Population<T>(in.get(0).size(),goal.get(0).size(),startingPool,parent);
		T best = null;
		int bestScore = -Integer.MAX_VALUE;
		for (int i = 0; i < generations; i++) {
			int temp = 0;
			if((temp=pop.bestScore())>bestScore) {
				best = pop.best();
				bestScore = temp;
			}	
			pop.score(in,goal);
			pop.kill();
			pop.breed();
	}
		return best;
	}

}
