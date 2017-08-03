package ro.contezi.ab.branchbound;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ro.contezi.ab.TicTacToeNode;

public class TicTacToeExpandBestTest {

    @Test
    public void initialPositionIsEqual() throws Exception {
        ExpandBest initialPosition = new ExpandBest(new TicTacToeNode("         "),
                (eb1, eb2) -> Double.compare(eb2.getValue(), eb1.getValue()), 10);
        
        int expansions = 0;
        while (initialPosition.expand() && expansions < 10) {
            expansions++;
        }
        assertThat(initialPosition.getValue()).isEqualTo(0);
    }
    
    @Test
    public void mistakeOnTheFirstMoveIsFatal() throws Exception {
        ExpandBest initialPosition = new ExpandBest(
                new TicTacToeNode("X  "+
                                  "   "+
                                  "0  "));
        while (initialPosition.expand()) {
            // expanding
        }                    
        assertThat(initialPosition.getValue()).isEqualTo(1);
    }

}
