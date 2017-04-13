package Network;

public interface Cost extends Comparable<Cost> {

	
	/* Returns a new cost that is the sum of this and the specified cost.
Parameters:
cost the cost to add
Returns:
a new cost*/
	
	
	public Cost add (Cost cost);
	
	
}
