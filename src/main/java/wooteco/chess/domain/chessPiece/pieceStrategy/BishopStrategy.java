package wooteco.chess.domain.chessPiece.pieceStrategy;

import static wooteco.chess.domain.chessPiece.pieceType.PieceDirection.*;

import wooteco.chess.domain.position.Position;

public class BishopStrategy implements PieceStrategy {

	@Override
	public boolean canLeap() {
		return false;
	}

	@Override
	public boolean canMove(final Position sourcePosition, final Position targetPosition, final int pawnMovableRange) {
		return canMoveDirection(sourcePosition, targetPosition) && canMoveRange();
	}

	private boolean canMoveDirection(final Position sourcePosition, final Position targetPosition) {
		return DIAGONAL.getMovableDirections().stream()
			.anyMatch(moveDirection -> moveDirection.isSameDirectionFrom(sourcePosition, targetPosition));
	}

	private boolean canMoveRange() {
		return true;
	}

	@Override
	public boolean canCatch(final Position sourcePosition, final Position targetPosition) {
		return DIAGONAL.getCatchableDirections().stream()
			.anyMatch(moveDirection -> moveDirection.isSameDirectionFrom(sourcePosition, targetPosition));
	}

}
