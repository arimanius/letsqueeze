package edu.arimanius.letsqueeze.ui.queeze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.repository.QueezeRepository
import edu.arimanius.letsqueeze.databinding.FragmentQueezeBinding
import edu.arimanius.letsqueeze.ui.profile.ProfileViewModel
import edu.arimanius.letsqueeze.ui.profile.ProfileViewModelFactory

class QueezeFragment : Fragment() {
    private lateinit var queezeViewModel: QueezeViewModel
    private lateinit var adapter: QuestionListAdapter

    private var _binding: FragmentQueezeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueezeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queezeViewModel = ViewModelProvider(
            this,
            QueezeViewModelFactory(requireContext())
        )[QueezeViewModel::class.java]

        adapter = QuestionListAdapter(requireContext())
        val recyclerView = binding.questions
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        queezeViewModel.queeze.observe(viewLifecycleOwner) { questions ->
            adapter.setQuestions(questions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}