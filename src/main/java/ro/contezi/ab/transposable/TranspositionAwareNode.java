package ro.contezi.ab.transposable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import ro.contezi.ab.ABNode;

public class TranspositionAwareNode implements ABNode {
    
    private final ABNode node;
    private final Map<TranspositionAwareNode, Double> transpositions;

    public TranspositionAwareNode(ABNode node) {
        this.node = node;
        transpositions = new ConcurrentHashMap<>();
    }
    
    private TranspositionAwareNode(ABNode child, Map<TranspositionAwareNode, Double> transpositions) {
        this.node = child;
        this.transpositions = transpositions;
    }

    @Override
    public double heuristicValue() {
        return transpositions.getOrDefault(this, node.heuristicValue());
    }

    @Override
    public boolean isTerminal() {
        return transpositions.containsKey(this) || node.isTerminal();
    }

    @Override
    public Collection<? extends TranspositionAwareNode> children() {
        return node.children().stream().map(child -> new TranspositionAwareNode(child, transpositions)).collect(Collectors.toList());
    }
    
    public void setComputedValue(Double computedValue) {
        transpositions.put(this, computedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TranspositionAwareNode other = (TranspositionAwareNode) obj;

        return Objects.equals(node, other.node);
    }
    
}
