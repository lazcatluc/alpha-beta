package ro.contezi.ab.transposable;

public class MaximizingTranspositionAlphaBeta extends TranspositionAlphaBeta {
    private final double alpha;
    private final double beta;

    public MaximizingTranspositionAlphaBeta(TranspositionAwareNode node, int depth, double alpha, double beta) {
        super(node, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    protected double computeNonCachedNonTerminalValue() {
        double ret = Double.NEGATIVE_INFINITY;
        double myAlpha = alpha;
        for (TranspositionAwareNode child : getNode().children()) {
            ret = Double.max(ret, new MinimizingTranspositionAlphaBeta(child, getDepth() - 1, myAlpha, beta).getValue());
            myAlpha = Double.max(myAlpha, ret);
            if (beta <= myAlpha) {
                break;
            }
        }
        return ret;
    }

}
