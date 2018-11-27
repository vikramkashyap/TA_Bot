package ta_bot;

import java.util.ArrayList;

/*
 * Purpose: This holds data on a member that is being evolved in a genetic algorithm
 * 
 * @author Carson Cummins
 * @version 0.0
 */
public class Holder<T extends Member<T>> implements Comparable<Holder<T>>, Cloneable{
protected T myMem;
protected double myScore;
public boolean complete = false;
	public Holder( T mem) {
		myMem = mem;
	}
public T getMember(){
	return myMem;
}
public double getScore() {
	return myScore;
}
public ArrayList<Holder<T>> breed(Holder<T> in) {
	ArrayList<Holder<T>> ret = new ArrayList<Holder<T>>();
	T[] p = myMem.breed(in.getMember());
	for (int i = 0; i < p.length; i++) {
		ret.add(new Holder<T>(p[i]));
	}
	return ret;
}
public Holder<T> mutate() {
	Holder<T> clone = clone();
	Holder<T> temp = new Holder<T>(clone.getMember());
	temp.myMem.mutate();
	return temp;
}
@Override
public Holder<T> clone(){
	Holder<T> ret = new Holder<T>(myMem.clone());
	ret.myScore = myScore;
	ret.complete = complete;
	return ret;
}
public int compareTo(Holder<T> in) {
	if (getScore() < in.getScore()) {
		return -1;
	} else if (getScore() > in.getScore()) {
		return 1;
	}
	
	return 0;
}
public void score() {
	myScore = myMem.score();
	
}

}
