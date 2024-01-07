package test.s160419098.anmp.wu.order

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.databinding.ItemOrderBinding

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var list: List<Order> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onClickListener = { position ->
            parent.findNavController().navigate(
                OrderFragmentDirections.openOrderDetail(list[position].table)
            )
        },
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.order = list[position]

        holder.binding.textOrderDuration.base = list[position].datetime
        holder.binding.textOrderDuration.start()
    }

    fun updateList(list: List<Order>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        val binding: ItemOrderBinding,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickHandler = OnClickListener { onClickListener(adapterPosition) }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<Order>,
        private val newList: List<Order>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].table == newList[pos1].table
        }

        override fun areContentsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].items.size == newList[pos1].items.size
        }
    }
}
