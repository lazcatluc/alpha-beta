package ro.contezi.ab.transposable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ro.contezi.ab.TicTacToeNode;

public class TicTacToeTransposableABTest {
    @Test
    public void initialPositionIsEqual() throws Exception {
        TranspositionAwareNode initialPosition = new TranspositionAwareNode(new TicTacToeNode("         "));
        
        assertThat(new MaximizingTranspositionAlphaBeta(initialPosition, 10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue()).isEqualTo(0);
    }
    
    @Test
    public void mistakeOnTheFirstMoveIsFatal() throws Exception {
        TranspositionAwareNode initialPosition = new TranspositionAwareNode(
                new TicTacToeNode("X  "+
                                  "   "+
                                  "0  "));
                            
        assertThat(new MaximizingTranspositionAlphaBeta(initialPosition, 10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue()).isEqualTo(1);
        // using cache
        assertThat(new MaximizingTranspositionAlphaBeta(initialPosition, 10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue()).isEqualTo(1);
    }
}
