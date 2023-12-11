package lab3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * This class replaces + [ ] {} operations with combinations of * | . or $ + creates postfix notation
 */
public class ExpressionReplace {
	private String expression;
	
	private static final Map<Character, Integer> operationPriority;
    static {
        Map<Character, Integer> priorityMap = new HashMap<Character, Integer>();
        priorityMap.put('(', 1);
        priorityMap.put('|', 2);
        priorityMap.put('.', 3); 
        priorityMap.put('*', 4);
        operationPriority = Collections.unmodifiableMap(priorityMap);
    }
    
	public ExpressionReplace(String expression) {
		this.expression = expression;
		replaceRange();
		replaceRepetitionOperator();
		replaceKleenePlus();
		replacePrognosticOperator();
		replaceConcatenation(); // LAST ONE CALLED - NOTE OPTIMISE 
		turnPolishNotation();
	}

	private void replaceConcatenation() {
		StringBuilder addConcatenation = new StringBuilder();
		for (int i = 0; i < expression.length(); ++i) {
			if (checkLeftSymbol(expression.charAt(i)) && (i+1 < expression.length()) && checkRightSymbol(expression.charAt(i+1)) ) {
				addConcatenation.append(expression.charAt(i));
				addConcatenation.append('.');
			}
			else 
				addConcatenation.append(expression.charAt(i));
		}
		this.expression = addConcatenation.toString();
	}
	private boolean checkLeftSymbol(char toCheck) {
		return ( checkIfSymbol(toCheck) || toCheck == ')' ||  toCheck == '*' );
	}
	private boolean checkRightSymbol(char toCheck) {
		return (checkIfSymbol(toCheck) || toCheck == '(');
	}
	
	private boolean checkIfSymbol(char toCheck) {
		return (Character.isLetter(toCheck) || Character.isDigit(toCheck) || toCheck == '$');
	}
	private void replaceKleenePlus() {
		for (int i = 0; i < this.expression.length(); i++) {
			if (expression.charAt(i) == '+') {
				if(!(expression.charAt(i-1) == ')')) {
					String operand = Character.toString(expression.charAt(i-1));
					operand = operand + operand + "*";
					String leftPart = expression.substring(0, i-1); 
					String rightPart = expression.substring(i+1); 
					
					expression = leftPart + operand + rightPart;
				} else {
					int openBracket = 0;
					for (int j = (i-1); j >= 0; j--) {
						if(expression.charAt(j) == '(') { 
							String operand = expression.substring(j, i); 
							operand = operand + operand + "*";
							String leftPart = expression.substring(0, j); 
							String rightPart = expression.substring(i+1);
							
							expression = leftPart + operand + rightPart;
							break;
						  } 
					   }
					}
				}
			}
		}
	private void replacePrognosticOperator() {
		// TODO Auto-generated method stub
		
	}

	private void replaceRange() {
		for (int i = 0; i < this.expression.length(); i++) {
			if (expression.charAt(i) == '[') {
				for (int j = i; j < this.expression.length(); j++ ) {
					if (expression.charAt(j) == ']') {
						 StringBuilder operator = new StringBuilder();
						 operator.append('(');
						 for(char k = expression.charAt(i+1); k < expression.charAt(j-1); k++) {
							 operator.append(k);
							 operator.append('|');
						 }
						 operator.append(expression.charAt(j-1));
						 operator.append(')');
						 String leftPart = expression.substring(0, i); 
						 String rightPart = expression.substring(j+1); 
						 expression = leftPart + operator.toString() +  rightPart;
						 break;
				}
			}
		}
	}
}

	private void replaceRepetitionOperator() {
		// TODO Auto-generated method stub
	}

	

	public String getExpr() {
		return this.expression;
	}
	
	public void turnPolishNotation() {
		StringBuffer finalString = new StringBuffer();
		Stack<Character> operators = new Stack<Character>();
		for (int i = 0; i < expression.length(); i++){
			char current = expression.charAt(i);
			if(!isOperator(current)) {
				finalString.append(current);
			}
			else if (current == '(') {
				operators.push(current);
			}
			else if (current == ')') {
				while(operators.size()>0 && !operators.peek().equals('(')) {
					finalString.append(operators.pop());
				}
				operators.pop();
			}
			else if (operationPriority.containsKey(current)){
				while(operators.size() > 0 && getPriority(operators.peek()) >= getPriority(current)) {
						finalString.append(operators.pop());
				}
				operators.push(current);
			}
		}
		while(operators.size() > 0) {
			finalString.append(operators.pop());
		}
		this.expression = finalString.toString();
	}
	private boolean isOperator(char toCheck) {
		return (toCheck == '(' || toCheck == ')' || toCheck == '|' || toCheck == '.' || toCheck == '*');
	}
	private boolean isEmptySubstring(char toCheck) {
		return (toCheck == '$');
	}
	public boolean isKey(char toCheck) {
		return (isOperator(toCheck) || isEmptySubstring(toCheck));
	}
	private Integer getPriority(char toEstimate) {
		Integer priority = operationPriority.get(toEstimate);
		return priority == null ? 5 : priority;
	}
}
