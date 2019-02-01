package Board;

import Agents.A_star;
import Agents.Agent;
import Agents.Aggressive;
import Agents.GreedySearch;
import Agents.Real_time_A_star;
import Agents.completely_passive;
import Agents.nearly_pacifist;

public class AgentMaker {
	public Agent create(String name)
	{
		// {"Human","completely passive","aggressive","nearly pacifist","greedy agent","A* agent","real-time A*"}
		if(name.equals("completely passive"))
			return new completely_passive();
		else if(name.equals("aggressive"))
			return new Aggressive();
		else if(name.equals("nearly pacifist"))
			return new nearly_pacifist();
		else if(name.equals("greedy agent"))
			return new GreedySearch();
		else if(name.equals("A* agent"))
			return new A_star();
		else if(name.equals("real-time A*"))
			return new Real_time_A_star();
		return null;
	}
}
