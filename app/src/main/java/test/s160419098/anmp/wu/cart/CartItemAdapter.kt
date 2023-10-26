package test.s160419098.anmp.wu.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.loremFlickr
import test.s160419098.anmp.wu.toCurrency

class CartItemAdapter(
    private val onItemChange: (item: CartItem) -> Unit,
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private var list: List<CartItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false),
            onQuantityChange = { position, direction ->
                onItemChange(list[position].copy(quantity = list[position].quantity + direction))
            },
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            itemImage.load(list[position].menu.name.loremFlickr(400))
            itemNameText.text = list[position].menu.name
            itemPriceText.text = list[position].menu.price.toCurrency()
            itemQuantityText.text = list[position].quantity.toString()
        }
    }

    fun updateList(list: List<CartItem>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        view: View,
        onQuantityChange: (position: Int, direction: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view[R.id.image_item]
        val itemNameText: TextView = view[R.id.text_item_name]
        val itemPriceText: TextView = view[R.id.text_item_price]
        private val decrementItemQuantityButton: Button = view[R.id.button_decrement_item_quantity]
        val itemQuantityText: TextView = view[R.id.text_item_quantity]
        private val incrementItemQuantityButton: Button = view[R.id.button_increment_item_quantity]

        init {
            decrementItemQuantityButton.setOnClickListener { onQuantityChange(adapterPosition, -1) }
            incrementItemQuantityButton.setOnClickListener { onQuantityChange(adapterPosition, +1) }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<CartItem>,
        private val newList: List<CartItem>,
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