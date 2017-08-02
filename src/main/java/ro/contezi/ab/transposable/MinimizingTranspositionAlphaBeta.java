package ro.contezi.ab.transposable;

public class MinimizingTranspositionAlphaBeta extends TranspositionAlphaBeta {
    private final double alpha;
    private final double beta;
    private TranspositionAwareNode child;

    public MinimizingTranspositionAlphaBeta(TranspositionAwareNode node, int depth, double alpha, double beta) {
        super(node, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    protected double computeNonCachedNonTerminalValue() {
        double ret = Double.POSITIVE_INFINITY;
        double myBeta = beta;
        for (TranspositionAwareNode child : getNode().children()) {
            double childValue = new MaximizingTranspositionAlphaBeta(child, getDepth() - 1, alpha, myBeta).getValue();
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

    @Override
    public TranspositionAwareNode getChild() {
        return child;
    }
}
