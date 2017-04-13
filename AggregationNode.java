package Network;

import com.google.common.collect.Maps;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.Value;
import com.google.visualization.datasource.query.AggregationType;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;


public class AggregationNode {

	

	
	/* A szülő csomopontja az aggregációs fában /*  
	private AggregationNode parent;
	
	
	

	  private Value value;
	  
	  

	  
	/*  Map egy egy oszlop id az aggregálásban. Az oszlop id, a lista aggregációs oszlopokhoz tartozik */
	  
	  private Map<String, ValueAggregator> columnAggregators = Maps.newHashMap();

	  
	
	/*  Maps az egy érték, a gyerek csompont(amely szintén agregált cspmopot)
	  Az érték ugyanaz az érték mint mint amit eltároltunk a gyerekben azaz  children.get(X).getValue() 
	  should equal X.*/
	  
	  private Map<Value, AggregationNode> children = Maps.newHashMap();

	  
	/*  Construal egy új aggregált csomopontot, amelynek paraméterei egy sor azonosítói (aggregációs oszlopok) 
	  */
	  
	  public AggregationNode(Set<String> columnsToAggregate, DataTable table) {
		     
		      //Hozzáadás egy oszlop aggregációs értékét az egyes aggregációs oszlopra. 
		  for (String columnId : columnsToAggregate) {
		        columnAggregators.put(columnId, new ValueAggregator(
		           table.getColumnDescription(columnId).getType()));
		       }
	  
	  }
		      

	  
	/
	
	/*  Aggregált értékek használata a csomopontban. 
	  Paraméterek : valuesByColumn egy oszlop id ami kell az agregáláshoz. (Összesítés)*/
	  
public void aggregate(Map<String, Value> valuesByColumn) {
     for (String columnId : valuesByColumn.keySet()) {
       columnAggregators.get(columnId).aggregate(valuesByColumn.get(columnId));
     }
   }

	  
	
	
/* Viszatérés az adott oszlop(tábla) aggregált értékkel és tipussal.
 * Paraméter : 
 * oszlop id, 
 * 	
 */

public Value getAggregationValue(String columnId, AggregationType type) {
     ValueAggregator valuesAggregator = columnAggregators.get(columnId);
     if (valuesAggregator == null) {
       throw new IllegalArgumentException("Column " + columnId +
          " is not aggregated");
    }
    return valuesAggregator.getValue(type);
  }

  




/*Visszatérés aggregált csomopont által meghatározott, értékkel */

public AggregationNode getChild(Value v) {
    AggregationNode result = children.get(v);
    if (result == null) 
	throw new NoSuchElementException("Value " + v + " is not a child.");    
    }
    return result;
    
}
}




/*Visszatér tru-val ha egy csomopotn tartalmaz egy gyereket(csomopont, érték által azonosítva, és másképpen hamis )
/*True ha  acsomopont egy gyerek, (v a gyerek értéke)*/


public boolean containsChild(Value v) {
    return children.containsKey(v);
  }







/* Kulcs, Új gyerek hozzáadás, definiáljuk az új gyerek értékét 
 *Paraméter
 * oszlopaggregációs, az oszlop aggregálás azonosítoja. 
 *  
 */

public void addChild(Value key, Set<String> columnsToAggregate, DataTable table) {

    if (children.containsKey(key)) {
      throw new IllegalArgumentException("A child with key: " + key +
          " already exists.");
    }
    AggregationNode node = new AggregationNode(columnsToAggregate, table);
    node.parent = this;
    node.value = key;
    children.put(key, node);
  }


/*Visszatér
/*A Map egy gyerekcsomopontnak1 példányának másolata */



public Map<Value, AggregationNode> getChildren() {
    return Maps.newHashMap(children);
  }



/* Visszatér a csomopont értékkel, Ez szintén a csomopont kulcsa.*/

protected Value getValue() {
    return value;
  }



/* Visszatér a szűlő csomopont az aggregált fában. 
/* A szülő csomopot*/


protected AggregationNode getParent() {
    return parent;
 }
}










	  