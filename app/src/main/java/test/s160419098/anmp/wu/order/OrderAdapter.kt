package test.s160419098.anmp.wu.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.toCurrency

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var list: List<Order> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false),
            onClickListener = { position ->
                parent.findNavController().navigate(
                    OrderFragmentDirections.openOrderDetail(list[position].table)
                )
            },
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            tableNumberText.text = list[position].table.toString()
            orderTotalText.text =
                (list[position].items.fold(0) { acc, item -> acc + item.menu.price * item.quantity } * 1.1F).toCurrency()
            orderDurationChrono.base = list[position].processedAt
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.orderDurationChrono.start()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.orderDurationChrono.stop()
    }

    fun updateList(list: List<Order>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        view: View,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        val tableNumberText: TextView = view[R.id.text_table_number]
        val orderTotalText: TextView = view[R.id.text_order_total]
        val orderDurationChrono: Chronometer = view[R.id.text_order_duration]

        init {
            view.setOnClickListener { onClickListener(adapterPosition) }
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