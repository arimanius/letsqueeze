package edu.arimanius.letsqueeze.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.databinding.FragmentQueezeResultBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QueezeResultFragment : Fragment() {

    private var _binding: FragmentQueezeResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQueezeResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val queezeResultViewModel = ViewModelProvider(
            this,
            QueezeResultViewModelFactory(requireContext())
        )[QueezeResultViewModel::class.java]

        CoroutineScope(Dispatchers.Main).launch {
            val questionWithAnswers = queezeResultViewModel.getOngoingQuestionsWithAnswers()
            val selectedAnswers = queezeResultViewModel.getOngoingSelectedAnswers()
            queezeResultViewModel.unsetOngoingQueezeId()
            val adapter = QuestionListAdapter(requireContext(), questionWithAnswers, selectedAnswers)
            val recyclerView = view.findViewById<RecyclerView>(R.id.queeze_result_list)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}