package lab1a;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveSample {
	public static boolean checkName(String checking)
	{
		if (checking.matches("[a-zA-Z][a-zA-Z0-9]{0,19}"))
			return true;
		else 
			return false;
	}
	public static boolean checkParam(String checking)
	{	
		if ( checking == null)
			return false;
		else if (checking.matches("[0-9]+") || checking.matches("[a-zA-Z][a-zA-Z0-9]*"))
			return true;
		else 
			return false;
	}
	public static boolean checkLine(String line, List<String> functions)
	{
		if (!line.matches("(.+)\\s=\\s(.+)\\((.*)\\);"))
			return false;
		String[] divVar = line.split("\\s*\\=\\s*|[(\\(|\\)|;]");
		if (!checkName(divVar[0]) || !checkName(divVar[1]))
			return false;
		if (divVar.length > 2)
			{
			String[] divParams = divVar[2].split(",", -1);
			for (String word:divParams)
		      	  {
					if (!checkParam(word))
						return false;
		      	  } 
			}
		functions.add(divVar[1]);
		return true;
	}
	public static boolean checkWholeLine(String line, List<String> functions)
	{
		Pattern sample = Pattern.compile("^[a-zA-Z][a-zA-Z\\d]{0,19}\\s*=\\s*([a-zA-Z][a-zA-Z\\d]{0,19})\\(((([a-z][a-zA-Z\\d]{0,19})|(\\d){1,20})(,(([a-z][a-zA-Z\\d]{0,19})|(\\d{1,20})))*)?\\);$");
		//("[a-zA-Z][a-zA-Z0-9]{0,19}\\s*=\\s*([a-zA-Z][a-zA-Z0-9]{0,19})\\((([a-z][a-zA-Z0-9]{0,19}|[0-9]{1,20})(,(([a-z][a-zA-Z0-9]{0,19})|([0-9]{1,20})))*)*\\);");
		Matcher	sampleM = sample.matcher(line);
		if (sampleM.find()==false)
			return false;
		else
		{
			functions.add(sampleM.group(1));
		    //System.out.println("Correct function is " + sampleM.group(1));
		}
		//if (!line.matches("[a-zA-Z][a-zA-Z\\d]{0,19}\\s*=\\s*([a-zA-Z][a-zA-Z\\d]{0,19})\\(((([a-z][a-zA-Z\\d]{0,19})|(\\d){1,20})(,(([a-z][a-zA-Z\\d]{0,19})|(\\d{1,20})))*)?\\);"))
		//	return false;
		return true;
	}
	public static void viewStatistics(List<String> list)
	{
		Set<String> unique = new HashSet<String>(list);
		for (String key : unique) {
			System.out.println(key + " called out " + Collections.frequency(list, key) + " times");
		}
		unique.clear();
	    list.clear();
	}
	public static void readInput(Scanner input)
	{
		List<String> list = new ArrayList<String>();
		System.out.println("Please enter your string below: (type 'exit' to exit) ");
	    while (true) {
	        String line = input.nextLine();
	        if ("exit".equalsIgnoreCase(line)) {
	            break;
	        }
	        if (checkWholeLine(line, list)==true)
	        	System.out.println("Correct sample\n");
	        else 
	        	System.out.println("Inorrect sample\n");
	    }
	    viewStatistics(list);
	}
	public static void readFile()
	{
		 try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt")) ) 
		 {
	            String line = reader.readLine();
	            List<String> list = new ArrayList<String>();
	            while (line != null) {
	                writer.write(line);
	                if (checkWholeLine(line, list))
	                	writer.write("--> Correct sample\n");
	    	        else 
	    	        	writer.write("--> Inorrect sample\n");
	                line = reader.readLine();
	            }
	            viewStatistics(list);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
}
