package com.example.android.mobilprog_oblig1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.example.android.mobilprog_oblig1.databinding.FragmentGameBinding
import java.util.*
import kotlin.concurrent.timerTask


class GameFragment : Fragment() {

    private lateinit var timer: Timer
    private lateinit var binding: FragmentGameBinding

    private var seconds: Int = 0
    private var minutes: Int = 0
    private var secondsString: String = "00"
    private var minutesString: String = "00"

    private var playerTurn = 1
    private var hasWon = false

    private val game: TicTacToeGame = TicTacToeGame()
    private var totalMoves = 0
    private var noMovesPlayer1: Int = 0
    private var noMovesPlayer2: Int = 0
    private var board = game.boardDimension


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val playerSymbols: List<String> = getSymbolsFromPreferences()
        val playerOneSymbol: String = playerSymbols[0]
        val playerTwoSymbol: String = playerSymbols[1]

        loadSavedInstances(savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        setListeners(playerOneSymbol, playerTwoSymbol)
        timer()

        binding.playerTurnView.text = getPlayerTurnString(
            playerTurn,
            playerOneSymbol,
            playerTwoSymbol
        )


        binding.movePlayerOne.text = getString(
            R.string.number_of_moves,
            playerOneSymbol,
            noMovesPlayer1
        )
        binding.movePlayerTwo.text = getString(
            R.string.number_of_moves,
            playerTwoSymbol,
            noMovesPlayer2
        )

        resetBoard(playerOneSymbol, playerTwoSymbol)


        return binding.root
    }

    private fun getSymbolsFromPreferences(): List<String> {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(super.requireContext())

        val playerOneSymbol: String =
            sharedPreferences.getString("symbol_player_one", "A").toString()

        val playerTwoSymbol: String =
            sharedPreferences.getString("symbol_player_two", "B").toString()
        return listOf(playerOneSymbol, playerTwoSymbol)
    }


    private fun handleClick(
        view: Button,
        playerOneSymbol: String = "A",
        playerTwoSymbol: String = "B"
    ) {
        if (playerTurn == 1) {
            view.text = playerOneSymbol
        } else {
            view.text = playerTwoSymbol
        }
    }

    private fun openResultsFragmentDraw(view: View) {
        val navController = Navigation.findNavController(view)
        val action = GameFragmentDirections.actionGameFragmentToResultsFragment(
            resultsHeader = getString(R.string.its_a_draw), ""
        )

        navController.navigate(action)
    }

    private fun openResultsFragmentWinner(
        view: View,
        winner: Int,
        playerOneSymbol: String,
        playerTwoSymbol: String
    ) {
        val navController = Navigation.findNavController(view)
        val winnerString: String = if (winner == 1) {
            playerOneSymbol
        } else {
            playerTwoSymbol
        }

        val action = GameFragmentDirections.actionGameFragmentToResultsFragment(
            getString(R.string.we_have_a_winner),
            getString(R.string.player) + " ${winnerString}!"
        )
        navController.navigate(action)
    }

    private fun loadSavedInstances(savedInstanceState: Bundle?) {
        totalMoves = savedInstanceState?.getInt("totalMoves") ?: 0
        noMovesPlayer1 = savedInstanceState?.getInt("move1") ?: 0
        noMovesPlayer2 = savedInstanceState?.getInt("move2") ?: 0

        playerTurn = savedInstanceState?.getInt("playerTurn") ?: playerTurn
        board = savedInstanceState?.getStringArray("board") ?: game.boardDimension
        minutes = savedInstanceState?.getInt("minutes") ?: 0
        seconds = savedInstanceState?.getInt("seconds") ?: 0
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("totalMoves", totalMoves)
        outState.putInt("playerTurn", playerTurn)
        outState.putInt("move1", noMovesPlayer1)
        outState.putInt("move2", noMovesPlayer2)
        outState.putInt("minutes", minutes)
        outState.putInt("seconds", seconds)
        outState.putStringArray("board", board)
        game.updateBoard(board)

        super.onSaveInstanceState(outState)
    }


    private fun getPlayerTurnString(
        player: Int, playerOneSymbol: String,
        playerTwoSymbol: String
    ): String {
        val nextMove: String = getString(R.string.next_move)
        return if (player == 1) {
            nextMove + playerOneSymbol
        } else {
            nextMove + playerTwoSymbol
        }
    }


    private fun setListeners(playerOneSymbol: String = "A", playerTwoSymbol: String = "B") {
        var buttonList: List<Button>
        with(binding) {
            buttonList = listOf(
                button1, button2, button3, button4, button5, button6,
                button7, button8, button9
            )

            for (button in buttonList) {
                button.setOnClickListener {
                    totalMoves++

                    if (game.isValidMove(buttonList.indexOf(button))) {
                        handleClick(it as Button, playerOneSymbol, playerTwoSymbol)
                        hasWon = game.gameMove(playerTurn, buttonList.indexOf(button))
                        game.updateBoard(board)
                        if (hasWon) {
                            openResultsFragmentWinner(
                                it,
                                playerTurn,
                                playerOneSymbol,
                                playerTwoSymbol
                            )

                        } else if (totalMoves == 9) {
                            openResultsFragmentDraw(it)
                        }


                        if (playerTurn == 1) {
                            noMovesPlayer1++
                            binding.movePlayerOne.text = getString(
                                R.string.number_of_moves,
                                playerOneSymbol,
                                noMovesPlayer1
                            )
                        } else {
                            noMovesPlayer2++
                            binding.movePlayerTwo.text = getString(
                                R.string.number_of_moves,
                                playerTwoSymbol,
                                noMovesPlayer2
                            )
                        }

                        playerTurn = changePlayer(playerTurn)
                        binding.playerTurnView.text =
                            getPlayerTurnString(
                                playerTurn,
                                playerOneSymbol,
                                playerTwoSymbol
                            )
                    }
                }
            }
        }
    }

    private fun resetBoard(playerOneSymbol: String, playerTwoSymbol: String) {
        var buttonList: List<Button>
        with(binding, {
            buttonList = listOf(
                button1, button2, button3, button4, button5, button6,
                button7, button8, button9
            )
            for ((counter: Int, item: Button) in buttonList.withIndex()) {
                board[counter]

                val text: String = when {
                    board[counter] == "empty" -> {
                        ""
                    }
                    board[counter] == "1" -> {
                        playerOneSymbol
                    }
                    board[counter] == "2" -> {
                        playerTwoSymbol
                    }
                    else -> {
                        ""
                    }
                }
                item.text = text
            }
        })
    }

    private fun changePlayer(initPlayerTurn: Int): Int {
        playerTurn = if (initPlayerTurn == 1) {
            2
        } else {
            1
        }
        return playerTurn
    }

    private fun timer() {
        timer = Timer()
        timer.scheduleAtFixedRate(
            timerTask {
                seconds++
                if (seconds == 60) {
                    minutes++
                    seconds = 0
                }

                secondsString = if (seconds < 10) {
                    "0$seconds"
                } else seconds.toString()
                minutesString = if (minutes < 10) {
                    "0$minutes"
                } else minutes.toString()

                activity?.runOnUiThread {
                    binding.timerView.text =
                        getString(R.string.time_left, minutesString, secondsString)
                }

            },
            0,
            1000
        )
    }

}

