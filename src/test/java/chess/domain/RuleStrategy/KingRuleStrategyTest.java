package chess.domain.RuleStrategy;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import chess.domain.position.Position;

class KingRuleStrategyTest {

	@Test
	void KingRuleStrategy_GenerateInstance() {
		assertThat(new KingRuleStrategy()).isInstanceOf(KingRuleStrategy.class);
	}

	@ParameterizedTest
	@NullSource
	void validate_NullSourcePosition_ExceptionThrown(Position sourcePosition) {
		Position targetPosition = Position.of("b1");

		assertThatThrownBy(() -> new KingRuleStrategy().canMove(sourcePosition, targetPosition))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("소스 위치가 존재하지 않습니다.");
	}

	@ParameterizedTest
	@NullSource
	void validate_NullTargetPosition_ExceptionThrown(Position targetPosition) {
		Position sourcePosition = Position.of("b1");

		assertThatThrownBy(() -> new KingRuleStrategy().canMove(sourcePosition, targetPosition))
			.isInstanceOf(NullPointerException.class)
			.hasMessage("타겟 위치가 존재하지 않습니다.");
	}

	@ParameterizedTest
	@CsvSource(value = {"a4", "a3", "b3", "c3", "c4", "c5", "b5", "a5"})
	void canMove_MovableSourcePositionAndTargetPosition_ReturnTrue(Position targetPosition) {
		KingRuleStrategy kingRuleStrategy = new KingRuleStrategy();
		Position sourcePosition = Position.of("b4");

		assertThat(kingRuleStrategy.canMove(sourcePosition, targetPosition)).isTrue();
	}

	@ParameterizedTest
	@CsvSource(value = {"d6", "a2", "a6", "d2", "d4"})
	void canMove_NonMovableSourcePositionAndTargetPosition_ReturnFalse(Position targetPosition) {
		KingRuleStrategy kingRuleStrategy = new KingRuleStrategy();
		Position sourcePosition = Position.of("b4");

		assertThat(kingRuleStrategy.canMove(sourcePosition, targetPosition)).isFalse();
	}

	@Test
	void canLeap_ReturnTrue() {
		assertThat(new KingRuleStrategy().canLeap()).isTrue();
	}

}