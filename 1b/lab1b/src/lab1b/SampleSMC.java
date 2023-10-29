package lab1b;
import java.util.ArrayList;
import java.util.List;
public class SampleSMC {
	private SampleSMCContext _fsm;
	private boolean isAcceptable;
	private boolean eqFlag;
	private int len;
	private List<Character> function;
	
	public SampleSMC()
	{
		_fsm = new SampleSMCContext(this);
		this.isAcceptable = false;
		this.eqFlag = false;
		this.len = 0;
		function = new ArrayList<>();
		
		_fsm.setDebugFlag(true);
	}
	 
	public boolean checkSample(String line)
	{
		int i,Length;
		char c;
		_fsm.enterStartState();
		for (i = 0, Length = line.length(); i < Length; ++i)
		{
			c = line.charAt(i);
			if ((c >= 'A' && c <= 'Z') || (c >= 'a'&& c <= 'z'))
				{
				System.out.println("Letter");
				_fsm.letter(c);
				}
			if (c >= '0'&& c <= '9')
				{
				System.out.println("Digit");
				_fsm.digit(c);
				}
			if (c == ',')
				{
				System.out.println("Comma");
				_fsm.cm();
				}
			if (c == ';')
				{
				System.out.println("Oxford comma");
				_fsm.oxford();
				}
			if (c == '(')
				{
				System.out.println("Opening bracket");
				_fsm.openBracket();
				}
			if (c == ')')
				{
				System.out.println("Closing bracket");
				_fsm.closeBracket();
				}
			if (c == '=')
				{
				System.out.println("Equal sign");
				_fsm.equal();
				}
			if (Character.isWhitespace(c))
				{
				System.out.println("Space");
				_fsm.space();
				}
		}
		System.out.println("New line");
		 _fsm.newLine();
		 if(!this.isAcceptable)
		 {
			 System.out.println("Error");
				_fsm.reset();
		 }
		return getStatus();
	}
 	public void startFSM() 
 	{ 
		 _fsm.enterStartState();  
 	} 


	public List<Character> getFunc()
	{
		return function;
	}
	public boolean getStatus()
	{
		return isAcceptable;
	}
	public void isAcceptable()
	{
		System.out.println("Fin state detected");
		isAcceptable = true;
	}
	public void notAcceptable()
	{
		//System.out.println("Default error state detected");
		isAcceptable = false;
	}
	public void lenClear()
	{
		len = 0;
	}
	public void lenInc()
	{
		len++;
	}
	public void eqUpdate()
	{
		eqFlag = true;
		//System.out.println("Flag = " + eqFlag);
		System.out.println("Lencheck = " + lenCheck());
	}
	public void funcWrite(char symbol)
	{
		//System.out.println("Writing func symbol: " + symbol);
		function.add(symbol);
	}
	public boolean lenCheck()
	{
		if (len <= 20)
			return true;
		else 
			return false;
	}
	public boolean eqCheck()
	{
		return eqFlag;
	}
	public void reset()
	{
		System.out.println("-----  Reset reached  -----" );
		isAcceptable = false;
		eqFlag = false;
		function.clear();
		len = 0;
		
	}
	public void msg()
	{
		System.out.println("Input your line here\n");
	}
}
