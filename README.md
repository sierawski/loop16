# loop16
Small Java module for breaking infinite loops/recursions using exceptions.

##Example
    import pl.rrbs.loop16.StaticLoopBreaker;
    
    public class Main {
    	public static void main(String[] args) {
    		int i = 0;
    		while(true) {
    			StaticLoopBreaker.silentTick(Main.class, 9);
    			System.out.println(++i);
    		}
    	}
    }
##Disclaimer
Using exceptions to break out of loops or recursion is not considered to be a good practice. Always check whether changing loop condition or other parts of code won't be a better answer.
