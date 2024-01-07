package test.s160419098.anmp.wu.cart

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.OrderItem
import test.s160419098.anmp.wu.databinding.ItemCartBinding

class CartItemAdapter(
    private val onItemChange: (item: OrderItem) -> Unit,
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private var list: List<OrderItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onQuantityChange = { position, direction ->
            onItemChange(list[position].copy(quantity = list[position].quantity + direction))
        },
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = list[position]
    }

    fun updateList(list: List<OrderItem>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        val binding: ItemCartBinding,
        onQuantityChange: (position: Int, direction: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.decrementHandler = OnClickListener { onQuantityChange(adapterPosition, -1) }
            binding.incrementHandler = OnClickListener { onQuantityChange(adapterPosition, +1) }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<OrderItem>,
        private val newList: List<OrderItem>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].menu.id == newList[pos1].menu.id
        }

        override fun areContentsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].quantity == newList[pos1].quantity
        }
    }
}
