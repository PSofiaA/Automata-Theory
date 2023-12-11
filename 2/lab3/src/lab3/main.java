package lab3;

public class main {
	 public static void main(String []args) {
		 NFA NFA = new NFA("[1-3]a$|bx+");
		 NFA.testPosfix();
	 }
}
