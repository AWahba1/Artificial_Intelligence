import java.util.*;

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

  public SearchProblem(String inputString) {
    parseInputString(inputString);

    State initialState = new State(
      initialFood,
      initialEnergy,
      initialMaterials,
      initialProsperity,
      0,
      null
    );
    this.root = new Node(initialState, null, 0, 0, null);
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
    this.foodUseBUILD1 = Integer.parseInt(prices1[1]);
    this.materialsUseBUILD1 = Integer.parseInt(prices1[2]);
    this.energyUseBUILD1 = Integer.parseInt(prices1[3]);
    this.prosperityBUILD1 = Integer.parseInt(prices1[4]);

    String[] prices2 = lines[7].split(",");
    this.priceBUILD2 = Integer.parseInt(prices2[0]);
    this.foodUseBUILD2 = Integer.parseInt(prices2[1]);
    this.materialsUseBUILD2 = Integer.parseInt(prices2[2]);
    this.energyUseBUILD2 = Integer.parseInt(prices2[3]);
    this.prosperityBUILD2 = Integer.parseInt(prices2[4]);
  }

  public Node getRoot() {
    return root;
  }

  public boolean isGoalState(Node node) {
    return node.getState().getProsperity() == 100;
  }

  public List<Node> expand(Node parent) {
    nodesCounter++;
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
        ) {
          children.add(
            new Node(
              getNewState(currentState, Operator.REQUEST_FOOD),
              parent,
              parent.getDepth() + 1,
              parent.getPathCost() + calculatePathCost(Operator.REQUEST_FOOD),
              Operator.REQUEST_FOOD
            )
          );
        }
        if (
          parent.getPathCost() +
          calculatePathCost(Operator.REQUEST_MATERIAL) <=
          100000
        ) {
          children.add(
            new Node(
              getNewState(currentState, Operator.REQUEST_MATERIAL),
              parent,
              parent.getDepth() + 1,
              parent.getPathCost() +
              calculatePathCost(Operator.REQUEST_MATERIAL),
              Operator.REQUEST_MATERIAL
            )
          );
        }
        if (
          parent.getPathCost() +
          calculatePathCost(Operator.REQUEST_ENERGY) <=
          100000
        ) {
          children.add(
            new Node(
              getNewState(currentState, Operator.REQUEST_ENERGY),
              parent,
              parent.getDepth() + 1,
              parent.getPathCost() + calculatePathCost(Operator.REQUEST_ENERGY),
              Operator.REQUEST_ENERGY
            )
          );
        }
      }
      if (parent.getPathCost() + calculatePathCost(Operator.WAIT) <= 100000) {
        children.add(
          new Node(
            getNewState(currentState, Operator.WAIT),
            parent,
            parent.getDepth() + 1,
            parent.getPathCost() + calculatePathCost(Operator.WAIT),
            Operator.WAIT
          )
        );
      }
      if (parent.getPathCost() + calculatePathCost(Operator.BUILD1) <= 100000) {
        children.add(
          new Node(
            getNewState(currentState, Operator.BUILD1),
            parent,
            parent.getDepth() + 1,
            parent.getPathCost() + calculatePathCost(Operator.BUILD1),
            Operator.BUILD1
          )
        );
      }
      if (parent.getPathCost() + calculatePathCost(Operator.BUILD2) <= 100000) {
        children.add(
          new Node(
            getNewState(currentState, Operator.BUILD2),
            parent,
            parent.getDepth() + 1,
            parent.getPathCost() + calculatePathCost(Operator.BUILD2),
            Operator.BUILD2
          )
        );
      }
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
          newDelay,
          Resource.FOOD
        );
      case REQUEST_MATERIAL:
        resourceToDeliver = Resource.MATERIAL;
        return new State(
          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
          currentState.getProsperity(),
          newDelay,
          Resource.MATERIAL
        );
      case REQUEST_ENERGY:
        resourceToDeliver = Resource.ENERGY;
        return new State(
          Math.min(currentState.getFood() - 1 + foodToAdd, 50),
          Math.min(currentState.getEnergy() - 1 + energyToAdd, 50),
          Math.min(currentState.getMaterial() - 1 + materialToAdd, 50),
          currentState.getProsperity(),
          newDelay,
          Resource.ENERGY
        );
      case BUILD1:
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
}
