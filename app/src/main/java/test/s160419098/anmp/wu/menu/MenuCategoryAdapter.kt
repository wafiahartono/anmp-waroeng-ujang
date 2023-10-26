package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get

class MenuCategoryAdapter : RecyclerView.Adapter<MenuCategoryAdapter.ViewHolder>() {
    private var list: List<MenuCategory> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_category, parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            categoryNameText.text = list[position].name
            (menuRecyclerView.adapter as MenuAdapter).updateList(list[position].items)
        }
    }

    fun updateList(list: List<MenuCategory>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryNameText: TextView = view[R.id.text_category_name]
        val menuRecyclerView: RecyclerView = view[R.id.recycler_view_menu]

        init {
            menuRecyclerView.adapter = MenuAdapter()
            menuRecyclerView.setHasFixedSize(true)
        }
    }

    class DiffUtilCallback(
        private val oldList: List<MenuCategory>,
        private val newList: List<MenuCategory>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].id == newList[pos1].id
        }

        override fun areContentsTheSame(pos0: Int, pos1: Int): Boolean {
            return oldList[pos0].items.size == newList[pos1].items.size
        }
    }
}