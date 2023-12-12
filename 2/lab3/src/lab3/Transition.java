package lab3;

public class Transition {
	private int beginState;
    private int finalState;
    private String symbol;
    
//	public Transition(String inputSymbol ) {
//		this.symbol = inputSymbol;
//        this.beginState = new State(NFA.getCurrentStateID());
//        this.finalState = new State(NFA.getCurrentStateID());
//        this.beginState.addNextState(this.finalState);
//        this.finalState.addPrevState(this.beginState);
//	}
	public Transition(String inputSymbol, int from, int to) {
		this.symbol = inputSymbol;
        this.beginState = from;
        this.finalState = to;
	}

	public int getBeginState() {
		return beginState;
	}

	public void setBeginState(int initialState) {
		this.beginState = initialState;
	}

	public int getFinalState() {
		return finalState;
	}

	public void setFinalState(int acceptState) {
		this.finalState = acceptState;
	}
	public String getSymbol() {
		return symbol;
	}
	
}	
