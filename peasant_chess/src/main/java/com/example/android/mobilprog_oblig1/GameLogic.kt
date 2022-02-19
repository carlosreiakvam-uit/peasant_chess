package com.example.android.mobilprog_oblig1

class TicTacToeGame {
    private var playerWon = false
    private var board = Array(3) { Array(3) { "0" } }
    var boardDimension = Array(9) { "0" }

    fun updateBoard(updatedBoard: Array<String>) {
        boardDimension = updatedBoard

        for ((boardPlacement, item) in boardDimension.withIndex()) {
            if (item == "1") {
                board[boardPlacement / 3][boardPlacement % 3] = item
            } else if (item == "2") {
                board[boardPlacement / 3][boardPlacement % 3] = item
            }
        }
    }

    fun gameMove(playerTurn: Int, boardPlacement: Int): Boolean {
        board[boardPlacement / 3][boardPlacement % 3] = playerTurn.toString()
        boardDimension[boardPlacement] = playerTurn.toString()
        playerWon = checkIfWon(playerTurn.toString())
        return playerWon
    }

    fun isValidMove(boardPlacement: Int): Boolean {
        val row = boardPlacement / 3
        val column = boardPlacement % 3

        if (row in 0..2 && column in 0..2) {
            if (board[row][column] == "0") {
                return true
            }
        }
        return false
    }

    private fun checkRows(symbol: String): Int {
        val row: Int
        val cond1: Boolean =
            board[0][0].contains(symbol) && board[0][1].contains(symbol) && board[0][2].contains(
                symbol
            )
        val cond2: Boolean =
            board[1][0].contains(symbol) && board[1][1].contains(symbol) && board[1][2].contains(
                symbol
            )
        val cond3: Boolean =
            board[2][0].contains(symbol) && board[2][1].contains(symbol) && board[2][2].contains(
                symbol
            )

        row = when {
            cond1 -> { 1 }
            cond2 -> { 2 }
            cond3 -> { 3 }
            else -> -1
        }
        return row
    }

    private fun checkColumns(symbol: String): Int {
        val column: Int
        val cond1: Boolean =
            board[0][0].contains(symbol) && board[1][0].contains(symbol) && board[2][0].contains(
                symbol
            )
        val cond2: Boolean =
            board[0][1].contains(symbol) && board[1][1].contains(symbol) && board[2][1].contains(
                symbol
            )
        val cond3: Boolean =
            board[0][2].contains(symbol) && board[1][2].contains(symbol) && board[2][2].contains(
                symbol
            )

        column = when {
            cond1 -> 1
            cond2 -> 2
            cond3 -> 3
            else -> -1
        }
        return column
    }

    private fun checkDiagonals(symbol: String): Int {
        val diagonal: Int
        val cond1: Boolean =
            board[0][0].contains(symbol) && board[1][1].contains(symbol) && board[2][2].contains(
                symbol
            )
        val cond2: Boolean =
            board[2][0].contains(symbol) && board[1][1].contains(symbol) && board[0][2].contains(
                symbol
            )
        diagonal = when {
            cond1 -> 1
            cond2 -> 2
            else -> -1
        }
        return diagonal
    }

    private fun checkIfWon(symbol: String): Boolean {
        var hasWon = false
        when {
            checkRows(symbol) != -1 -> {
                hasWon = true
            }
            checkColumns(symbol) != -1 -> {
                hasWon = true
            }
            checkDiagonals(symbol) != -1 -> {
                hasWon = true
            }
        }
        return hasWon
    }


}