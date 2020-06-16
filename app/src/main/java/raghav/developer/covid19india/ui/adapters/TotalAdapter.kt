package raghav.developer.covid19india.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raghav.developer.covid19india.databinding.ItemTotalBinding
import raghav.developer.covid19india.model.StateResponse

class TotalAdapter : ListAdapter<StateResponse,TotalAdapter.TotalViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TotalViewHolder(
            ItemTotalBinding.inflate(Statewise
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) =
        holder.bind(getItem(position))


    class TotalViewHolder (private val binding: ItemTotalBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind (statewise: Statewise){
            binding.textConfirmed.text = statewise.confirmed
            binding.textActive.text = statewise.active
            binding.textDeceased.text = statewise.deaths
            binding.textRecovered.text = statewise.recovered

            //New confirmed
            statewise.deltaconfirmed.let {
                if(it == "0"){
                    binding.groupNewConfirmed.visibility = View.GONE
                }
                else{
                    binding.groupNewConfirmed.visibility = View.VISIBLE
                    binding.textNewConfirmed.text = statewise.deltaconfirmed
                }
            }
            //New deaths
            statewise.deltadeaths.let {
                if(it == "0"){
                    binding.groupNewDeaths.visibility = View.GONE
                }
                else{
                    binding.groupNewDeaths.visibility = View.VISIBLE
                    binding.textNewDeaths.text = statewise.deltadeaths
                }
            }
            //New recovered
            statewise.deltarecovered.let {
                if(it == "0"){
                    binding.groupNewRecovered.visibility = View.GONE
                }
                else{
                    binding.groupNewRecovered.visibility = View.VISIBLE
                    binding.textNewRecovered.text = statewise.deltarecovered
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