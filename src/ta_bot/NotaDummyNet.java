package ta_bot;

public class NotaDummyNet {
	
	//heading update, speed update, turret heading update, fire confidence
	double[] data;
	NeuralNetwork brain;
	public NotaDummyNet() {
		data = new double[]{10.0, 10.0, 10.0, 50.0};
		brain = NeuralNetwork.deserialize(NeuralNetwork.SERIALIZE_FILEPATH);
	}
	
	//current x, y, heading, turretheading, energy, + enemydata
	public double[] forwardProp(double new_x, double new_y, double newheading, double newturretheading, double newenergy, TrackedEnemy[] newEnemyData) {
		double[] enemdat = new double[28];
		int count = 0;
		for (TrackedEnemy e : newEnemyData){
			enemdat[count+0] = e.currentX;
			enemdat[count+1] = e.currentY;
			enemdat[count+2] = e.enemyVelocity;
			enemdat[count+3] = e.enemyHeading;
			count+=4;
		}
		double[] indat = new double[33];
		for (int i=0; i<28; i++){
			indat[i] = enemdat[i];
		}
		indat[28] = new_x;
		indat[29] = new_y;
		indat[30] = newheading;
		indat[31] = newturretheading;
		indat[32]=newenergy;
		return brain.calculate(indat);
	}

}
