package lab1b;
import java.io.*;
import java.util.*;
public class Test {
	public static void viewStatistics(List<String> list)
	{
		Set<String> unique = new HashSet<String>(list);
		for (String key : unique) {
			System.out.println(key + " called out " + Collections.frequency(list, key) + " times");
		}
		unique.clear();
	    list.clear();
	}
	public static void readInput(Scanner input, SampleSMC testSample)
	{
		List<String> list = new ArrayList<String>();
		//while (true) {
	    String line = input.nextLine();
	    //    	if ("exit".equalsIgnoreCase(line)) {
	    //    		break;}
        if(testSample.checkSample(line)==true)
	    	{
        	System.out.println("Correct sample\n");
        	StringBuilder builder = new StringBuilder(testSample.getFunc().size());
        	for(Character ch: testSample.getFunc())
	                builder.append(ch);
            list.add(builder.toString());
	    	}
	    else 
	        {
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
	              //  if (checkWholeLine(line, list))
	                	writer.write("--> Correct sample\n");
	    	        //else 
	    	        	writer.write("--> Inorrect sample\n");
	                line = reader.readLine();
	            }
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
	public static void main(String[] args) 
	{
		SampleSMC testSample = new SampleSMC();;
		 try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt")) ) 
		 {
			List<String> list = new ArrayList<String>();
			String line = reader.readLine();
			while(line != null)
			{ 
				writer.write(line);	
			    if(testSample.checkSample(line)==true)
		    	{
			    	writer.write("--> Correct sample\n");
		        	StringBuilder builder = new StringBuilder(testSample.getFunc().size());
		        	for(Character ch: testSample.getFunc())
			                builder.append(ch);
		            list.add(builder.toString());
		    	}
			    else 
			    {
			    	writer.write("--> Inorrect sample\n");
		        }
			    testSample.reset();
			    line = reader.readLine();
			}
			System.out.println("--- Statistics --- ");
			viewStatistics(list);
		 }
	      catch (IOException e) {
	        	e.printStackTrace();
	        }
	}
}


