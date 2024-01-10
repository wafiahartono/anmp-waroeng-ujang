package test.s160419098.anmp.wu.order

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Order
import test.s160419098.anmp.wu.databinding.ItemOrderBinding

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var orders: List<Order> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onClickListener = { pos ->
            parent.findNavController().navigate(
                OrderFragmentDirections.openOrderDetail(orders[pos].id)
            )
        },
    )

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.order = orders[position]
    }

    fun updateOrders(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemOrderBinding,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickHandler = OnClickListener { onClickListener(adapterPosition) }
        }
    }

}
