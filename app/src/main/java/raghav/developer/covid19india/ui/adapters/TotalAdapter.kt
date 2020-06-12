package dev.shreyaspatil.covid19notify.ui.adapter

import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raghav.developer.covid19india.databinding.ItemTotalBinding
import raghav.developer.covid19india.model.Statewise


class TotalAdapter : ListAdapter<Statewise,TotalAdapter.TotalViewHolder>(DIFF_CALLBACK) {


    class TotalViewHolder (private val binding: ItemTotalBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(statewise: Statewise){
            binding.textConfirmed.text = statewise.confirmed
            binding.textActive.text = statewise.active
            binding.textRecovered.text = statewise.recovered
            binding.textDeceased.text = statewise.deaths
        }
    }

}