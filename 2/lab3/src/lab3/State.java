package lab3;

import java.util.LinkedList;
import java.util.List;

public class State {
	private int stateID;
	private boolean isInitial;
    private boolean isAccept;
	private List<State> nextStates;
	private List<State> previousStates;
	

	public State(int stateID) {
		this.stateID = stateID;
		NFA.updateCurrentStateID();
		this.previousStates = new LinkedList<State>();
		this.nextStates = new LinkedList<State>();
	}
	
	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public boolean isInitial() {
		return isInitial;
	}

	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}

	public boolean isAccept() {
		return isAccept;
	}

	public void setAccept(boolean isAccept) {
		this.isAccept = isAccept;
	}

	public List<State> getNextStates() {
		return nextStates;
	}

	public List<State> getPreviousStates() {
		return previousStates;
	}

	public void addNextState(State nextState) {
		this.nextStates.add(nextState);
	}

	public void addPrevState(State prevState) {
		this.previousStates.add(prevState);
	}
	 
	public void removeNextState() {
		int index = this.nextStates.size() - 1;
		this.nextStates.remove(index);
	}
	public void removePrevState() {
		int index = this.previousStates.size() - 1;
		this.previousStates.remove(index);
	}
	public State getLastPrev() {
		 int index = this.previousStates.size() - 2;
		 if (index >= 0) {
			 return previousStates.get(index);
		 }
		 return null;
	 }
}
