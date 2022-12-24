package edu.arimanius.letsqueeze.ui.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.dao.QuestionWithAnswers
import edu.arimanius.letsqueeze.data.entity.Answer
import edu.arimanius.letsqueeze.data.entity.SelectedAnswer


class QuestionListAdapter(
    val context: Context,
    private val questionList: List<QuestionWithAnswers>,
    private val selectedAnswers: List<SelectedAnswer>,
) : RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentQuestion = questionList[position]
        holder.itemView.findViewById<TextView>(R.id.question_number).text = "${position + 1}."
        holder.itemView.findViewById<TextView>(R.id.question_content).text =
            currentQuestion.question.content

        val answerViews = listOf<TextView>(
            holder.itemView.findViewById(R.id.question_result_choice_1),
            holder.itemView.findViewById(R.id.question_result_choice_2),
            holder.itemView.findViewById(R.id.question_result_choice_3),
            holder.itemView.findViewById(R.id.question_result_choice_4),
        )
        val answerId = getSelectedAnswerId(currentQuestion)
        currentQuestion.answers.forEachIndexed { index, answer ->
            if (answerId == answer.id && answer.correct) {
                answerViews[index].setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.green
                    )
                )
            } else if (answerId == answer.id) {
                answerViews[index].setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
            } else if (answer.correct) {
                answerViews[index].setBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.blue
                    )
                )
            }

            answerViews[index].text = answer.content
        }
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    private fun getSelectedAnswerId(question: QuestionWithAnswers): Int {
        question.answers.forEach { answer ->
            if (selectedAnswers.firstOrNull { it.answerId == answer.id } != null) {
                return answer.id
            }
        }
        return -1
    }
}