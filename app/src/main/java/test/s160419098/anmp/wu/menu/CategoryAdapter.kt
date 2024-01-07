package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Category
import test.s160419098.anmp.wu.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var list: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = list[position]

        (holder.binding.recyclerViewMenu.adapter as MenuAdapter).updateList(list[position].items)
    }

    fun updateList(list: List<Category>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.recyclerViewMenu.apply {
                adapter = MenuAdapter()
                setHasFixedSize(true)
            }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<Category>,
        private val newList: List<Category>,
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
