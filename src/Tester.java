import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("Enter an expression to solve.");
			String expression = scan.nextLine();
			ExpressionSimplifier es = new ExpressionSimplifier(expression);
			System.out.println(es.evaluate());
		}
		
	}
	
}
