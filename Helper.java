public class Helper {
	
	/** 
    * Class constructor.
    */
	private Helper () {}

	/**
	* This method is used to check if a number is prime or not
	* @param x A positive integer number
	* @return boolean True if x is prime; Otherwise, false
	*/
	public static boolean isPrime(int x) {
		
		if(x == 0 || x == 1) {
			return false;
		}
		
		for(int i = 2; i < x; i++) {
			if(x % i == 0) {
				return false;
			}
		}
		// TODO Add your code here

		return true;
	}

	/**
	* This method is used to get the largest prime factor 
	* @param x A positive integer number
	* @return int The largest prime factor of x
	*/
	public static int getLargestPrimeFactor(int x) {
		
		int largestPrimeFactor = 2;
		
		while (x > largestPrimeFactor) {
			if(x % largestPrimeFactor !=0 ) {
				largestPrimeFactor++;
			}else {
				x = x/largestPrimeFactor;
				largestPrimeFactor = 2;
			}
		}
		return largestPrimeFactor;
    }
}