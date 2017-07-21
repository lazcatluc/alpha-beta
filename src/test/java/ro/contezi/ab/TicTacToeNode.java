package ro.contezi.ab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class TicTacToeNode implements ABNode {
    
    private final List<List<Character>> grid;
    private final Character myCharacter;
    private final Character myOpponentCharacter;
    private final int score;

    public TicTacToeNode(String str) {
        this.grid = Arrays.asList(fromChars(str.substring(0,3)), fromChars(str.substring(3,6)), fromChars(str.substring(6,9)));
        this.myCharacter = 'X';
        this.myOpponentCharacter = '0';
        this.score = 1;
    }
    
    private TicTacToeNode(TicTacToeNode original, int lineToAdd, int columnToAdd) {
        List<List<Character>> newGrid = new ArrayList<>(original.grid);
        List<Character> modifiedLine = new ArrayList<>(newGrid.get(lineToAdd));
        modifiedLine.set(columnToAdd, original.myCharacter);
        newGrid.set(lineToAdd, modifiedLine);
        this.grid = Collections.unmodifiableList(newGrid);
        this.myCharacter = original.myOpponentCharacter;
        this.myOpponentCharacter = original.myCharacter;
        this.score = -original.score;
    }
    
    private static List<Character> fromChars(String str) {
        List<Character> myList = new ArrayList<>();
        for(char ch : str.toCharArray()) {
            myList.add(ch);
        }
        return Collections.unmodifiableList(myList);
    }

    @Override
    public double heuristicValue() {
        return has3() ? score : 0;
    }

    @Override
    public boolean isTerminal() {
        return has3() || isFull();
    }

    private boolean isFull() {
        return grid.stream().allMatch(chars -> chars.stream().noneMatch(ch -> ch == ' '));
    }

    private boolean has3OnDiagonals() {
        return Arrays.asList(Arrays.asList(grid.get(0).get(0), grid.get(1).get(1), grid.get(2).get(2)),
               Arrays.asList(grid.get(2).get(0), grid.get(1).get(1), grid.get(0).get(2)))
                    .stream().anyMatch(chars -> chars.stream().allMatch(myCharacter::equals));
    }

    private boolean has3OnColumns() {
        return IntStream.range(0,3).anyMatch(column -> has3OnColumn(column, myCharacter));
    }

    private boolean has3OnColumn(int column, Character ch) {
        return grid.stream().map(list -> list.get(column)).allMatch(ch::equals);
    }

    private boolean has3OnLines() {
        return grid.stream().anyMatch(chars -> chars.stream().allMatch(myCharacter::equals));
    }

    private boolean has3() {
        return has3OnLines() || has3OnColumns() || has3OnDiagonals();
    }

    @Override
    public Collection<TicTacToeNode> children() {
        List<TicTacToeNode> ret = new ArrayList<>();
        IntStream.range(0,3).forEach(x ->
            IntStream.range(0,3).filter(y -> grid.get(x ).get(y) == ' ').forEach(y -> ret.add(new TicTacToeNode(this, x, y))));
        return ret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid.hashCode(), myCharacter.hashCode(), myOpponentCharacter.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TicTacToeNode other = (TicTacToeNode) obj;
        return Objects.equals(grid, other.grid) && 
               Objects.equals(myCharacter, other.myCharacter) && 
               Objects.equals(myOpponentCharacter, other.myOpponentCharacter);
    }

}
