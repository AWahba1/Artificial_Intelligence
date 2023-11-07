import java.util.*;
public class State {
        private int food, energy, material, prosperity;

        public State(int food, int energy, int material, int prosperity) {
            this.food = food;
            this.energy = energy;
            this.material = material;
            this.prosperity = prosperity;
        }

        public int getFood() {
            return food;
        }

        public int getEnergy() {
            return energy;
        }

        public int getMaterial() {
            return material;
        }

        public int getProsperity() {
            return prosperity;
        }


        public List<State> getSuccessors() {
            List<State> successors = new ArrayList<State>();
            // expand logic
            return successors;
        }
    }