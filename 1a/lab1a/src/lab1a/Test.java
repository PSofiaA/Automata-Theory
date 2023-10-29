package lab1a;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
	    String  choice = "";
	    do {
	        System.out.println("1.Read input");
	        System.out.println("2.Read from file");
	        choice = in.nextLine();
	        switch (choice) {
	            case "1": NaiveSample.readInput(in); break;
	            case "2": NaiveSample.readFile(); break;
	            default: System.out.println("Make your choice and press <ENTER> " +
	                "If exit press <ENTER>"); break;
	        }
	    } while (!choice.isEmpty());
	    in.close();
	}
}
