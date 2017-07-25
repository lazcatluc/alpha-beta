package ro.contezi.ab.transposable;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ro.contezi.ab.ABNode;

public class TranspositionAwareNode implements ABNode {
    
    private final ABNode node;
    private final Map<TranspositionAwareNode, Double> transpositions;
    private final Map<TranspositionAwareNode, Integer> depth;
    private final Comparator<TranspositionAwareNode> childrenSorter;
    private final Consumer<List<TranspositionAwareNode>> listSorter;

    public TranspositionAwareNode(ABNode node) {
        this.node = node;
        transpositions = new ConcurrentHashMap<>();
        depth = new ConcurrentHashMap<>();
        this.childrenSorter = (c1, c2) -> Double.compare(c2.heuristicValue(), c1.heuristicValue());
        this.listSorter = list -> Collections.sort(list, this.childrenSorter);
    }
    
    private TranspositionAwareNode(ABNode child, Map<TranspositionAwareNode, Double> transpositions, 
            Map<TranspositionAwareNode, Integer> depth, Comparator<TranspositionAwareNode> childrenSorter) {
        this.node = child;
        this.transpositions = transpositions;
        this.depth = depth;
        this.childrenSorter = childrenSorter.reversed();
        this.listSorter = list -> Collections.sort(list, this.childrenSorter);
    }

    @Override
    public double heuristicValue() {
        return transpositions.getOrDefault(this, node.heuristicValue());
    }

    @Override
    public boolean isTerminal() {
        return node.isTerminal();
    }

    public boolean isTerminal(int depth) {
        return node.isTerminal() || this.depth.getOrDefault(this, 0) >= depth;
    }

    @Override
    public Collection<? extends TranspositionAwareNode> children() {
        List<TranspositionAwareNode> children = node.children().stream().map(child -> 
            new TranspositionAwareNode(child, transpositions, depth, childrenSorter)).collect(Collectors.toList());
        listSorter.accept(children);
        return children;
    }
    
    public synchronized void setComputedValueAtDepth(int depth, Double computedValue) {
        transpositions.put(this, computedValue);
        this.depth.put(this, depth);
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
