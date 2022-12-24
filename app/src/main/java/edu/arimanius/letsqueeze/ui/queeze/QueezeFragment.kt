package edu.arimanius.letsqueeze.ui.queeze

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.dao.QuestionWithAnswers
import edu.arimanius.letsqueeze.databinding.FragmentQueezeBinding

class QueezeFragment : Fragment() {
    private lateinit var queezeViewModel: QueezeViewModel

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

        queezeViewModel.queeze.observe(viewLifecycleOwner) { (queezeResultId, questions) ->
            queezeViewModel.currentQuestionIndex.observe(viewLifecycleOwner) {
                it ?: return@observe
                var index = it.toInt()
                binding.nextButton.setOnClickListener {
                    binding.questionMessage.text = ""
                    binding.nextButton.isEnabled = false
                    binding.submitButton.isEnabled = true
                    binding.questionRadioGroup.clearCheck()
                    binding.questionRadioGroup.children.forEach { it.isEnabled = true }

                    index++
                    when (index) {
                        questions.size -> {
                            findNavController().navigate(R.id.action_queezeFragment_to_queezeResultFragment)
                            return@setOnClickListener
                        }
                        questions.size - 1 -> {
                            binding.nextButton.text = "Finish"
                        }
                        else -> {
                            binding.nextButton.text = "Next"
                        }
                    }
                    queezeViewModel.setCurrentQuestionIndex(index)
                }
                updateQuestion(questions[index], queezeResultId, index)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateQuestion(
        currentQuestion: QuestionWithAnswers,
        queezeResultId: Int,
        index: Int
    ) {
        binding.questionNumber.text = "${index + 1}."
        binding.questionContent.text = currentQuestion.question.content

        val currentAnswers = currentQuestion.answers.shuffled()
        val correctIndex = currentAnswers.indexOfFirst { it.correct }
        listOf(
            binding.questionChoice1,
            binding.questionChoice2,
            binding.questionChoice3,
            binding.questionChoice4,
        ).forEachIndexed { index, radioButton ->
            radioButton.text = currentAnswers[index].content
            radioButton.text = currentAnswers[index].content
            if (index == correctIndex) {
                radioButton.setTag(R.id.is_correct, true)
            } else {
                radioButton.setTag(R.id.is_correct, false)
            }
            radioButton.setTag(R.id.answer_id, currentAnswers[index].id)
        }

        binding.submitButton.setOnClickListener {
            binding.submitButton.isEnabled = false
            binding.questionRadioGroup.children.forEach { it.isEnabled = false }

            val rbId = binding.questionRadioGroup.checkedRadioButtonId
            if (rbId == -1) {
                binding.questionMessage.text = "You didn't choose anything!"
                queezeViewModel.submitAnswer(queezeResultId, -1, 0)
            } else {
                val rb = binding.questionRadioGroup.findViewById<RadioButton>(rbId)
                val answerId = rb.getTag(R.id.answer_id) as Int
                if (rb.getTag(R.id.is_correct) as Boolean) {
                    binding.questionMessage.text = "Your answer is correct!"
                    queezeViewModel.submitAnswer(queezeResultId, answerId, 3)
                } else {
                    binding.questionMessage.text = "Your answer is incorrect!"
                    queezeViewModel.submitAnswer(queezeResultId, answerId, -1)
                }
            }

            binding.nextButton.isEnabled = true
        }
    }
}