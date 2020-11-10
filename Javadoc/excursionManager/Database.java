package excursionManager;

import java.util.ArrayList;

public class Database {
	public boolean createExcursion(Excursion excursion) {
		return true;
	}
	
	public boolean editExcursion(Excursion excursionNew) {
		return true;
	}
	
	public boolean cancelExcursion(Excursion excursion) {
		return true;
	}
	
	public ArrayList<Excursion> getAllExcursion() {
		ArrayList<Excursion> xyz = new ArrayList<Excursion>();
		return xyz;
	}
	
	public boolean createExcursionEvent(ExcursionEvent excursionEvent) {
		return true;
	}
	
	public ArrayList<ExcursionEvent> getAllExcursionEvents() {
		ArrayList<ExcursionEvent> xyz = new ArrayList<ExcursionEvent>();
		return xyz;
	}
	
	public boolean cancelExcursionEvent(ExcursionEvent excursionEvent) {
		return true;
	}
	public boolean editExcursionEvent(ExcursionEvent excursionNew) {
		return true;
	}
}
