package code;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchProblem {

	  private Node root;

	  int initialProsperity;
	  int initialFood, initialMaterials, initialEnergy;
	  int unitPriceFood, unitPriceMaterials, unitPriceEnergy;
	  int amountRequestFood, delayRequestFood;
	  int amountRequestMaterials, delayRequestMaterials;
	  int amountRequestEnergy, delayRequestEnergy;
	  int priceBUILD1, materialsUseBUILD1, energyUseBUILD1;
	  int priceBUILD2, materialsUseBUILD2, energyUseBUILD2;
	  static int prosperityBUILD1, prosperityBUILD2, foodUseBUILD1, foodUseBUILD2;
	  int nodesCounter = 0;


	  String strategy;
	  int cutoff;

	  // Repeated States
	  HashSet<String> visitedStates;

	  public SearchProblem(String inputString, String strategy, boolean visualize) {
	    parseInputString(inputString);
	    if (visualize) {
	    	
	    	System.out.println();
	    	System.out.println("Initial Conditions:");
	    	System.out.println("-----------------------");
	    	System.out.println("initialProsperity: " + initialProsperity);
	    	System.out.println("initialFood: " + initialFood);
	    	System.out.println("initialMaterials: " + initialMaterials);
	    	System.out.println("initialEnergy: " + initialEnergy);
	    	
	    	System.out.println("unitPriceFood: " + unitPriceFood);
	    	System.out.println("unitPriceMaterials: " + unitPriceMaterials);
	    	System.out.println("unitPriceEnergy: " + unitPriceEnergy);
	    	
	    	System.out.println("amountRequestFood: " + amountRequestFood);
	    	System.out.println("delayRequestFood: " + delayRequestFood);
	    	
	    	System.out.println("amountRequestMaterials: " + amountRequestMaterials);
	    	System.out.println("delayRequestMaterials: " + delayRequestMaterials);
	    	
	    	System.out.println("amountRequestEnergy: " + amountRequestEnergy);
	    	System.out.println("delayRequestEnergy: " + delayRequestEnergy);
	    	
	    	System.out.println("priceBUILD1: " + priceBUILD1);
	    	System.out.println("foodUseBUILD1: " + foodUseBUILD1);
	    	System.out.println("materialsUseBUILD1: " + materialsUseBUILD1);
	    	System.out.println("energyUseBUILD1: " + energyUseBUILD1);
	    	System.out.println("prosperityBUILD1: " + prosperityBUILD1);

	    	System.out.println("priceBUILD2: " + priceBUILD2);
	    	System.out.println("foodUseBUILD2: " + foodUseBUILD2);
	    	System.out.println("materialsUseBUILD2: " + materialsUseBUILD2);
	    	System.out.println("energyUseBUILD2: " + energyUseBUILD2);
	    	System.out.println("prosperityBUILD2: " + prosperityBUILD2);
	    	System.out.println("-------------------------------------------");
	    }
	    State initialState = new State(
	      initialFood,
	      initialEnergy,
	      initialMaterials,
	      initialProsperity,
	      0,
	      null
	    );
	    this.root = new Node(initialState, null, 0, 0, null);
	    this.visitedStates = new HashSet<>();

	    // To handle Iterative Deepening
	    this.strategy = strategy;
	  }

	  private void parseInputString(String inputString) {
	    String[] lines = inputString.split(";");
	    this.initialProsperity = Integer.parseInt(lines[0]);

	    String[] resources = lines[1].split(",");
	    this.initialFood = Integer.parseInt(resources[0]);
	    this.initialMaterials = Integer.parseInt(resources[1]);
	    this.initialEnergy = Integer.parseInt(resources[2]);

	    String[] prices = lines[2].split(",");
	    this.unitPriceFood = Integer.parseInt(prices[0]);
	    this.unitPriceMaterials = Integer.parseInt(prices[1]);
	    this.unitPriceEnergy = Integer.parseInt(prices[2]);

	    String[] requests = lines[3].split(",");
	    this.amountRequestFood = Integer.parseInt(requests[0]);
	    this.delayRequestFood = Integer.parseInt(requests[1]);

	    String[] requests2 = lines[4].split(",");
	    this.amountRequestMaterials = Integer.parseInt(requests2[0]);
	    this.delayRequestMaterials = Integer.parseInt(requests2[1]);

	    String[] requests3 = lines[5].split(",");
	    this.amountRequestEnergy = Integer.parseInt(requests3[0]);
	    this.delayRequestEnergy = Integer.parseInt(requests3[1]);

	    String[] prices1 = lines[6].split(",");
	    this.priceBUILD1 = Integer.parseInt(prices1[0]);
	    foodUseBUILD1 = Integer.parseInt(prices1[1]);
	    this.materialsUseBUILD1 = Integer.parseInt(prices1[2]);
	    this.energyUseBUILD1 = Integer.parseInt(prices1[3]);
	    prosperityBUILD1 = Integer.parseInt(prices1[4]);

	    String[] prices2 = lines[7].split(",");
	    this.priceBUILD2 = Integer.parseInt(prices2[0]);
	    foodUseBUILD2 = Integer.parseInt(prices2[1]);
	    this.materialsUseBUILD2 = Integer.parseInt(prices2[2]);
	    this.energyUseBUILD2 = Integer.parseInt(prices2[3]);
	    prosperityBUILD2 = Integer.parseInt(prices2[4]);
	  }

	  public Node getRoot() {
	    return root;
	  }

	  public boolean isGoalState(Node node) {
	    return node.getState().getProsperity() >= 100;
	  }


	  public List<Node> expand(Node parent) {
	    nodesCounter++;
	    List<Node> children = new ArrayList<Node>();
	    if (strategy == "ID")
	    {
	    	System.out.println(parent.getDepth());
	        if (parent.getDepth() < cutoff)
	        {
	        	 children = expandNode(parent);
	 	        System.out.println(children);
	        }
	        else if (parent.getDepth() == cutoff)
	          return children;
	    }
	    else{
	      children = expandNode(parent);
	    }
	    return children;
	  }

	  public State getNewState(
	    State currentState,
	    Node parentNode,
	    Operator operatorToApply
	  ) {
	    int foodToAdd = 0;
	    int energyToAdd = 0;
	    int materialToAdd = 0;
	    if (currentState.getDelay() == 1) {
	      switch (currentState.getResource()) {
	        case FOOD:
	          foodToAdd = amountRequestFood;
	          break;
	        case MATERIAL:
	          materialToAdd = amountRequestMaterials;
	          break;
	        case ENERGY:
	          energyToAdd = amountRequestEnergy;
	          break;
	      }
	    }
	    int newDelay = currentState.getDelay() - 1;
	    Resource newResource = parentNode.getState().getResource();
	    if (newDelay < 0) {
	      newDelay = 0;
	      newResource = null;
	    }
	    switch (operatorToApply) {
	      case REQUEST_FOOD:
	        return new State(
	          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
	          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
	          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
	          currentState.getProsperity(),
	          delayRequestFood,
	          Resource.FOOD
	        );
	      case REQUEST_MATERIAL:
	        return new State(
	          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
	          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
	          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
	          currentState.getProsperity(),
	          delayRequestMaterials,
	          Resource.MATERIAL
	        );
	      case REQUEST_ENERGY:
	        return new State(
	          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
	          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
	          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
	          currentState.getProsperity(),
	          delayRequestEnergy,
	          Resource.ENERGY
	        );
	      case BUILD1:
	      {
	    	  return new State(
	    	          Math.min(currentState.getFood() - foodUseBUILD1 + foodToAdd, 50),
	    	          Math.min(
	    	            currentState.getEnergy() - energyUseBUILD1 + energyToAdd,
	    	            50
	    	          ),
	    	          Math.min(
	    	            currentState.getMaterial() - materialsUseBUILD1 + materialToAdd,
	    	            50
	    	          ),
	    	          currentState.getProsperity() + prosperityBUILD1,
	    	          newDelay,
	    	          newResource
	    	        );
	      }
	      case BUILD2:
	        return new State(
	          Math.min(currentState.getFood() - foodUseBUILD2 + foodToAdd, 50),
	          Math.min(
	            currentState.getEnergy() - energyUseBUILD2 + energyToAdd,
	            50
	          ),
	          Math.min(
	            currentState.getMaterial() - materialsUseBUILD2 + materialToAdd,
	            50
	          ),
	          currentState.getProsperity() + prosperityBUILD2,
	          newDelay,
	          newResource
	        );
	      default: // WAIT Case
	        return new State(
	          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
	          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
	          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
	          currentState.getProsperity(),
	          newDelay,
	          newResource
	        );
	    }
	  }

	  public int calculatePathCost(Operator operatorToApply) {
	    switch (operatorToApply) {
	      case REQUEST_FOOD:
	      case REQUEST_MATERIAL:
	      case REQUEST_ENERGY:
	      case WAIT:
	        return unitPriceEnergy + unitPriceFood + unitPriceMaterials;
	      case BUILD1:
	        return (
	          priceBUILD1 +
	          foodUseBUILD1 *
	          unitPriceFood +
	          energyUseBUILD1 *
	          unitPriceEnergy +
	          materialsUseBUILD1 *
	          unitPriceMaterials
	        );
	      default: // BUILD2 Case
	        return (
	          priceBUILD2 +
	          foodUseBUILD2 *
	          unitPriceFood +
	          energyUseBUILD2 *
	          unitPriceEnergy +
	          materialsUseBUILD2 *
	          unitPriceMaterials
	        );
	    }
	  }

	  public int getNodesCounter() {
	    return nodesCounter;
	  }

	  public List<Node> expandNode(Node parent){
		List<Node> children = new ArrayList<Node>();
		
	    State currentState = parent.getState();
	    if (
	      parent.getState().getFood() >= 1 &&
	      parent.getState().getEnergy() >= 1 &&
	      parent.getState().getMaterial() >= 1
	    ) {
	      if (parent.getState().getDelay() == 0) {
	        if (
	          parent.getPathCost() +
	          calculatePathCost(Operator.REQUEST_FOOD) <=
	          100000
	        && !visitedStates.contains(getNewState(currentState, parent, Operator.REQUEST_FOOD).getStringRepresentation())) {
	          children.add(
	            new Node(
	              getNewState(currentState, parent, Operator.REQUEST_FOOD),
	              parent,
	              parent.getDepth() + 1,
	              parent.getPathCost() + calculatePathCost(Operator.REQUEST_FOOD),
	              Operator.REQUEST_FOOD
	            )
	          );
	          visitedStates.add(getNewState(currentState, parent, Operator.REQUEST_FOOD).getStringRepresentation());
	        }
	        if (
	          parent.getPathCost() +
	          calculatePathCost(Operator.REQUEST_MATERIAL) <=
	          100000
	          && !visitedStates.contains(getNewState(currentState, parent, Operator.REQUEST_MATERIAL).getStringRepresentation())
	        ) {
	          children.add(
	            new Node(
	              getNewState(currentState, parent, Operator.REQUEST_MATERIAL),
	              parent,
	              parent.getDepth() + 1,
	              parent.getPathCost() +
	              calculatePathCost(Operator.REQUEST_MATERIAL),
	              Operator.REQUEST_MATERIAL
	            )
	          );
	          visitedStates.add(getNewState(currentState, parent, Operator.REQUEST_MATERIAL).getStringRepresentation());

	        }
	        if (
	          parent.getPathCost() +
	          calculatePathCost(Operator.REQUEST_ENERGY) <=
	          100000
	          && !visitedStates.contains(getNewState(currentState, parent, Operator.REQUEST_ENERGY).getStringRepresentation())
	        ) {
	          children.add(
	            new Node(
	              getNewState(currentState, parent, Operator.REQUEST_ENERGY),
	              parent,
	              parent.getDepth() + 1,
	              parent.getPathCost() + calculatePathCost(Operator.REQUEST_ENERGY),
	              Operator.REQUEST_ENERGY
	            )
	          );
	          visitedStates.add(getNewState(currentState, parent, Operator.REQUEST_ENERGY).getStringRepresentation());

	        }
	      }
	      if (parent.getPathCost() + calculatePathCost(Operator.WAIT) <= 100000 
	      && !visitedStates.contains(getNewState(currentState, parent, Operator.WAIT).getStringRepresentation()) 
	      && parent.getState().getDelay()!=0) {
	        children.add(
	          new Node(
	            getNewState(currentState, parent, Operator.WAIT),
	            parent,
	            parent.getDepth() + 1,
	            parent.getPathCost() + calculatePathCost(Operator.WAIT),
	            Operator.WAIT
	          )
	        );
	        visitedStates.add(getNewState(currentState, parent, Operator.WAIT).getStringRepresentation());

	      }
	      if (parent.getPathCost() + calculatePathCost(Operator.BUILD1) <= 100000
	      && !visitedStates.contains(getNewState(currentState, parent, Operator.BUILD1).getStringRepresentation()) 
	      && isOperatorValid(parent, Operator.BUILD1 )) {
	        children.add(
	          new Node(
	            getNewState(currentState, parent, Operator.BUILD1),
	            parent,
	            parent.getDepth() + 1,
	            parent.getPathCost() + calculatePathCost(Operator.BUILD1),
	            Operator.BUILD1
	          )
	        );
	       visitedStates.add(getNewState(currentState, parent, Operator.BUILD1).getStringRepresentation());

	      }
	      if (parent.getPathCost() + calculatePathCost(Operator.BUILD2) <= 100000
	      && !visitedStates.contains(getNewState(currentState, parent, Operator.BUILD2).getStringRepresentation())
	      && isOperatorValid(parent, Operator.BUILD2 )) {
	        children.add(
	          new Node(
	            getNewState(currentState, parent, Operator.BUILD2),
	            parent,
	            parent.getDepth() + 1,
	            parent.getPathCost() + calculatePathCost(Operator.BUILD2),
	            Operator.BUILD2
	          )
	        );
	      visitedStates.add(getNewState(currentState, parent, Operator.BUILD2).getStringRepresentation());

	      }
	    }
	   
	    return children;

	  } // end expand node

	  public void setCutOff(int cutoff)
	  {
	    this.cutoff = cutoff;
	  }
	  
	  public boolean isOperatorValid(Node parent, Operator operator) {
		  State parentState = parent.getState();
		  if (operator == Operator.BUILD1)
		  {
			  return parentState.getFood()>=SearchProblem.foodUseBUILD1 
					  && parentState.getEnergy()>=this.energyUseBUILD1 && parentState.getMaterial()>=this.materialsUseBUILD1;
		  }
		  else if (operator == Operator.BUILD2)
		  {
			  return parentState.getFood()>=SearchProblem.foodUseBUILD2
					  && parentState.getEnergy()>=this.energyUseBUILD2 && parentState.getMaterial()>=this.materialsUseBUILD2;		 
		  }
		  else {
			 return false; // ONLY BUILD IS ALLOWED FOR NOW
		  }
			  
	  }
	}
