package dialogAgent;

import java.util.ArrayList;
import java.util.HashMap;
import excursionManager.MultiMap;
import excursionManager.ExcursionController;

public class ExcursionAgent {
	
	private static ExcursionController excursionController = new ExcursionController();
	
	public MultiMap<String, String> viewALLExcursion(){
		return excursionController.viewAllExcursion();
	}

	
	public ArrayList<Integer> getExcursionIDs(){
		return excursionController.getExcursionIDs();
	}

}
