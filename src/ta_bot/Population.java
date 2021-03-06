package ta_bot;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
/*
 * Purpose: This handles the implementation of a genetic algorithm
 * 
 * @author Carson Cummins
 * @version 0.0
 */
public class Population <T extends Member<T>> implements ActionListener{

	/** The size of the tournament. */
	boolean engine_vis;
	private static final int TOURNAMENT_SIZE = 3;
	private static final Random rand = new Random(System.currentTimeMillis());
	private float elitism;
	private float mutation;
	private float crossover;
	JFrame f;
	private ArrayList<Holder<T>> popArr;
	public Population(int size, float crossoverRatio, float elitismRatio, float mutationRatio, T parent) {
		f = new JFrame("evolution handler");
		f.setLayout(new GridLayout(1,1));
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton jb = new JButton("set visible \n (this action can't be undone)");
		f.add(jb);
		jb.addActionListener(this);
		f.setVisible(true);
		this.crossover = crossoverRatio;
		this.elitism = elitismRatio;
		this.mutation = mutationRatio;
		// Generate an initial population
		System.out.println("beginning initial population creation");
		this.popArr = new ArrayList<Holder<T>>();
		for (int i = 0; i < size; i++) {
			this.popArr.add(new Holder<T>(parent.random()));
		}
		for(Holder<T> kk : this.popArr) {
			System.out.println(kk);
			System.out.println(kk.myMem);
		}
		popArr = sort(popArr);
	}

	/**
	 * Method used to evolve the population.
	 */
	public void evolve() {
		// Create a buffer for the new generation
		ArrayList<Holder<T>> buffer = new ArrayList<Holder<T>>();
		for (int i = 0; i < popArr.size(); i++) {
			buffer.add(null);
		}
		// Copy over a portion of the population unchanged, based on 
		// the elitism ratio.
		int idx = Math.round(popArr.size() * elitism);
		for (int i = 0; i < idx+1; i++) {
			buffer.set(i, popArr.get(i));
		}
		// Iterate over the remainder of the population and evolve as 
		// appropriate.
		while (idx < buffer.size()) {
			// Check to see if we should perform a crossover. 
			if (rand.nextFloat() <= crossover) {
				
				// Select the parents and mate to get their children
				ArrayList<Holder<T>> parents = selectParents();
				ArrayList<Holder<T>> children = parents.get(0).breed(parents.get(1));
				
				// Check to see if the first child should be mutated.
				if (rand.nextFloat() <= mutation) {
					buffer.set(idx++,children.get(0).mutate());
				} else {
					buffer.set(idx++,children.get(0));
				}
				
				// Repeat for the second child, if there is room.
				if (idx < buffer.size()) {
					if (rand.nextFloat() <= mutation) {
						buffer.set(idx,children.get(1).mutate());
					} else {
						buffer.set(idx,children.get(1));
					}
				}
			} else { // No crossover, so copy verbatium.
				// Determine if mutation should occur.
				if (rand.nextFloat() <= mutation) {
					buffer.set(idx,popArr.get(idx).mutate());
				} else {
					buffer.set(idx,popArr.get(idx));
				}
			}
			
			// Increase counter
			++idx;
		}
		// Sort the buffer based on fitness.
		buffer = sort(buffer);
		// Reset the population
		popArr = buffer;
		System.out.println("1 layer of evolution is complete");
	}
	

	private ArrayList<Holder<T>> sort(ArrayList<Holder<T>> in) {
		ArrayList<Holder<T>> ret = in;
		//score the current population
		for (int i = 0; i < ret.size(); i++) {
			ret.get(i).score(engine_vis);
		}
		Collections.sort(ret);
		return ret;
	}

	public ArrayList<Holder<T>> getPopulation() {
		ArrayList<Holder<T>> popret = new ArrayList<>();
		for (Holder<T> t : popArr){
			popret.add((Holder<T>)t.clone());
		}
		return popret;
	}
	

	public float getElitism() {
		return elitism;
	}

	
	public float getCrossover() {
		return crossover;
	}

	
	public float getMutation() {
		return mutation;
	}

	private ArrayList<Holder<T>> selectParents() {
		ArrayList<Holder<T>> parents = new ArrayList<Holder<T>>();
for (int i = 0; i < 2; i++) {
	parents.add(null);
}
		// Randomly select two parents via tournament selection.
		for (int i = 0; i < 2; i++) {
			parents.set(i, popArr.get(rand.nextInt(popArr.size())));
			for (int j = 0; j < TOURNAMENT_SIZE; j++) {
				int idx = rand.nextInt(popArr.size());
				if (popArr.get(idx).compareTo(parents.get(i)) < 0) {
					parents.set(i,popArr.get(idx));
				}
			}
		}
		
		return parents;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		engine_vis = !engine_vis;
		
	}
}