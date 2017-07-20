package ro.contezi.ab;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TicTacToeABTest {
    @Test
    public void initialPositionIsEqual() throws Exception {
        TicTacToeNode initialPosition = new TicTacToeNode("         ");
        
        assertThat(new MaximizingAlphaBeta(initialPosition, 10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue()).isEqualTo(0);
    }
    
    @Test
    public void mistakeOnTheFirstMoveIsFatal() throws Exception {
        TicTacToeNode initialPosition = new TicTacToeNode("X  "+
                                                          "   "+
                                                          "0  ");
        
        assertThat(new MaximizingAlphaBeta(initialPosition, 10, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getValue()).isEqualTo(1);
    }
   
}
