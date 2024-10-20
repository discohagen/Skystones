import java.awt.Dimension

data class GameBoardField(val card: Card, var player: Player?)
typealias GameBoardRow = Array<GameBoardField?>

class Move(val card: Card, val player: Player, val row: Int, val col: Int)

class GameBoard(private val dimension: Dimension) {
    private var board: Array<GameBoardRow> =
        Array(dimension.height) { arrayOfNulls<GameBoardField>(dimension.width) }

    fun placeCard(move: Move) {
        if (isValidMove(move)) {
            board[move.row][move.col] = GameBoardField(move.card, move.player)
            if (!coordinatesOutOfBounds(move.row - 1, move.col) && fieldOccupied(
                    move.row - 1,
                    move.col
                ) && move.card.beats(
                    board[move.row - 1][move.col]?.card, Direction.NORTH
                )
            ) {
                board[move.row - 1][move.col]?.player = move.player
            }
            if (!coordinatesOutOfBounds(move.row, move.col - 1) && fieldOccupied(move.row, move.col - 1)
                && move.card.beats(board[move.row][move.col - 1]?.card, Direction.WEST)
            ) {
                board[move.row][move.col - 1]?.player = move.player
            }
            if (!coordinatesOutOfBounds(move.row + 1, move.col) && fieldOccupied(move.row + 1, move.col)
                && move.card.beats(board[move.row + 1][move.col]?.card, Direction.SOUTH)
            ) {
                board[move.row + 1][move.col]?.player = move.player
            }
            if (!coordinatesOutOfBounds(move.row, move.col + 1) && fieldOccupied(move.row, move.col + 1)
                && move.card.beats(board[move.row][move.col + 1]?.card, Direction.EAST)
            ) {
                board[move.row][move.col + 1]?.player = move.player
            }
        }
    }

    private fun boardFull(): Boolean {
        return board.all { row -> row.all { field -> field != null } }
    }

    private fun fieldOccupied(row: Int, col: Int): Boolean {
        return board[row][col] != null
    }

    private fun coordinatesOutOfBounds(row: Int, col: Int): Boolean {
        return row !in 0..dimension.height || col !in 0..dimension.width
    }

    private fun isValidMove(move: Move): Boolean {
        return !coordinatesOutOfBounds(move.row, move.col) && !fieldOccupied(move.row, move.col) && !boardFull()
    }
}