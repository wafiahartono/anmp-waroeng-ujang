package test.s160419098.anmp.wu.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.data.Menu
import test.s160419098.anmp.wu.databinding.ItemMenuBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private var list: List<Menu> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false,
        ),
        onClickListener = { position ->
            parent.findNavController().navigate(
                MenuFragmentDirections.openMenuDetail(list[position].id)
            )
        },
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.menu = list[position]
    }

    fun updateList(list: List<Menu>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(this.list, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        val binding: ItemMenuBinding,
        onClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.clickHandler = View.OnClickListener { onClickListener(adapterPosition) }
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
