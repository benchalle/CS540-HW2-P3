import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlphaBetaPruning {
	private int move = 0;
	private int minMove = 0;
	private double value = 0;
	private int numOfNodesVisited = 0;
	private int numOfNodesEvaluated = 0;
	private int maxDepth = 0;
	private int depthAllowed = 0;
	private int bf = 0;
	private int lowestSeen = 0;
	private int totalNumOfSuccessors = 0;
	//double[] valueMovePair = new double[2];
	//private boolean isBounded = false; 
	ArrayList<Double> score = new ArrayList<Double>();

    public AlphaBetaPruning() {
    }

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
    	System.out.println("Move: " + move);
    	System.out.println("Value: " + value );
    	System.out.println("Number of Nodes Visited: " + numOfNodesVisited);
    	System.out.println("Number of Nodes Evaluated: " + numOfNodesEvaluated);
    	System.out.println("Max Depth Reached: " + maxDepth);
    	DecimalFormat d2 = new DecimalFormat("#.#");
    	System.out.println("Avg Effective Branching Factor: "+ d2.format((double)totalNumOfSuccessors/(bf)));
        // TODO Add your code here
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
    	boolean maxTurn = true;
    	if(state.getLastMove() != -1){
        	int stonesCount = 0;
        	for(int i = 1; i< state.getSize(); i++){
        		if(!state.getStone(i)){
        			stonesCount++;
        		}
        	}
        	if(stonesCount % 2 != 0){
        		maxTurn = false;
        	}
    	}
    	depthAllowed = depth;
    	lowestSeen = depth;
    	value = alphabeta(state,depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxTurn);
    	if(!maxTurn){
    		//System.out.println("" + move);
    		move = minMove;
    	}
    	
        // TODO Add your code here
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
    	numOfNodesVisited++;
    	if(lowestSeen > depth){
    		lowestSeen = depth;
    		maxDepth++;
    	}
    	List<Integer> moves = state.getMoves();
    	
		if(moves.isEmpty() || depth == 0) {
			numOfNodesEvaluated++;
			return state.evaluate();
		}
		
    		if(maxPlayer) {
    			//System.out.println("Max player at depth: "+ depth);
    			bf++;
    			maxPlayer = false;

    			double v = Double.NEGATIVE_INFINITY;
    			
    			for(GameState states : state.getSuccessors()) {
    				totalNumOfSuccessors++;
    				//System.out.println("Max Successors " + states.getLastMove());

    				if(depthAllowed == depth || state.getLastMove() == -1) {
    					
    	    				double newV = Math.max(v,alphabeta(states, depth-1, alpha, beta, maxPlayer));
    	    				if(newV != v){
    	    					//System.out.println("hereMax");
    	    					move = states.getLastMove();
    	    					v = newV;
    	    					//System.out.println("Max move saved: " + newV + " " + move);
    	    				}
    	    				if(v >= beta){
    	    					return v;
    	    				}
    	    				alpha = Math.max(alpha, v);
    					
    				}else {
    					v = Math.max(v,alphabeta(states, depth-1, alpha, beta, maxPlayer));
    					if(v >= beta){
    						return v;
    					}
    					alpha = Math.max(alpha, v);
    				}
    			}
    			return v;
    		}else {
    			//System.out.println("Min player at depth: "+ depth);
    			bf++;
    			maxPlayer = true;
    			double v = Double.POSITIVE_INFINITY;
    			for(GameState states : state.getSuccessors()) {
    				//System.out.println("Min Successors " + states.getLastMove());
    				totalNumOfSuccessors++;
    				if(depthAllowed  == depth) {
    					
    					double newV = Math.min(v, alphabeta(states, depth -1, alpha, beta, maxPlayer));
    					if(newV != v){
    						//System.out.println("minhere " + depth);
    						minMove = states.getLastMove();
    						v = newV;
	    					//System.out.println("Max move saved: " + newV + " " + minMove);
    					}
    					if(v <= alpha){
    						return v;
    					}
    					beta = Math.min(beta, v);
    				}else{
    					v = Math.min(v, alphabeta(states, depth -1, alpha, beta, maxPlayer));
        				if(v <= alpha){
        					return v;
        				}
        				beta = Math.min(beta, v);
    				}
    				
    			}
    			return v;
    		}
        // TODO Add your code here
    }
}