package ro.contezi.ab;

import java.util.Collection;

public interface ABNode {
    double heuristicValue();
    boolean isTerminal();
    Collection<? extends ABNode> children();
}
