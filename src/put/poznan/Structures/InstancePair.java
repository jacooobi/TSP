package put.poznan.Structures;

public class InstancePair {
    private String name;
    private Nodes nodes;

    public InstancePair(String name, Nodes nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public String getName() {
        return name;
    }

    public Nodes getNodes() {
        return nodes;
    }
}
