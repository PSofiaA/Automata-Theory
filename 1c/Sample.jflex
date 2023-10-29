import java.io.*;
import java.util.*;

%%
%class Lexer
%standalone	
%unicode

%{
 	StringBuffer string = new StringBuffer();
 	String funcBuffer = new String();

 	private List<String> functions;

 	public List<String> getFunctions()
 	{
 		return this.functions;
 	}
 	public void funcWrite(String func)
	{
		this.functions.add(func);
	}
	public void viewStatistics(List<String> list)
   {
      Set<String> unique = new HashSet<String>(list);
      for (String key : unique) {
         System.out.println(key + " called out " + Collections.frequency(list, key) + " times");
      }
      unique.clear();
   }
   public void writeFile(StringBuffer buffer)
	{
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt")) ) 
		{
	     writer.write(buffer.toString());
	    }
	catch (IOException e) 
		{
	 	e.printStackTrace();
	    }
	}
   public void saveData(StringBuffer buffer, String toSave, String condition)
   {
   	buffer.append(condition); 
		buffer.append(toSave); 
		writeFile(buffer);
   }
   public void saveData(StringBuffer buffer, String condition)
   {
   	buffer.append(condition); 
		writeFile(buffer);
   }
%}

Name = [A-Za-z]([A-Za-z0-9]){0,19}
NameDigit = [0-9]{1,20}
Parameters = \s* \( \s*(\s*({Name}|{NameDigit})\s*(\s*,\s*({Name}|{NameDigit}))*)?\s* \)\s*;

%state FUNC, PARAMS, FIN, ERRSTATE
%%
<YYINITIAL>
{
	" " 					{}
	{Name}\s*=\s* 		{string.append(yytext()); yybegin(FUNC);}

	"\r\n" 				{
							saveData(string,yytext(),"--> Incorrect!");
							yybegin(YYINITIAL);}

	<<EOF>> 				{writeFile(string);  
							viewStatistics(getFunctions()); 
							return YYEOF;}
}
<FUNC>
{
	{Name} 				{string.append(yytext()); 
							 funcBuffer = yytext();
						 	 yybegin(PARAMS); }
}
<PARAMS>
{
	{Parameters} 		{string.append(yytext()); 
							yybegin(FIN);}
							
}
<FIN>
{
	<<EOF>> 				{funcWrite(funcBuffer);
							saveData(string, "--> Correct!");
							viewStatistics(getFunctions()); 
							return YYEOF;}

	[\t\ ]				{}

	"\r\n" 				{funcWrite(funcBuffer);
							saveData(string,yytext(), "--> Correct!");
							yybegin(YYINITIAL);}
}
<ERRSTATE>
{
	[^"\r\n"] 			{string.append(yytext());}

	"\r\n" 				{saveData(string,yytext(),"--> Incorrect!");
							yybegin(YYINITIAL);}

	<<EOF>> 				{
							saveData(string,yytext(), "--> Incorrect!");
							viewStatistics(getFunctions()); 
							return YYEOF;}					  
}
/* error fallback */
[^]    					{string.append(yytext()); yybegin(ERRSTATE);}