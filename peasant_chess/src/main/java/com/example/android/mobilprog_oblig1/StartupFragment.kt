package com.example.android.mobilprog_oblig1

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.mobilprog_oblig1.databinding.FragmentStartupBinding
import kotlin.system.exitProcess

class StartupFragment : Fragment() {
    private lateinit var binding: FragmentStartupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_startup, container, false)

        setHasOptionsMenu(true)

        binding.btnQuitGame.setOnClickListener { exitProcess(0) }

        binding.btnNewGame.setOnClickListener {
            findNavController().navigate(R.id.action_startupFragment_to_gameFragment)
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    // Handle toolbar actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                findNavController().navigate(R.id.action_startupFragment_to_preferenceFragment)
                true
            }
            R.id.about -> {
                findNavController().navigate(R.id.action_startupFragment_to_aboutFragment)
                true
            }
            R.id.quit_game -> {
                exitProcess(0)
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}