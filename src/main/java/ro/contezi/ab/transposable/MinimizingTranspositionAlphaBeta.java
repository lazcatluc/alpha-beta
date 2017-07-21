package ro.contezi.ab.transposable;

public class MinimizingTranspositionAlphaBeta extends TranspositionAlphaBeta {
    private final double alpha;
    private final double beta;

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
            ret = Double.min(ret, new MaximizingTranspositionAlphaBeta(child, getDepth() - 1, alpha, myBeta).getValue());
            myBeta = Double.min(myBeta, ret);
            if (myBeta <= alpha) {
                break;
            }
        }
        return ret;
    }

}
