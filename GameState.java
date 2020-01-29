import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private int size;            // The number of stones
    private boolean[] stones;    // Game state: true for available stones, false for taken ones
    private int lastMove;        // The last move
    /**
     * Class constructor specifying the number of stones.
     */
    public GameState(int size) {

        this.size = size;

        //  For convenience, we use 1-based index, and set 0 to be unavailable
        this.stones = new boolean[this.size + 1];
        this.stones[0] = false;

        // Set default state of stones to available
        for (int i = 1; i <= this.size; ++i) {
            this.stones[i] = true;
        }

        // Set the last move be -1
        this.lastMove = -1;
    }

    /**
     * Copy constructor
     */
    public GameState(GameState other) {
        this.size = other.size;
        this.stones = Arrays.copyOf(other.stones, other.stones.length);
        this.lastMove = other.lastMove;
    }


    /**
     * This method is used to compute a list of legal moves
     *
     * @return This is the list of state's moves
     */
    public List<Integer> getMoves() {
    	
    	List<Integer> list = new ArrayList<Integer>();
    	if(getLastMove() ==-1){
    		for(int i = 1; i < stones.length; i++){
    			if(i <(stones.length-1)/2.0 && i % 2 != 0){
    				list.add(i);
    			}
    		}
    	}else{
    	
    	for(int i = 1; i < stones.length; i++) {
    		if(stones[i] == true) {
    			if(i % lastMove == 0 || lastMove % i == 0) {
    				list.add(i);
    			}
    		}
    	}
    	}

        // TODO Add your code here
        return list;
    }


    /**
     * This method is used to generate a list of successors
     * using the getMoves() method
     *
     * @return This is the list of state's successors
     */
    public List<GameState> getSuccessors() {
        return this.getMoves().stream().map(move -> {
            GameState state = new GameState(this);
            state.removeStone(move);
            return state;
        }).collect(Collectors.toList());
    }


    /**
     * This method is used to evaluate a game state based on
     * the given heuristic function
     *
     * @return int This is the static score of given state
     */
    public double evaluate() {
    	List<Integer> successors = getMoves();

    	//odd number of stones its min turn
    	//even number of stones its max
    	int stonesCount = 0;
    	for(int i = 1; i< stones.length; i++){
    		if(!stones[i]){
    			stonesCount++;
    		}
    	}
    	boolean maxTurn = true;
    	if(stonesCount % 2 == 0) {
    		maxTurn = false;
    	}
    	if(lastMove == -1){
    		maxTurn = true;
    	}
    	if(maxTurn && successors.isEmpty()) {
    		return 1;
    	}else if(!maxTurn && successors.isEmpty()) {
    		return -1;
    	}else {
    		if(maxTurn) {
    			if(stones[1]) {
    				return 0;
    			}
    			if(lastMove == 1) {
    				int count = successors.size();
    				
    				if(count % 2 == 0) {
    					return -0.5;
    				}else {
    					return 0.5;
    				}
    			}
    			if(Helper.isPrime(lastMove)) {
    				int count = 0;
    				for(int i = 0; i < successors.size(); i++){
    						if(successors.get(i) % lastMove == 0){
    							count++;
    						}
    				}
    				if(count % 2 == 0){
    					return -0.7;
    				}else{
    					return 0.7;
    				}
    			
    					
    			}else{
    				int count = 0;
    				int lpf = Helper.getLargestPrimeFactor(lastMove);
    				for(int i = 0; i < successors.size(); i++){
    					if(lpf % successors.get(i) == 0){
    						count ++;
    					}
    				}
    				if(count % 2 ==0){
    					return -0.6;
    				}else{
    					return 0.6;
    				}
    			}
    			
    			
    		}else{
    			if(lastMove == 1){
    				int count = getSuccessors().size();
    				
    				if(count % 2 == 0){
    					return 0.5;
    				}else{
    					return -0.5;
    				}
    			}
    			if(Helper.isPrime(lastMove)){
    				int count = 0;
    				for(int i = 0; i < successors.size(); i++){
    						if(successors.get(i) % lastMove == 0){
    							count++;
    						}
    				}
    				if(count % 2 == 0){
    					return 0.7;
    				}else{
    					return -0.7;
    				}
    			}else{
    				int count = 0;
    				int lpf = Helper.getLargestPrimeFactor(lastMove);
    				for(int i = 0; i < successors.size(); i++){
    					if(lpf % successors.get(i) == 0){
    						count ++;
    					}
    				}
    				if(count % 2 ==0){
    					return 0.6;
    				}else{
    					return -0.6;
    				}
    			}
    		}
    	}
    
    	//At an end game state
    	
        // TODO Add your code here
    }

    /**
     * This method is used to take a stone out
     *
     * @param idx Index of the taken stone
     */
    public void removeStone(int idx) {
        this.stones[idx] = false;
        this.lastMove = idx;
    }

    /**
     * These are get/set methods for a stone
     *
     * @param idx Index of the taken stone
     */
    public void setStone(int idx) {
        this.stones[idx] = true;
    }

    public boolean getStone(int idx) {
        return this.stones[idx];
    }

    /**
     * These are get/set methods for lastMove variable
     *
     * @param move Index of the taken stone
     */
    public void setLastMove(int move) {
        this.lastMove = move;
    }

    public int getLastMove() {
        return this.lastMove;
    }

    /**
     * This is get method for game size
     *
     * @return int the number of stones
     */
    public int getSize() {
        return this.size;
    }

}	