package ro.contezi.ab.branchbound;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ro.contezi.ab.ABNode;

public class ExpandBest {
    
    private static final Logger LOGGER = LogManager.getLogger(ExpandBest.class);
    
    private final ABNode node;
    private final Comparator<ExpandBest> bestChildFinder;
    private PriorityQueue<ExpandBest> children;
    private boolean noLongerExpandable = false;
    private double value;
    private final int maxExpandableChildren;
    
    public ExpandBest(ABNode node) {
        this(node, (eb1, eb2) -> Double.compare(eb2.getValue(), eb1.getValue()), 3);
    }

    public ExpandBest(ABNode node, Comparator<ExpandBest> bestChildFinder, int maxExpandableChildren) {
        this.node = node;
        this.bestChildFinder = bestChildFinder;
        this.maxExpandableChildren = maxExpandableChildren;
    }

    public boolean expand() {
        if (node.isTerminal() || noLongerExpandable) {
            return false;
        }
        if (children == null) {
            initChildren();
            return true;
        }
        Iterator<ExpandBest> expander = children.iterator();
        Set<ExpandBest> newlyExpanded = new HashSet<>();
        while (expander.hasNext()) {
            ExpandBest next = expander.next();
            if (next.expandBest()) {
                expander.remove();
                newlyExpanded.add(next);
            }
        }
        if (newlyExpanded.isEmpty()) {
            noLongerExpandable = true;
            return false;
        }
        else {
            children.addAll(newlyExpanded);
            computeValue();
            return true;
        }
    }
    
    private boolean expandBest() {
        if (node.isTerminal() || noLongerExpandable) {
            return false;
        }
        if (children == null) {
            initChildren();
            return true;
        }
        Iterator<ExpandBest> expander = children.iterator();
        while (expander.hasNext()) {
            ExpandBest next = expander.next();
            if (next.expandBest()) {
                expander.remove();
                children.offer(next);
                computeValue();
                return true;
            }
        }
        noLongerExpandable = true;
        return false;
    }

    private void initChildren() {
        Collection<? extends ABNode> nodeChildren = node.children();
        PriorityQueue<ExpandBest> childrenToTruncate = new PriorityQueue<>(nodeChildren.size(), bestChildFinder);
        Comparator<ExpandBest> reversed = bestChildFinder.reversed();
        nodeChildren.stream().map(child -> new ExpandBest(child, reversed, maxExpandableChildren))
            .forEach(childrenToTruncate::offer);
        children = new PriorityQueue<>(bestChildFinder);
        childrenToTruncate.stream().limit(maxExpandableChildren).forEach(children::offer);
        computeValue();
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
    
    public Optional<ExpandBest> getGrandChild(ABNode node) {
        if (children == null) {
            LOGGER.debug("Children not expanded yet");
            return Optional.empty();
        }
        for (ExpandBest child : children) {
            if (child.children != null) {
                for (ExpandBest grandChild : child.children) {
                    if (grandChild.node.equals(node)) {
                        LOGGER.debug("Found already expanded grandchild");
                        return Optional.of(grandChild);
                    }
                }
            }
        }
        LOGGER.debug("No expanded grandchild found");
        return Optional.empty();
    }
}
