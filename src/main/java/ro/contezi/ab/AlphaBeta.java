package ro.contezi.ab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AlphaBeta {
    private final ABNode node;
    private final int depth;
    
    public AlphaBeta(ABNode node, int depth) {
        this.node = node;
        this.depth = depth;
    }
    
    public double getValue() {
        if (getDepth() == 0 || getNode().isTerminal()) {
            return getNode().heuristicValue();
        }
        return computeNonTerminalValue();
    }

    protected abstract double computeNonTerminalValue();

    public int getDepth() {
        return depth;
    }

    public ABNode getNode() {
        return node;
    }
    
    public Collection<? extends ABNode> getNodeChildren() {
        List<? extends ABNode> children = new ArrayList<>(node.children());
        return children.subList(0, Math.min(children.size(), depth));
    }
    
    public abstract ABNode getChild();
}
