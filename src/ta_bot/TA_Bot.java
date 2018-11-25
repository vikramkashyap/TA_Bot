//TABOT made by 2nd period java TA's
// 11/24/18
//this will hopefully work

package ta_bot;
//Imports 
import robocode.*;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
public class TA_Bot extends AdvancedRobot {
	//Variables
	
	//Set up enemies that we will track
	int maxEnemiesTracked = 8;
	String[] currentEnemies = new String[maxEnemiesTracked];
	TrackedEnemy[] enemyData = new TrackedEnemy[maxEnemiesTracked];
	int numSavedEnemies = 0;
	NotaDummyNet net = new NotaDummyNet();
	
	//heading update, speed update, turret heading update, fire confidence
	double[] data = {0, 0, 0, 0};

	
	// Robot's default behavior
	public void run() {
		// Initialization of the robot
		// Color setup
		setBodyColor(Color.black);
		setGunColor(new Color(15, 0, 0));
		setRadarColor(new Color(15, 0, 0));
		setBulletColor(Color.black);
		setScanColor(new Color(15,0,0));
		// Turn robot, gun, and radar independently
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		
		// Robot main loop
		while (true) {
			setTurnRadarRight(10000);
			System.out.println("heading update: " + data[0]);
			turnRight(data[0]);
			System.out.println("speed update: " + data[1]);
			ahead(data[1]);
			System.out.println("turret heading update: " + data[2]);
			turnGunRight(data[2]);
			System.out.println("fire conf: " + data[3]);
			calculateFire(data[3]);
		}
	}

	// When a robot is scanned by the radar
	public void onScannedRobot(ScannedRobotEvent e) {
		String currentScanned = e.getName();
		boolean createNewEntry = true;
		for(int i = 0; i < numSavedEnemies; i++) {
			if(currentEnemies[i].equals(currentScanned)) {
				enemyData[i].updateEnemy(e, this);
				createNewEntry = false;
				System.out.println("updated entry for enemy: " + e.getName() +" at array position " + i);
				break;
			}
		}
		if(createNewEntry && numSavedEnemies < maxEnemiesTracked) {
			currentEnemies[numSavedEnemies] = e.getName();
			enemyData[numSavedEnemies] = new TrackedEnemy();
			enemyData[numSavedEnemies].updateEnemy(e, this);
			System.out.println("created new entry for enemy: " + e.getName() +" at array position " + numSavedEnemies);
			numSavedEnemies++;
		}
		data = net.forwardProp(this.getX(), this.getY(), this.getHeading(), this.getGunHeading(), this.getEnergy(), enemyData);
	}
	
	public void calculateFire(double fireconfidence) {
		if(fireconfidence < 50.0) {
			return;
		}
		else {
			System.out.println("firing at " + (fireconfidence - 50)/16 + " bullet strength");
			fire((fireconfidence - 50)/16);
		}
	}

	public void onHitRobot(HitRobotEvent e) {

	}
	

	public void onRobotDeath(RobotDeathEvent e) {

	}

	//If robot was hit by a bullet
	public void onHitByBullet(HitByBulletEvent e) {

	}
	
	public void shootEnemy() {
		
	}
}
