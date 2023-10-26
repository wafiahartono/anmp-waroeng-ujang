package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.loremFlickr
import test.s160419098.anmp.wu.toCurrency

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private var list: List<Menu> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false),
            onClickListener = { position ->
                parent.findNavController().navigate(
                    MenuFragmentDirections.openMenuDetail(list[position].id)
                )
            },
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            menuImage.load(list[position].name.loremFlickr(400))
            menuNameText.text = list[position].name
            menuPriceText.text = list[position].price.toCurrency()
        }
    }

    fun updateList(list: List<Menu>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        view: View,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        val menuImage: ImageView = view[R.id.image_menu]
        val menuNameText: TextView = view[R.id.text_menu_name]
        val menuPriceText: TextView = view[R.id.text_menu_price]

        init {
            view.setOnClickListener { onClickListener(adapterPosition) }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<Menu>,
        private val newList: List<Menu>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].id == newList[pos1].id
        }

        override fun areContentsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].id == newList[pos1].id
        }
    }
}