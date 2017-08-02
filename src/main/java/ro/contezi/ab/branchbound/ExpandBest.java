package ro.contezi.ab.branchbound;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import ro.contezi.ab.ABNode;

public class ExpandBest {

    private final ABNode node;
    private final Comparator<ExpandBest> bestChildFinder;
    private PriorityQueue<ExpandBest> children;
    private boolean noLongerExpandable = false;
    private double value;
    
    public ExpandBest(ABNode node) {
        this(node, (eb1, eb2) -> Double.compare(eb2.getValue(), eb1.getValue()));
    }

    public ExpandBest(ABNode node, Comparator<ExpandBest> bestChildFinder) {
        this.node = node;
        this.bestChildFinder = bestChildFinder;
    }

    public boolean expand() {
        if (node.isTerminal() || noLongerExpandable) {
            return false;
        }
        if (children == null) {
            Collection<? extends ABNode> nodeChildren = node.children();
            children = new PriorityQueue<>(nodeChildren.size(), bestChildFinder);
            Comparator<ExpandBest> reversed = bestChildFinder.reversed();
            nodeChildren.stream().map(child -> new ExpandBest(child, reversed)).forEach(children::offer);
            computeValue();
            return true;
        }
        Iterator<ExpandBest> expander = children.iterator();
        while (expander.hasNext()) {
            ExpandBest next = expander.next();
            if (next.expand()) {
                expander.remove();
                children.offer(next);
                computeValue();
                return true;
            }
        }
        noLongerExpandable = true;
        return false;
    }

    private void computeValue() {
        value = children.peek().getValue();
    }
    
    public double getValue() {
        if (children == null) {
            return node.heuristicValue();
        }
        return value;
    }

    public ABNode getChild() {
        return children.peek().node;
    }
}
