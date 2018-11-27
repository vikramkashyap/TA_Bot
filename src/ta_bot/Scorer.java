package ta_bot;
/*Scorer: Tests a robocode bot against samples and returns a score
*/

import java.io.File;

import robocode.control.*;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.snapshot.IRobotSnapshot;

public class Scorer extends BattleAdaptor {
	
	BattleCompletedEvent completedevent = null;
	int testBotIndex = -1;
	RobocodeEngine engine;
	BattleSpecification battleSpec;
	public Scorer() {
		engine = new RobocodeEngine(new File("C:/Robocode"));
		
		engine.addBattleListener(this);
		
		engine.setVisible(false);
		
		int numBattles = 5;
		BattlefieldSpecification fieldSpec = new BattlefieldSpecification(800, 600);
		RobotSpecification[] robots = engine.getLocalRepository("sample.Walls,sample.Crazy,sample.RamFire,sample.TrackFire,ta_bot.TA_Bot");	//Add the path of our bot
		battleSpec = new BattleSpecification(numBattles, fieldSpec, robots);
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
			if (s.getName().equals("sample.Walls"))	//Replace with full name
				testBotIndex = s.getRobotIndex();
		}
	}

	public void onBattleCompleted(BattleCompletedEvent e) {
		completedevent = e;
	}
}
