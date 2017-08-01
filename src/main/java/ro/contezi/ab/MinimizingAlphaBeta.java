package ro.contezi.ab;

public class MinimizingAlphaBeta extends AlphaBeta {
    private final double alpha;
    private final double beta;
    private ABNode child;

    public MinimizingAlphaBeta(ABNode node, int depth, double alpha, double beta) {
        super(node, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    protected double computeNonTerminalValue() {
        double ret = Double.POSITIVE_INFINITY;
        double myBeta = beta;
        for (ABNode child : getNode().children()) {
            double childValue = new MaximizingAlphaBeta(child, getDepth() - 1, alpha, myBeta).getValue();
            if (childValue < ret) {
                this.child = child;
            }
            ret = Double.min(ret, childValue);
            myBeta = Double.min(myBeta, ret);
            if (myBeta <= alpha) {
                break;
            }
        }
        return ret;
    }
    
    public ABNode getChild() {
        return child;
    }
}
