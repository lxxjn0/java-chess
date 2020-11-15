package chess.domain.chessPiece.pieceType;

import chess.domain.MovableStrategy.QueenMovableStrategy;
import chess.domain.chessPiece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {
    @Test
    void Queen_PieceColor_GenerateInstance() {
        assertThat(new Queen(PieceColor.BLACK, new QueenMovableStrategy())).isInstanceOf(Queen.class);
    }

    @Test
    void getName_ReturnName() {
        assertThat(new Queen(PieceColor.BLACK, new QueenMovableStrategy()).getName()).isEqualTo("Q");
        assertThat(new Queen(PieceColor.WHITE, new QueenMovableStrategy()).getName()).isEqualTo("q");
    }
}