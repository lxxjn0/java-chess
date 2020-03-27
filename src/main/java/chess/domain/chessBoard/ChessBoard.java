package chess.domain.chessBoard;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import chess.domain.chessPiece.ChessPiece;
import chess.domain.chessPiece.pieceType.Pawn;
import chess.domain.chessPiece.pieceType.PieceColor;
import chess.domain.position.ChessFile;
import chess.domain.position.MoveDirection;
import chess.domain.position.Position;

public class ChessBoard {

	private final Map<Position, ChessPiece> chessBoard;

	public ChessBoard(Map<Position, ChessPiece> chessBoard) {
		this.chessBoard = chessBoard;
	}

	public void move(Position sourcePosition, Position targetPosition) {
		validate(sourcePosition, targetPosition);

		ChessPiece sourceChessPiece = findSourceChessPieceFrom(sourcePosition);
		checkLeapable(sourceChessPiece, sourcePosition, targetPosition);
		checkMovableOrCatchable(sourceChessPiece, sourcePosition, targetPosition);
		moveChessPiece(sourceChessPiece, sourcePosition, targetPosition);
	}

	private void validate(Position sourcePosition, Position targetPosition) {
		Objects.requireNonNull(sourcePosition, "소스 위치가 null입니다.");
		Objects.requireNonNull(targetPosition, "타겟 위치가 null입니다.");
	}

	private ChessPiece findSourceChessPieceFrom(Position sourcePosition) {
		ChessPiece sourceChessPiece = chessBoard.get(sourcePosition);

		if (Objects.isNull(chessBoard.get(sourcePosition))) {
			throw new IllegalArgumentException("해당 위치에 체스 피스가 존재하지 않습니다.");
		}
		return sourceChessPiece;
	}

	private void checkLeapable(ChessPiece sourceChessPiece, Position sourcePosition, Position targetPosition) {
		if (!sourceChessPiece.canLeap()) {
			checkChessPieceRoute(sourcePosition, targetPosition);
		}
	}

	private void checkChessPieceRoute(Position sourcePosition, Position targetPosition) {
		MoveDirection checkingDirection = findDirectionOf(sourcePosition, targetPosition);
		Position checkingPosition = checkingDirection.move(sourcePosition);

		while (!checkingPosition.equals(targetPosition) && isChessPieceNotExistAt(checkingPosition)) {
			checkingPosition = checkingDirection.move(checkingPosition);
		}
	}

	private MoveDirection findDirectionOf(Position sourcePosition, Position targetPosition) {
		return Arrays.stream(MoveDirection.values())
			.filter(moveDirection -> moveDirection.isSameDirectionFrom(sourcePosition, targetPosition))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException("체스 피스가 이동할 수 없는 위치를 입력하였습니다."));
	}

	private boolean isChessPieceNotExistAt(Position checkingPosition) {
		if (Objects.nonNull(chessBoard.get(checkingPosition))) {
			throw new NullPointerException("체스 피스의 이동 경로에 다른 체스 피스가 존재합니다.");
		}
		return true;
	}

	private void checkMovableOrCatchable(ChessPiece sourceChessPiece, Position sourcePosition,
		Position targetPosition) {
		ChessPiece targetChessPiece = chessBoard.get(targetPosition);

		if (Objects.nonNull(targetChessPiece)) {
			checkIsSamePieceColorWith(sourceChessPiece, targetChessPiece);
			checkCanCatchWith(sourceChessPiece, sourcePosition, targetPosition);
			return;
		}
		checkCanMoveWith(sourceChessPiece, sourcePosition, targetPosition);
	}

	private void checkIsSamePieceColorWith(ChessPiece sourceChessPiece, ChessPiece targetChessPiece) {
		if (sourceChessPiece.isSamePieceColorWith(targetChessPiece)) {
			throw new IllegalArgumentException("체스 피스가 이동할 수 없습니다.");
		}
	}

	private void checkCanCatchWith(ChessPiece sourceChessPiece, Position sourcePosition, Position targetPosition) {
		if (!sourceChessPiece.canCatch(sourcePosition, targetPosition)) {
			throw new IllegalArgumentException("체스 피스가 이동할 수 없습니다.");
		}
		// TODO: 2020/03/28 isKingCatchChessPiece!? -> throw new KingCatchedException();
	}

	private void checkCanMoveWith(ChessPiece sourceChessPiece, Position sourcePosition, Position targetPosition) {
		if (!sourceChessPiece.canMove(sourcePosition, targetPosition)) {
			throw new IllegalArgumentException("체스 피스가 이동할 수 없습니다.");
		}
	}

	private void moveChessPiece(ChessPiece sourceChessPiece, Position sourcePosition, Position targetPosition) {
		chessBoard.put(targetPosition, sourceChessPiece);
		chessBoard.remove(sourcePosition);
	}

	public double calculateScoreOf(PieceColor pieceColor) {
		return ChessFile.values().stream()
			.map(chessFile -> getChessPiecesOn(chessFile, pieceColor))
			.mapToDouble(this::calculateTotalScore)
			.sum();
	}

	private Stream<ChessPiece> getChessPiecesOn(ChessFile chessFile, PieceColor pieceColor) {
		return chessBoard.entrySet().stream()
			.filter(entry -> entry.getKey().isSameFilePosition(chessFile))
			.map(Map.Entry::getValue)
			.filter(chessPiece -> chessPiece.isSamePieceColorWith(pieceColor));
	}

	private double calculateTotalScore(Stream<ChessPiece> chessPieceStream) {
		double totalScore = chessPieceStream.mapToDouble(ChessPiece::getScore).sum();

		Stream<ChessPiece> pawns = chessPieceStream.filter(chessPiece -> chessPiece instanceof Pawn);
		double pawnTotalScore = pawns.mapToDouble(ChessPiece::getScore).sum();

		if (pawns.count() > 1) {
			return totalScore - (pawnTotalScore / 2);
		}
		return totalScore;
	}

}
