
import java.util.ArrayList;

public class Population<T extends Member<T>> {
ArrayList<Holder<T>> pop = new ArrayList<Holder<T>>();
	public Population(int inSize, int outSize,int size, T parent) {
		for (int i = 0; i < size; i++) {
			pop.add(new Holder<T>(parent.random(inSize, outSize)));
		}
	}

	public void score(ArrayList<ArrayList<Float>> in, ArrayList<ArrayList<Float>> goal) {
		for (int i = 0; i < pop.size(); i++) {
			pop.get(i).setScore(pop.get(i).getMember().score(in,goal));
		}
		normalize();
	}

	private void normalize() {
	//get standard deviation of scores
		//get average
		int sum = 0;
		for (int i = 0; i < pop.size(); i++) {
			sum+=pop.get(i).getScore();
		}
		double avg = sum/pop.size();
		double[] dists = new double[pop.size()];
		for (int i = 0; i < dists.length; i++) {
			dists[i] = Math.abs(pop.get(i).getScore()-avg);
		}
		//find average distances
		sum = 0;
		for (int i = 0; i < dists.length; i++) {
			sum+=dists[i];
		}
		double stddev = sum/dists.length;
	//set all scores equal to std devs from mean
		for (int i = 0; i < dists.length; i++) {
			pop.get(i).setScore((int)Math.round((pop.get(i).getScore()-avg)/stddev));
		}
	//make all scores positive
		int bs = bestScore();
		for (int i = 0; i < dists.length; i++) {
			pop.get(i).setScore(pop.get(i).getScore()+bs);
		}
	}

	public void kill() {
		int bst = bestScore();
		ArrayList<Holder<T>> newPop = new ArrayList<Holder<T>>();
		for (int i = 0; i < pop.size(); i++) {
			if((pop.get(i).getScore()/bst)>Math.random()) {
				newPop.add(pop.get(i));
			}
		}
		pop = newPop;
		
	}

	public void breed() {
		ArrayList<Holder<T>> newPop = new ArrayList<Holder<T>>();
		for (int i = 0; i < pop.size(); i+=2) {
			if(i+1>pop.size()) {
			break;	
			}else {
				//add two new children
				newPop.add(new Holder<T>(pop.get(i).getMember().breed(pop.get(i+1).getMember())));
				newPop.add(new Holder<T>(pop.get(i).getMember().breed(pop.get(i+1).getMember())));
			}
		}
		pop = newPop;
	}

	public int bestScore() {
		int ret = -Integer.MAX_VALUE;
		for (int i = 0; i < pop.size(); i++) {
			if(pop.get(i).getScore()>ret) {
				ret = pop.get(i).getScore();
			}
		}
		return ret;
	}

	public T best() {
		int best = -Integer.MAX_VALUE;
		T ret=null;
		for (int i = 0; i < pop.size(); i++) {
			if(pop.get(i).getScore()>best) {
				best = pop.get(i).getScore();
			ret = pop.get(i).getMember();
			}
		}
		return ret;
	}

}
