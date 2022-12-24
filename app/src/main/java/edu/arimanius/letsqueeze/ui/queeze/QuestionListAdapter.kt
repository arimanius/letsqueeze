package edu.arimanius.letsqueeze.ui.queeze

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.dao.QuestionWithAnswers


class QuestionListAdapter(val context: Context) :
    RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {
    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var questionList = emptyList<QuestionWithAnswers>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val currentQuestion = questionList[position]
        holder.itemView.findViewById<TextView>(R.id.question_number).text = "${position + 1}."
        holder.itemView.findViewById<TextView>(R.id.question_content).text =
            currentQuestion.question.content
        val currentAnswers = currentQuestion.answers.shuffled()
        val correctIndex = currentAnswers.indexOfFirst { it.correct }
        listOf<RadioButton>(
            holder.itemView.findViewById(R.id.question_choice_1),
            holder.itemView.findViewById(R.id.question_choice_2),
            holder.itemView.findViewById(R.id.question_choice_3),
            holder.itemView.findViewById(R.id.question_choice_4),
        ).forEachIndexed { index, radioButton ->
            radioButton.text = currentAnswers[index].content
            if (index == correctIndex) {
                radioButton.tag = "correct"
            } else {
                radioButton.tag = "incorrect"
            }
        }

    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}