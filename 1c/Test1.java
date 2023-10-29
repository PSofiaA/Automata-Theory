import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
public class Test1 {
   public static void viewStatistics(List<String> list)
   {
      Set<String> unique = new HashSet<String>(list);
      for (String key : unique) {
         System.out.println(key + " called out " + Collections.frequency(list, key) + " times");
      }
     unique.clear();
   }
   public static void main(String[] args) {
   try {
         Reader input =
            args.length > 0 ?
               new FileReader(args[0]) :
               new InputStreamReader(System.in);
          Lexer scanner = new Lexer(input);
         int token = 0;
         while (token != Lexer.YYEOF) {
            token = scanner.yylex();
   
         };
      } catch (IOException e) {
         System.err.println(e);
      }
   }
}
