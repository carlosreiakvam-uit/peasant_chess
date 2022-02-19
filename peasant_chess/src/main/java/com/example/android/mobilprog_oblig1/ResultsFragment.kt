package com.example.android.mobilprog_oblig1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.mobilprog_oblig1.databinding.FragmentResultsBinding
import kotlin.system.exitProcess

class ResultsFragment : Fragment() {
    private lateinit var resultsHeader: TextView
    private lateinit var declareWinner: TextView
    private val args: ResultsFragmentArgs by navArgs()
    private lateinit var binding: FragmentResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("TAG", "MIKE TYSON")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.btnQuitGameFromResult.setOnClickListener { exitProcess(0) }
        binding.btnPlayAgain.setOnClickListener {
            findNavController().navigate(R.id.startupFragment)
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        resultsHeader = view.findViewById(R.id.results_header)
        declareWinner = view.findViewById(R.id.declare_winner)
        super.onViewCreated(view, savedInstanceState)
        resultsHeader.text = args.resultsHeader
        declareWinner.text = args.winnerDeclaration
    }
}