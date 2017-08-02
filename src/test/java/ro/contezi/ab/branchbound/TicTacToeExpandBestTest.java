package ro.contezi.ab.branchbound;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ro.contezi.ab.TicTacToeNode;

public class TicTacToeExpandBestTest {

    @Test
    public void initialPositionIsEqual() throws Exception {
        ExpandBest initialPosition = new ExpandBest(new TicTacToeNode("         "));
        
        int expansions = 0;
        while (initialPosition.expand() && expansions < 100) {
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
