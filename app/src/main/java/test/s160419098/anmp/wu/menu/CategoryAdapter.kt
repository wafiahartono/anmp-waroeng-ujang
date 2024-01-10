package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Category
import test.s160419098.anmp.wu.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.category = categories[position]

        (holder.binding.recyclerViewMenu.adapter as MenuAdapter).updateMenus(categories[position].items)
    }

    fun updateCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
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

}
