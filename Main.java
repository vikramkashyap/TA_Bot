import java.util.Scanner;

public class Main {
	
	int iter = 0;
	int trainingIndex = 0;
	Scanner scan = new Scanner(System.in);
	String input = "placeholder";
	NeuralNetwork net;
	
	public Main() {
		net = new NeuralNetwork();
		double[] data = new double[net.getNumInputs()];
		
		while(iter <= 8000000){
			net.train(trainingIndex);
			if(iter % 100000 == 0){
				System.out.println("ITERATION: " + iter);
				net.displayWeights();
				net.displayResult(false);
			}
			iter++;
			trainingIndex++;
			if(trainingIndex == net.trainingData.length){
				trainingIndex = 0;
			}
		}
		
		while(true){
			System.out.println();
			for(int i = 0; i < data.length; i++){
				System.out.print("Enter data point " + (i+1) + ": ");
				input = scan.nextLine();
				try{
					data[i] = Double.parseDouble(input);
					System.out.println(data[i]);
				}
				catch (Exception e){
					System.out.println("Enter a double!");
					i--;
				}
			}
			net.run(data);
		}	
	}

	public static void main(String[] args) {
		new Main();

	}

}
