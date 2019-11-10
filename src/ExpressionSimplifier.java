import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ExpressionSimplifier {
	
	private String simplifiedExpression;
	private char[] validOperators = {'+', '-', '*', '/', '%', '^'};
	private boolean simplified = false;
	
	private String expression;
	
	public ExpressionSimplifier(String givenExpression) {
		expression = givenExpression;
	}
	
	public double evaluate() {
		if(!simplified) {
			simplify();
		}
		
		Stack<Double> operands = new Stack<>();
		
		String num = "";
		for(char c : simplifiedExpression.toCharArray()) {
			if(c == ' ') {
				operands.push(Double.parseDouble(num));
				num = "";
			} else if(isNumberPart(c)) {
				num += c;
			} else {
				double num2 = operands.pop();
				double num1 = operands.pop();
				if(c == '+') {
					operands.push(num1 + num2);
				} else if(c == '-') {
					operands.push(num1 - num2);
				} else if(c == '*') {
					operands.push(num1 * num2);
				} else if(c == '/') {
					operands.push(num1 / num2);
				} else if(c == '%') {
					operands.push(num1 % num2);
				} else if(c == '^') {
					operands.push(Math.pow(num1, num2));
				}
			}
		}
		return operands.pop();
	}
	
	public String getSimplifiedExpression() {
		return simplifiedExpression;
	}
	
	public void simplify() {
		Stack<Character> operators = new Stack<>();
		Queue<String> postOrder = new LinkedList<>();
		
		HashMap<Character, Integer> operatorTiers = new HashMap<>();
		operatorTiers.put('+', 1);
		operatorTiers.put('-', 1);
		operatorTiers.put('*', 2);
		operatorTiers.put('/', 2);
		operatorTiers.put('%', 2);
		operatorTiers.put('^', 3);
		operatorTiers.put('(', 0);
		operatorTiers.put(')', 0);
		
		String number = "";
		for(char c : expression.toCharArray()) {
			if(isNumberPart(c)) {
				number += c;
			} else if(isOperator(c)) {
				if(number != "") {
					postOrder.add(number + " ");
					number = "";	
				}
				while(!operators.isEmpty() && operatorTiers.get(operators.peek()) >= operatorTiers.get(c)) {
					String operator = "" + operators.pop();
					postOrder.add(operator);
				}
				operators.push(c);
			} else if(isGrouper(c)) {
				if(c == '(') {
					operators.push(c);
				} else {
					if(number != "") {
						postOrder.add(number + " ");
						number = "";	
					}
					while(operators.peek() != '(') {
						String operator = "" + operators.pop();
						postOrder.add(operator);
					}
					operators.pop();
				}
			}
		}
		if(number != "") {
			postOrder.add(number + " ");
			number = "";	
		}
		while(!operators.isEmpty()) {
			String operator = "" + operators.pop();
			postOrder.add(operator);
		}
		
		simplifiedExpression = "";
		while(!postOrder.isEmpty()) {
			simplifiedExpression += postOrder.poll();
		}
		simplified = true;
	}

	private boolean isNumberPart(char c) {
		if(Character.isDigit(c)) {
			return true;
		} else if(c == '.') {
			return true;
		}
		return false;
	}		
	

	private boolean isOperator(char c) {
		for(char operator : validOperators) {
			if(c == operator) {
				return true;
			}
		}
		return false;
	}

	private boolean isGrouper(char c) {
		if(c == '(' || c == ')') {
			return true;
		}
		return false;
	}
	
}
