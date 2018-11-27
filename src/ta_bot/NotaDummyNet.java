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
		System.out.println("enemies passed to nn " + newEnemyData.length);
		double[] enemdat = new double[32];
		int count = 0;
		for (TrackedEnemy e : newEnemyData){
			enemdat[count+0] = e==null?0:e.currentX;
			enemdat[count+1] = e==null?0:e.currentY;
			enemdat[count+2] = e==null?0:e.enemyVelocity;
			enemdat[count+3] = e==null?0:e.enemyHeading;
			count+=4;
		}
		double[] indat = new double[37];
		for (int i=0; i<32; i++){
			indat[i] = enemdat[i];
		}
		indat[32] = new_x;
		indat[33] = new_y;
		indat[34] = newheading;
		indat[35] = newturretheading;
		indat[36]=newenergy;
		return brain.calculate(indat);
	}

}
