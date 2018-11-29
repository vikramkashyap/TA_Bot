package ta_bot;

import java.io.Serializable;

//Nathan Purwosumarto - August 2017

import java.util.Random;

public class Neuron implements Serializable{
	private static final long serialVersionUID = 12345L;
	
	static Random random = new Random();
	
	int numInputs;
	final double rate = 0.001;
	
	double[] inputs;
	double[] weights;
	double lastOutput;
	double[] previousWeightDeltas;
	double biasWeight = -1;
	double momentumWeight = 0;
	
	public void mutate(){
		for (int i =0; i<weights.length; i++){
			if(Math.random()<.04)inputs[i]=inputs[i]+10*(Math.random()-.5);
		}
		if(Math.random()<.4)biasWeight+=10*(Math.random()-.5);
	}
	public Neuron(int newInputs){
		numInputs = newInputs;
		inputs = new double[numInputs];
		weights = new double[numInputs];
		previousWeightDeltas = new double[numInputs];
		//initializes random weights between -2.0 to 2.0
		for(int i = 0; i < numInputs; i++){
			weights[i] = 10*(2 * random.nextDouble() - 1);
		}
		biasWeight = 10*(2*random.nextDouble() -1);
	}
	
	public void setInputs(double[] data){
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = data[i];
			
		}
	}
	
	public double calculateOutput(){
		double total = 0;
		for(int i = 0; i < numInputs; i++){
			total += inputs[i] * weights[i];
		}
		total += 1 * biasWeight;
		total = sigmoidFunction(total);
		lastOutput = total;
		
		return total;
	}
	
	public double updateErrorLastLayer(double trueValue, double error){
		double residual = trueValue - lastOutput;
		error += residual * (lastOutput * (1 - lastOutput));
		return error;
		
		
	}
	
	
	public double calculateResidual(double truevalue){
		return truevalue - lastOutput;
	}
	
	public double[] updateWeights(double contribution){
		double error = contribution * (lastOutput * (1 - lastOutput));
		
		//calculate previous layer contribution
		double[] previousLayerContribution = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			previousLayerContribution[i] = weights[i];
		}
		for (int i = 0; i < previousLayerContribution.length; i++) {
			previousLayerContribution[i] *= error;
		}
		
		//update actual weights
		for(int i = 0; i < weights.length; i++){
			weights[i] += (rate * error * inputs[i]) + (previousWeightDeltas[i] * momentumWeight);
			previousWeightDeltas[i] = rate * error * inputs[i];
		}
		biasWeight += rate * error;
		
		return previousLayerContribution;
		
	}
	public Neuron clone(){
		Neuron n = new Neuron(numInputs);
		n.weights = weights;
		n.biasWeight=biasWeight;
		n.momentumWeight=momentumWeight;
		n.inputs = inputs;
		n.lastOutput=lastOutput;
		n.previousWeightDeltas=previousWeightDeltas;
		return n;
	}
	public double sigmoidFunction(double x){
		return (1/ (1 + Math.exp(-x)));
	}
	
	public void printWeights(){
		for (int i = 0; i < weights.length; i++) {
			System.out.println(weights[i]);
		}
	}
		

}