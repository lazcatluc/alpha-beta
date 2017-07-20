package ro.contezi.ab;

public class MaximizingAlphaBeta extends AlphaBeta {

    private final double alpha;
    private final double beta;

    public MaximizingAlphaBeta(ABNode node, int depth, double alpha, double beta) {
        super(node, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    protected double computeNonTerminalValue() {
        double ret = Double.NEGATIVE_INFINITY;
        double myAlpha = alpha;
        for (ABNode child : getNode().children()) {
            ret = Double.max(ret, new MinimizingAlphaBeta(child, getDepth() - 1, myAlpha, beta).getValue());
            myAlpha = Double.max(myAlpha, ret);
            if (beta <= myAlpha) {
                break;
            }
        }
        return ret;
    }

}
