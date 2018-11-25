/*Scorer: Tests a robocode bot against samples and returns a score
*/

import robocode.*;
import robocode.control.RobocodeEngine;

public class Scorer extends BattleAdaptor {
	
	BattleCompletedEvent completedevent = null;
	int testBotIndex = -1;

	public Scorer() {
		RobocodeEngine engine = new RobocodeEngine(new File("C:\Robocode"));
		
		engine.addBattleListener(this);
		
		engine.setVisible(true);
		
		int numBattles = 5;
		BattlefieldSpecification fieldSpec = new BattlefieldSpecification(800, 600);
		RobotSpecification[] robots = engine.getLocalRepository("sample.Wall,sample.Crazy,sample.RamFire,sample.TrackFire");	//Add the path of our bot
		BattleSpecification battleSpec = new BattleSpecification(numbattles, fieldSpec, robots);
	}
	
	/*Runs a battle with given robot against examples and returns score of bot
	 */
	public int score() {
		engine.runBattle(battleSpec, true);	//Runs battle and waits for finish, so should trigger event to overwrite lastscore
		
		while (completedevent == null);	//Wait

		int score = completedevent.getIndexedResults()[testBotIndex].getScore();

		completedevent = null;
		testBotIndex = -1;

		return score;
	}

	public void onRoundStarted(RoundStartedEvent e) {
		IRobotSnapshot[] robots = e.getStartSnapshot().getRobots();
		for (IRobotSnapshot s : robots) {
			if (s.getName().equals("TABot"))	//Replace with full name
				testBotIndex = s.getRobotIndex();
		}
	}

	public void onBattleCompleted(BattleCompletedEvent e) {
		completedevent = e;
	}
}
