package ro.contezi.ab.transposable;

import ro.contezi.ab.AlphaBeta;

public abstract class TranspositionAlphaBeta extends AlphaBeta {

    public TranspositionAlphaBeta(TranspositionAwareNode node, int depth) {
        super(node, depth);
    }

    @Override
    protected double computeNonTerminalValue() {
        double nonTerminalValue = computeNonCachedNonTerminalValue();
        ((TranspositionAwareNode)getNode()).setComputedValueAtDepth(getDepth(), nonTerminalValue);
        return nonTerminalValue;
    }
    
    @Override
    public double getValue() {
        if (getDepth() == 0 || getNode().isTerminal(getDepth())) {
            return getNode().heuristicValue();
        }
        return computeNonTerminalValue();
    }

    @Override
    public TranspositionAwareNode getNode() {
        return TranspositionAwareNode.class.cast(super.getNode());
    }

    protected abstract double computeNonCachedNonTerminalValue();

}
