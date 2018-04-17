package calculator;
import java.io.Console;
import java.util.Scanner;
public class Calculator {
	public static void main(String[] args) {
		boolean isReal;
		int action,scalar;
		Polynomial pl1,pl2;
		String firstInput,secondInput;
		Scanner sc = new Scanner(System.in);
		boolean toExit = false;
		System.out.println("Welcome to the polynomial calculator." ); 
		System.out.println("Please select the scalar field" );
		System.out.println("Rational (Q) or Real (R)" );
		//System.out.println("\f");//clear the Console;
		String field = sc.nextLine();
		if(field == "R")
			isReal = true;
		else
			isReal = false;
		while(!toExit){
			System.out.println("Please select an operation:");
			//	System.out.println("Please select an operation:" + "\\n" + "1.Addition"+ "\\n" + "2.Multiplication");
			System.out.println("1.Addition");
			System.out.println("2.Multiplication");
			System.out.println("3.Evaluation");
			System.out.println("4.Derivate");
			System.out.println("5.Exit");
			action = sc.nextInt();
			switch( action) {
			case 1:
				System.out.println("you have selected addition");
				System.out.println("Please insert the first polynomial");
				firstInput = sc.nextLine();
				pl1 = new Polynomial (firstInput,isReal);
				System.out.println("Please insert the second polynomial");
				secondInput =sc.nextLine();
				pl2 = new Polynomial (secondInput,isReal);
				System.out.println("the solution is:" + pl1.add(pl2).toString());
				break;
			case 2:
				System.out.println("you have selected addition");
				System.out.println("Please insert the first polynomial");
				firstInput = sc.nextLine();
			    pl1 = new Polynomial (firstInput,isReal);
				System.out.println("Please insert the second polynomial");
				secondInput =sc.nextLine();
				pl2= new Polynomial (secondInput,isReal);
				System.out.println("the solution is:" + pl1.mul(pl2).toString());
				break;
			case 3:
				System.out.println("you have selected evaluation"); //not finished case
				System.out.println("Please insert the  polynomial");
				firstInput = sc.nextLine();
			    pl1 = new Polynomial (firstInput,isReal);
				System.out.println("Please insert the scalar");	
				secondInput = sc.nextLine();
				if(isReal)
					System.out.println("the solution is:" + pl1.evaluate(new RealScalar(secondInput)).toString());	  
				else
					System.out.println("the solution is:" + pl1.evaluate(new RationalScalar(secondInput)).toString());
				break;
			case 4:
				System.out.println("you have selected Derivate");
				System.out.println("Please insert the polynomial");
				firstInput = sc.nextLine();
			    pl1 = new Polynomial (firstInput,isReal);
				System.out.println("The derivative polynomial is:" + pl1.derivate().toString());
				break;
			case 5:
				System.out.println("you chose to exit");
				toExit = true;
				break;
			default:
				System.out.println("you entered ilegal value try again");
				break;
			} 
		}
	}

}

