package code;


public class State {
	private int food, energy, material, prosperity, delay;
    private Resource resource;

    public State(int food, int energy, int material, int prosperity, int delay, Resource resource) {
        this.food = food;
        this.energy = energy;
        this.material = material;
        this.prosperity = prosperity;
        this.delay = delay;
        this.resource = resource;
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

    public int getDelay() {
        return delay;
    }

    public Resource getResource() {
        return resource;
    }

    public String getStringRepresentation() {
        return String.format("%d-%d-%d-%d-%d-%s", food, energy, material, prosperity, delay, resource == null ? "0" : resource.toString());
    } 
}
