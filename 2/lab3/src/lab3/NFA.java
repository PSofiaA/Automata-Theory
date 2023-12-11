package lab3;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class NFA {
	 private String origExpression;
	 private String prepExpression;
	 private ExpressionReplace expressionReplacer;
	 private List<State> initialState;
	 private List<String> states;
	 private List<Character> alphabet;
	 private List<State> acceptStates;
	 private List<Transition> transitions;
	 
	 //private State saveFinal;
	 private Stack<State> stackInitialStates;
	 private Stack<State> stackAcceptStates;
	 private static int stateID = 0;
	 
	 public NFA(String inputExpression) {
		 this.origExpression = inputExpression;
		 this.expressionReplacer = new ExpressionReplace(this.origExpression);
		 this.prepExpression = expressionReplacer.getExpr();
//		 this.prepExpression = expressionRearranger.toPostfix(expressionHandler.getExpr());
		 this.initialState = new LinkedList<State>();
		 this.states = new LinkedList<String>();
		 this.alphabet = new LinkedList<Character>();
		 this.acceptStates = new LinkedList<State>();
		 this.transitions = new LinkedList<Transition>();
		 createAlphabet();
	 }
	 public String getPrepExpression() { 
		 return this.prepExpression;
	 }
	 public void createNFA() {
		 for(int i = 0; i < prepExpression.length(); i++) {
			 char current = prepExpression.charAt(i);
			//Met empty substring - E-leaf
			 if(current == '$') {
				 Transition transition = new Transition("$");
				 State start = transition.getStart();
				 State accept = transition.getAccept();
				 
				 stackInitialStates.push(start);
				 stackAcceptStates.push(accept);
			 }
			 //Met plain symbol a-leaf
			 else if(alphabet.contains(current)) {
				 Transition transition = new Transition(Character.toString(current));
				 State start = transition.getStart();
				 State accept = transition.getAccept();
				 
				 stackInitialStates.push(start);
				 stackAcceptStates.push(accept);
			 }
			//Met concatenation .-node
			 else if(current == '.') {
				 
			 }
			//Met pipe |-node
			 else if(current == '|') {
				 
			 }
			//Met Kleene something *-node
			 else if(current == '*') {
				 
			 }
			 
		 }
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
	 public void testPosfix() {
		 System.out.println("Replaced " + this.expressionReplacer.getExpr());
		 System.out.println("Reverse Polish Notation " + this.prepExpression);
	 }
}
