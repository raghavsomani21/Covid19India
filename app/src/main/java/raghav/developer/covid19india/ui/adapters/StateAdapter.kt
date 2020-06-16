package raghav.developer.covid19india.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raghav.developer.covid19india.R
import raghav.developer.covid19india.databinding.ItemStateBinding
import raghav.developer.covid19india.utils.getPeriod

class StateAdapter(val clickListener: (statewise: Statewise) -> Unit = {}) :
    ListAdapter<Statewise, StateAdapter.StateViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StateViewHolder(
        ItemStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) =
        holder.bind(getItem(position))


    inner class StateViewHolder(private val binding: ItemStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(details: Statewise) {
            binding.textState.text = details.state
            binding.textLastUpdatedView.text = itemView.context.getString(
                R.string.text_last_updated,
                getPeriod(
                    details.lastupdatedtime.toDateFormat()
                )
            )

            binding.textConfirmed.text = details.confirmed
            binding.textActive.text = details.active
            binding.textRecovered.text = details.recovered
            binding.textDeath.text = details.deaths

            // New Confirmed
            details.deltaconfirmed.let {
                if (it == "0") {
                    binding.groupStateNewConfirm.visibility = View.GONE
                } else {
                    binding.groupStateNewConfirm.visibility = View.VISIBLE
                    binding.textStateNewConfirm.text = it
                }
            }

            // New Recovered
            details.deltarecovered.let {
                if (it == "0") {
                    binding.groupStateNewRecover.visibility = View.GONE
                } else {
                    binding.groupStateNewRecover.visibility = View.VISIBLE
                    binding.textStateNewRecover.text = it
                }
            }

            // New Deaths
            details.deltadeaths.let {
                if (it == "0") {
                    binding.groupStateNewDeaths.visibility = View.GONE
                } else {
                    binding.groupStateNewDeaths.visibility = View.VISIBLE
                    binding.textStateNewDeath.text = it
                }
            }

            // Set Click Listener
            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }

                val item = getItem(bindingAdapterPosition)
                item.let {
                    clickListener.invoke(it)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Statewise>() {
            override fun areItemsTheSame(oldItem: Statewise, newItem: Statewise): Boolean =
                oldItem.state == newItem.state

            override fun areContentsTheSame(oldItem: Statewise, newItem: Statewise): Boolean =
                oldItem == newItem
        }
    }
}
