package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Menu
import test.s160419098.anmp.wu.databinding.ItemMenuBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var menus: List<Menu> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        onClickListener = {
            parent.findNavController().navigate(
                MenuFragmentDirections.openMenuDetail(menus[it].id)
            )
        },
    )

    override fun getItemCount() = menus.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menu = menus[position]
    }

    fun updateMenus(menus: List<Menu>) {
        this.menus = menus
        notifyDataSetChanged()
    }

    class ViewHolder(
        val binding: ItemMenuBinding,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickHandler = OnClickListener { onClickListener(adapterPosition) }
        }
    }

}
