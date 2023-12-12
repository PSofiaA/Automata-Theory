package lab3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NFA {
	 private String origExpression;
	 private String prepExpression;
	 private ExpressionReplace expressionReplacer;
	 private List<Character> alphabet;
	 private List<State> initialState;
	 private List<String> states;
	 private List<State> acceptStates;
	 private List<Transition> transitions;
	 
	 private Stack<State> stackInitialStates;
	 private Stack<State> stackAcceptStates;
	 private static int currentStateID = 0;
	 
	public NFA(String inputExpression) {
		 this.origExpression = inputExpression;
		 this.expressionReplacer = new ExpressionReplace(this.origExpression);
		 this.prepExpression = expressionReplacer.getExpr();
		 this.initialState = new LinkedList<State>();
		 this.states = new LinkedList<String>();
		 this.alphabet = new ArrayList<Character>();
		 this.acceptStates = new LinkedList<State>();
		 this.transitions = new LinkedList<Transition>();
		 this.stackInitialStates = new Stack<State>();
		 this.stackAcceptStates = new Stack<State>();
		 System.out.println(prepExpression);
		 createAlphabet();
		 createNFA();
		 printNFA();
	 }
	 
	 public void createNFA() {
		 State resultAccept;
		 
		 for(int i = 0; i < prepExpression.length(); i++) {
			 char current = prepExpression.charAt(i);
			//Met empty substring - E-leaf
//			 if(current == '$') {
//				 Transition transition = new Transition("$");
//				 State start = transition.getBeginState();
//				 State accept = transition.getFinalState();	 
//				 stackInitialStates.push(start);
//				 stackAcceptStates .push(accept);
//			 }
			 
			 //Met plain symbol a-leaf OR empty substring - E-leaf
			 if(alphabet.contains(current) || current == '$') {
				 State start = new State(currentStateID);
				 State accept = new State(currentStateID);
				 start.addNextState(accept);
				 accept.addPrevState(start);
				 Transition transition = new Transition(Character.toString(current), start.getStateID(), accept.getStateID());
				 transitions.add(transition);
				 stackInitialStates.push(start);
				 stackAcceptStates.push(accept);
				//System.out.println("Cupprent INITIAL state ID " + stackInitialStates.peek().getStateID());
				//System.out.println("Cupprent ACCEPT state ID " + stackAcceptStates.peek().getStateID());
				 //printTransitions();
			 }
			//Met concatenation .-node
			 else if(current == '.') {
				System.out.println("Met concatenation"); 
				for (State st:stackInitialStates) {
					System.out.println("STACK INITIAL state ID " + st.getStateID());
				}
				for (State st:stackAcceptStates) {
					System.out.println("STACK FINAL state ID " + st.getStateID());
				}
				resultAccept = stackAcceptStates.pop(); // final partial automata accepting state
				State toMergeLeft = stackAcceptStates.pop(); 
				toMergeLeft.setAccept(false);
				State looseState = stackInitialStates.pop();
				
				//State resultStart = stackInitialStates.pop();
				System.out.println("resultAccept " + resultAccept.getStateID());
				for (State st:resultAccept.getPreviousStates()) {
					System.out.println("Previous state " + st.getStateID());
				}
				
				int bufID = 0;
				if(resultAccept.getPreviousStates().size() > 0) {
					bufID = resultAccept.getPreviousStates().getLast().getStateID();
					resultAccept.getPreviousStates().removeLast();
				}
				System.out.println("Delete ID " + bufID);	
				State toMergeRight = (resultAccept.getLastPrev() != null) ?	resultAccept.getLastPrev() : resultAccept;
				System.out.println("To merge right " + toMergeRight.getStateID());
				concatenationMerge(toMergeLeft, toMergeRight);
//				toMergeRight.addPrevState(toMergeLeft);
//				toMergeLeft.addNextState(toMergeRight);
//				toMergeRight.getPreviousStates().addAll(toMergeLeft.getPreviousStates());
				
				System.out.println("resultAccept AFTER  " + resultAccept.getStateID());
				for (State st:resultAccept.getPreviousStates()) {
					System.out.println("Previous state " + st.getStateID());
				}
				int toDelete = getTransitionByBeginID(bufID);
				String throwSymbol = this.transitions.get(toDelete).getSymbol();
				this.transitions.remove(toDelete);
				Transition merge = new Transition(throwSymbol, toMergeLeft.getStateID(), toMergeRight.getStateID());
				 transitions.add(merge);
				stackAcceptStates.push(resultAccept);
				printTransitions();
			 }
			//Met pipe |-node
			 else if(current == '|') {
				 
			 }
			//Met Kleene something *-node
			 else if(current == '*') {
				 
			 }
			 
		 }
	 }
	 
	 private void concatenationMerge(State left, State right) {
		 right.addPrevState(left);
		 left.addNextState(right);
		 while(left.getPreviousStates().isEmpty())
	 }
	 
	
	 private void createAlphabet() {
		 for(int i = 0; i < prepExpression.length(); i++) {
			 if(!expressionReplacer.isKey(prepExpression.charAt(i))) {
				 if(!alphabet.contains(prepExpression.charAt(i))) {
					 alphabet.add(prepExpression.charAt(i));
				 }
			 }
		 }
	 }
	 private void printNFA() {
		 System.out.println("Basic regular expression -> " + this.getOrigExpression());
		 System.out.println("Reverse Polish Notation regular expression -> " + this.getPrepExpression());
		 System.out.println("NFA alphabet -> " + this.getAlphabet());
		 printTransitions();
	 }
	 public void printTransitions() {
		 System.out.println("Transition list is");
		 for(Transition tr : transitions){
			 System.out.print("TRANSITION  ");
			 System.out.println("	Symbol is " + tr.getSymbol());
			 System.out.println("	Begin is " + tr.getBeginState());
			 System.out.println("	Fin is " + tr.getFinalState());
			}
		
	 }
	 public String getOrigExpression() { return this.origExpression; }
	 
	 public String getPrepExpression() { return this.prepExpression; }
	 
	 public static int getCurrentStateID() { return currentStateID; }
	 
	 public static void updateCurrentStateID() { NFA.currentStateID++;}
	 
	 public int getTransitionByBeginID(int inputID) {
		 int index = 0;
		 for (Transition tr : this.transitions) {
			 if (tr.getBeginState() == inputID){
				 return index++;
			 }
			 index++;
		 }
		 return -1;
	 }
	 public List<Character> getAlphabet() {
		return alphabet;
	}
}
