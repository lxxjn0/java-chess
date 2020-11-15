package chess.domain.chessPiece.pieceType;

import chess.domain.RuleStrategy.nonLeapableStrategy.QueenRuleStrategy;
import chess.domain.chessPiece.PieceColor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueenTest {
    @Test
    void Queen_PieceColor_GenerateInstance() {
        assertThat(new Queen(PieceColor.BLACK, new QueenRuleStrategy())).isInstanceOf(Queen.class);
    }

    @Test
    void getName_ReturnName() {
        assertThat(new Queen(PieceColor.BLACK, new QueenRuleStrategy()).getName()).isEqualTo("Q");
        assertThat(new Queen(PieceColor.WHITE, new QueenRuleStrategy()).getName()).isEqualTo("q");
    }
}