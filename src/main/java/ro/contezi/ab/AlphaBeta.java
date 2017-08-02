package ro.contezi.ab;

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
    
    public abstract ABNode getChild();
}
