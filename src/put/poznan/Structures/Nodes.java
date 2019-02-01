package put.poznan.Structures;

import java.util.ArrayList;
import java.util.HashMap;

public class Nodes extends ArrayList<Node> {
    public Nodes copy() {
        Nodes newClone = new Nodes();

        for (HashMap node : this) {
            newClone.add((Node)node.clone());
        }

        return newClone;
    }

    public Nodes subList(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }

        Nodes newNodes = new Nodes();

        for (int i = start; i < end; i++) {
            newNodes.add(this.get(i));
        }

        return newNodes;
    }
}
