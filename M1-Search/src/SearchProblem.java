 import java.util.*;

 public class SearchProblem {
        private Node root;

        int initialProsperity;
        int initialFood, initialMaterials, initialEnergy;
        int unitPriceFood, unitPriceMaterials, unitPriceEnergy;
        int amountRequestFood, delayRequestFood;
        int amountRequestMaterials, delayRequestMaterials;
        int amountRequestEnergy, delayRequestEnergy;
        int priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1;
        int priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2;
      

        // Handles delivery part
        Resource resourceToDeliver = null; // null signifies no delivery


        public SearchProblem(String inputString) {
            
            parseInputString(inputString);

            State initialState = new State(initialFood, initialEnergy, initialMaterials, initialProsperity);
            this.root = new Node(initialState, null, 0, 0, null);

        }

        private void parseInputString(String inputString)
        {
            String [] lines = inputString.split(";");
            this.initialProsperity = Integer.parseInt(lines[0]);

            String [] resources = lines[1].split(",");
            this.initialFood = Integer.parseInt(resources[0]);
            this.initialMaterials = Integer.parseInt(resources[1]);
            this.initialEnergy = Integer.parseInt(resources[2]);

            String [] prices = lines[2].split(",");
            this.unitPriceFood = Integer.parseInt(prices[0]);
            this.unitPriceMaterials = Integer.parseInt(prices[1]);
            this.unitPriceEnergy = Integer.parseInt(prices[2]);

            String [] requests = lines[3].split(",");
            this.amountRequestFood = Integer.parseInt(requests[0]);
            this.delayRequestFood = Integer.parseInt(requests[1]);

            String [] requests2 = lines[4].split(",");
            this.amountRequestMaterials = Integer.parseInt(requests2[0]);
            this.delayRequestMaterials = Integer.parseInt(requests2[1]);

            String [] requests3 = lines[5].split(",");
            this.amountRequestEnergy = Integer.parseInt(requests3[0]);
            this.delayRequestEnergy = Integer.parseInt(requests3[1]);

            String [] prices1 = lines[6].split(",");
            this.priceBUILD1 = Integer.parseInt(prices1[0]);
            this.foodUseBUILD1 = Integer.parseInt(prices1[1]);
            this.materialsUseBUILD1 = Integer.parseInt(prices1[2]);
            this.energyUseBUILD1 = Integer.parseInt(prices1[3]);
            this.prosperityBUILD1 = Integer.parseInt(prices1[4]);

            String [] prices2 = lines[7].split(",");
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
            List<Node> children = new ArrayList<Node>();

            State currentState = parent.getState();
            children.add(new Node(getNewState(currentState, Operator.REQUEST_FOOD), parent, 1, 0, "WAIT"));
            // expand logic HERE
            return children;
        }

        public State getNewState(State currentState, Operator operatorToApply)
        {
            switch(operatorToApply)
            {
                case REQUEST_FOOD: 
                    resourceToDeliver = Resource.FOOD;
                    return new State(currentState.getFood() - 1, currentState.getEnergy() - 1, currentState.getMaterial() - 1, 
                    currentState.getProsperity());          

                case REQUEST_MATERIAL:
                    resourceToDeliver = Resource.MATERIAL;
                    return new State(currentState.getFood() - 1, currentState.getEnergy() - 1, currentState.getMaterial() - 1, 
                    currentState.getProsperity()); 

                case REQUEST_ENERGY:
                    resourceToDeliver = Resource.ENERGY;
                    return new State(currentState.getFood() - 1, currentState.getEnergy() - 1, currentState.getMaterial() - 1, 
                    currentState.getProsperity()); 

                case BUILD1:
                    return new State(currentState.getFood() - foodUseBUILD1, currentState.getEnergy() - energyUseBUILD1 , currentState.getMaterial() - materialsUseBUILD1, 
                    currentState.getProsperity() + prosperityBUILD1);

                case BUILD2: 
                    return new State(currentState.getFood() - foodUseBUILD2, currentState.getEnergy() - energyUseBUILD2, currentState.getMaterial() - materialsUseBUILD2, 
                        currentState.getProsperity() + prosperityBUILD2);
                
                default: // WAIT Case
                    return new State(currentState.getFood() - 1, currentState.getEnergy() - 1, currentState.getMaterial() - 1, 
                    currentState.getProsperity());
                
            }
        
        }

        public int calculatePathCost(Operator operatorToApply){

            switch (operatorToApply)
            {
                case REQUEST_FOOD: case REQUEST_MATERIAL: case REQUEST_ENERGY: case WAIT:
                    return unitPriceEnergy + unitPriceFood + unitPriceMaterials;
                
                case BUILD1:
                    return priceBUILD1 + foodUseBUILD1 * unitPriceFood + energyUseBUILD1 * unitPriceEnergy + materialsUseBUILD1 * unitPriceMaterials;
                
                default: // BUILD2 Case
                    return priceBUILD2 + foodUseBUILD2 * unitPriceFood + energyUseBUILD2 * unitPriceEnergy + materialsUseBUILD2 * unitPriceMaterials;
            }

        }

    }