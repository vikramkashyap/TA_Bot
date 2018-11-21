package ta_bot;

public class DummyNet {
	
	//heading update, speed update, turret heading update, fire confidence
	double[] data;
	
	public DummyNet() {
		data = new double[]{10.0, 10.0, 10.0, 50.0};
	}
	
	//current x, y, heading, turretheading, energy, + enemydata
	public double[] forwardProp(double new_x, double new_y, double newheading, double newturretheading, double newenergy, TrackedEnemy[] newEnemyData) {
		return data;
	}

}
