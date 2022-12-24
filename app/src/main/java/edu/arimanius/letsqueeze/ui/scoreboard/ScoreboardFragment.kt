package edu.arimanius.letsqueeze.ui.scoreboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import edu.arimanius.letsqueeze.R

class ScoreboardFragment : Fragment() {

    private lateinit var scoreboardViewModel: ScoreboardViewModel
    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scoreboard_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyScoreboardRecyclerViewAdapter()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scoreboardViewModel = ViewModelProvider(
            this,
            ScoreboardViewModelFactory(requireContext())
        )[ScoreboardViewModel::class.java]

        scoreboardViewModel.users.observe(viewLifecycleOwner) {
            it ?: return@observe
            ((view as RecyclerView).adapter as MyScoreboardRecyclerViewAdapter).updateUsers(it)
        }
        scoreboardViewModel.loggedInUser.observe(viewLifecycleOwner) {
            ((view as RecyclerView).adapter as MyScoreboardRecyclerViewAdapter).updateCurrentUserId(
                it?.id ?: -1
            )
        }
    }
}