package ro.contezi.ab;

public class MaximizingAlphaBeta extends AlphaBeta {

    private final double alpha;
    private final double beta;
    private ABNode child;

    public MaximizingAlphaBeta(ABNode node, int depth, double alpha, double beta) {
        super(node, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    protected double computeNonTerminalValue() {
        double ret = Double.NEGATIVE_INFINITY;
        double myAlpha = alpha;
        for (ABNode child : getNodeChildren()) {
            double childValue = new MinimizingAlphaBeta(child, getDepth() - 1, myAlpha, beta).getValue();
            if (childValue > ret) {
                this.child = child;
            }
            ret = Double.max(ret, childValue);
            myAlpha = Double.max(myAlpha, ret);
            if (beta <= myAlpha) {
                break;
            }
        }
        return ret;
    }

    @Override
    public ABNode getChild() {
        return child;
    }
}
