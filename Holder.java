package org.bitenet.brains.genetic;

public class Holder<T extends Member<T>> {
private T myMem;
private int myScore;
	public Holder( T mem) {
		myMem = mem;
	}
public T getMember(){
	return myMem;
}
public int getScore() {
	return myScore;
}
public void setScore(int score) {
	myScore = score;
	
}
}
