package test.s160419098.anmp.wu.cart

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Menu
import test.s160419098.anmp.wu.data.OrderItem
import test.s160419098.anmp.wu.databinding.ItemCartBinding

class CartItemAdapter(
    private val onQuantityChange: (menu: Menu, quantity: Int) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var items: List<OrderItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onQuantityChange = { pos, inc ->
            onQuantityChange(items[pos].menu, items[pos].quantity + inc)
        },
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.item = items[position]
    }

    fun updateItems(items: List<OrderItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemCartBinding,
        onQuantityChange: (position: Int, increment: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.decrementHandler = OnClickListener { onQuantityChange(adapterPosition, -1) }
            binding.incrementHandler = OnClickListener { onQuantityChange(adapterPosition, +1) }
        }
    }

}
