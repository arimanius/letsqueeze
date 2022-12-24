package edu.arimanius.letsqueeze.ui.scoreboard

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.entity.User
import edu.arimanius.letsqueeze.databinding.FragmentScoreboardBinding

class MyScoreboardRecyclerViewAdapter : RecyclerView.Adapter<MyScoreboardRecyclerViewAdapter.ViewHolder>() {

    private var users: List<User> = emptyList()
    private var currentUserId: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentScoreboardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    fun updateCurrentUserId(userId: Int) {
        currentUserId = userId
        notifyDataSetChanged()
    }

    fun updateUsers(values: List<User>) {
        this.users = values
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        if (position < 3) {
            holder.root.setBackgroundColor(
                ContextCompat.getColor(
                    holder.root.context,
                    when (position) {
                        0 -> R.color.gold
                        1 -> R.color.silver
                        else -> R.color.bronze
                    }
                )
            )
        }
        holder.itemNumberView.text = (position + 1).toString()
        holder.userDisplayNameView.text = user.displayName ?: user.username
        holder.userScoreView.text = user.score.toString()
        if (user.id == currentUserId) {
            holder.itemNumberView.setTextColor(
                ContextCompat.getColor(
                    holder.itemNumberView.context,
                    R.color.teal_200,
                )
            )
            holder.userDisplayNameView.setTextColor(
                ContextCompat.getColor(
                    holder.userDisplayNameView.context,
                    R.color.teal_200,
                )
            )
            holder.userScoreView.setTextColor(
                ContextCompat.getColor(
                    holder.userScoreView.context,
                    R.color.teal_200,
                )
            )
        }
    }

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(binding: FragmentScoreboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root: ConstraintLayout = binding.root
        val itemNumberView: TextView = binding.itemNumber
        val userDisplayNameView: TextView = binding.userDisplayName
        val userScoreView: TextView = binding.userScore

        override fun toString(): String {
            return super.toString() + " '" + userDisplayNameView.text + "'"
        }
    }

}