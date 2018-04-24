package calculator;
import polynom.*;
import scalar.*;
import java.io.Console;
import java.util.Scanner;
public class Calculator {
	public static void main(String[] args) {
		boolean isReal;
		String action,scalar;
		Polynomial pl1,pl2;
		String firstInput,secondInput;
		Scanner sc = new Scanner(System.in);
		boolean toExit = false;

		System.out.println("Welcome to the polynomial calculator." ); 
		System.out.println("Please select the scalar field" );
		System.out.println("Rational (Q) or Real (R)" );
		String field = sc.nextLine();
		while(!field.equals("R") & !field.equals("Q")){
			System.out.println("Please insert valid input : Q for Rational R for Real ");
			field = sc.nextLine();
		}
		if(field.equals("R"))
			isReal = true;
		else
			isReal = false;	

		while(!toExit){
			System.out.println("Please select an operation:");
			System.out.println("1.Addition");
			System.out.println("2.Multiplication");
			System.out.println("3.Evaluation");
			System.out.println("4.Derivate");
			System.out.println("5.Exit");


			action = sc.nextLine();
			switch(action) {
			case "1":
				System.out.println("You have selected: Addition");
				System.out.println("Please insert the first polynomial");
				try{
					firstInput = sc.nextLine().toLowerCase();
					pl1 = new Polynomial (firstInput,isReal);
					System.out.println("Please insert the second polynomial");
					secondInput =sc.nextLine().toLowerCase();
					pl2 = new Polynomial (secondInput,isReal);
					System.out.println("The solution is:\n" + pl1.add(pl2).toString());
				}
				catch(Exception e) {
					System.out.println("You inserted invalid polynomial");
				}
				break;
			case "2":
				System.out.println("You have selected: Multiplication");
				System.out.println("Please insert the first polynomial");
			//	try {
					firstInput = sc.nextLine().toLowerCase();
					pl1 = new Polynomial (firstInput,isReal);
					System.out.println("Please insert the second polynomial");
					secondInput =sc.nextLine().toLowerCase();
					pl2= new Polynomial (secondInput,isReal);
					System.out.println("The solution is:\n" + pl1.mul(pl2).toString());
			//	}
			//	catch(Exception e) {
			//		System.out.println("You inserted invalid polynomial");
			//	}
				break;
			case "3":
				System.out.println("You have selected: Evaluation"); 
				System.out.println("Please insert the  polynomial");
				try {
					firstInput = sc.nextLine().toLowerCase();
					pl1 = new Polynomial (firstInput,isReal);
					System.out.println("Please insert the scalar");	
					secondInput = sc.nextLine().toLowerCase();
					try {
						if(isReal)
							System.out.println("The solution is:\n" + pl1.evaluate(new RealScalar(secondInput)).toString());	  
						else
							System.out.println("The solution is:\n" + pl1.evaluate(new RationalScalar(secondInput)).toString());
					}
					catch(Exception e) {
						System.out.println("You inserted invalid scalar");
					}
				}
				catch(Exception e) {
					System.out.println("You inserted invalid polynomial");
				}
				break;
			case "4":
				System.out.println("You have selected: Derivate");
				System.out.println("Please insert the polynomial");
				try {
				firstInput = sc.nextLine().toLowerCase();
				pl1 = new Polynomial (firstInput,isReal);
				System.out.println("The derivative polynomial is:" + pl1.derivate().toString());
				}
				catch(Exception e) {
					System.out.println("You inserted invalid polynomial");
				}
				break;
			case "5":
				System.out.println("You chose to exit");
				System.out.println("bye bye");
				toExit = true;
				break;
			default:
				System.out.println("You entered illegal value try again");
				break;
			} 

		}
	}
}



