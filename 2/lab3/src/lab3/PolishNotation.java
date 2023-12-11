/*package lab3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class PolishNotation {
	private static final Map<Character, Integer> operationPriority;
    static {
        Map<Character, Integer> priorityMap = new HashMap<Character, Integer>();
        priorityMap.put('(', 1);
        priorityMap.put('|', 2);
        priorityMap.put('.', 3); 
        priorityMap.put('*', 4);
        operationPriority = Collections.unmodifiableMap(priorityMap);
    }
	public String turnPolishNotation(String expression) {
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
		return finalString.toString();
	}
	public String infixToPostfix(String regex) {
	        String postfix = new String();

	        Stack<Character> stack = new Stack<Character>();

	        String formattedRegEx = regex;

	        for (Character c : formattedRegEx.toCharArray()) {
	            switch (c) {
	                case '(':
	                    stack.push(c);
	                    break;

	                case ')':
	                    while (!stack.peek().equals('(')) {
	                        postfix += stack.pop();
	                    }
	                    stack.pop();
	                    break;

	                default:
	                    while (stack.size() > 0) {
	                        Character peekedChar = stack.peek();

	                        Integer peekedCharPrecedence = getPriority(peekedChar);
	                        Integer currentCharPrecedence = getPriority(c);

	                        if (peekedCharPrecedence >= currentCharPrecedence) {
	                            postfix += stack.pop();
	                        } else {
	                            break;
	                        }
	                    }
	                    stack.push(c);
	                    break;
	            }

	        }

	        while (stack.size() > 0)
	            postfix += stack.pop();

	        return postfix;
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
*/
