//Nathan Purwosumarto - August 2017

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork implements Member<NeuralNetwork>{
	
	final Random random = new Random();
	final int rawInputs = 3;
	int[] neuronsInLayer = {32, 16, 8, 4};
	ArrayList<Neuron[]> layers = new ArrayList<>();
	
	
	
//	final int trainingSetSize = 100;
//	final int trainingMax = 100;
//	double[][] trainingData = new double[trainingSetSize][rawInputs];
//	double[][] trainingAnswers = new double[trainingSetSize][neuronsInLayer[1]];

	public NeuralNetwork[] breed(NeuralNetwork p){

	}
	public score(){

	}
	public NeuralNetwork random(){

	}
	public void mutate(){
		Neuron[][] me = vals();
		for (Neuron[] layer : me){
			for (Neuron n : me){
				if (Math.random()<.031)n.mutate();
			}
		}

	}
	public void print(){
		displayWeights();
	}
	@Override
	public NeuralNetwork clone(){
		return new NeuralNetwork(vals());
	}
	private Neuron[][] vals(){

	}
	private NeuralNetwork(Neuron[][] ners){
		for (Neuron[] layerog: ners){
			Neuron[] layer = new Neuron[layerog.length];
			for (int i = 0; i<layerog.length; i++){
				layer[i] = layerog[i].clone();
			}
			layers.add(layer);
		}
	}
	public NeuralNetwork(){
		
		//populate layers of neurons
		for(int i = 0; i < neuronsInLayer.length; i++){
			Neuron[] layer = new Neuron[neuronsInLayer[i]];
			int inputsPerLayer = (i == 0) ? rawInputs : neuronsInLayer[i - 1];
			for(int j = 0; j < neuronsInLayer[i]; j++){
				layer[j] = new Neuron(inputsPerLayer);
			}
			layers.add(layer);
		}
		//displayWeights();
	}
	
	
	public void run(double[] input){
		calculate(input);
		displayResult(true);
	}
	
	public void train(int trainingIndex){
		int lastLayer = layers.size() - 1;
		double[][] lastLayerContribution = new double[layers.get(lastLayer).length][];
		for (int i = 0; i < layers.get(lastLayer).length; i++) {
			Neuron n = layers.get(lastLayer)[i];
			double[] currentSet = trainingData[trainingIndex];
			//System.out.println(currentSet[0] + " " + currentSet[1] + " " + currentSet[2]);
			double[] resultArray = calculate(currentSet);
			double[] correctAnswers = trainingAnswers[trainingIndex];
			lastLayerContribution[i] = n.updateWeights(n.calculateResidual(correctAnswers[i]));
		}
		backpropagate(lastLayer-1, lastLayerContribution);
	}
	
	public void backpropagate(int currentLayer, double[][] layerContribution){
		if(currentLayer < 0){
			return;
		}
		//update weights on other layers using contribution from previous layer
		else{
			double[][] lastLayerContribution = new double[layers.get(currentLayer).length][];
			for (int i = 0; i < layers.get(currentLayer).length; i++) {
				Neuron n = layers.get(currentLayer)[i];
				double totalContribution = 0;
				for (int j = 0; j < layerContribution.length; j++) {
					totalContribution += layerContribution[j][i];
				}
				lastLayerContribution[i] = n.updateWeights(totalContribution);
			}
			backpropagate(currentLayer-1, lastLayerContribution);
		}
		
	}
	
	public double[] calculate(double[] data){
		double[] layerOutput = null;
		for(int i = 0; i < layers.size(); i++){
			for(int j = 0; j < layers.get(i).length; j++){
				Neuron n = layers.get(i)[j];
				//pass raw inputs into first layer of neurons
				if(i == 0){
					n.setInputs(data);
				}
				//pass previous layer's output into subsequent layers
				else{
					n.setInputs(layerOutput);
				}
			}
			layerOutput = new double[layers.get(i).length];
			for(int j = 0; j < layers.get(i).length; j++){
				layerOutput[j] = layers.get(i)[j].calculateOutput();
			}
		}
		return layerOutput;
	}
	
	public void displayResult(boolean formatted){
		int lastLayer = layers.size() - 1;
		System.out.println("Last Layer Results: ");
		if(formatted) System.out.println("(formatted output)");
		for (int i = 0; i < layers.get(lastLayer).length; i++) {
			Neuron n = layers.get(lastLayer)[i];
			if(formatted){
				System.out.println("Neuron " + i + ": " + Math.round(n.lastOutput*1000000)/10000d);
			}
			else{
				System.out.println("Neuron " + i + ": " + n.lastOutput);
			}
		}
		System.out.println();
	}
	
	public void displayWeights(){
		for (int i = 0; i < layers.size(); i++) {
			for (int j = 0; j < layers.get(i).length; j++) {
				System.out.println("Neuron " + i + "-" + j +": ");
				layers.get(i)[j].printWeights();
				
			}
			System.out.println();
		}
	}
	
	public int getNumInputs(){
		return rawInputs;
	}

}