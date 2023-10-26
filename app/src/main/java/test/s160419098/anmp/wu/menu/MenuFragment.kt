package test.s160419098.anmp.wu.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.TextInput
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.get

class MenuFragment : Fragment() {
    private val cartViewModel: CartViewModel by activityViewModels()
    private val menuViewModel: MenuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableNumberText: TextView = view[R.id.text_table_number]
        val changeTableButton: Button = view[R.id.button_change_table_number]
        val searchDishTextInput = TextInput(
            view, R.id.text_input_layout_search_dish, R.id.edit_text_search_dish,
        )
        val menuCategoryRecyclerView: RecyclerView = view[R.id.recycler_view_menu_category]

        menuCategoryRecyclerView.adapter = MenuCategoryAdapter()

        changeTableButton.setOnClickListener {
            findNavController().navigate(MenuFragmentDirections.changeTable())
        }

        searchDishTextInput.editText.doAfterTextChanged {
            menuViewModel.menuCategoriesQuery.value = it.toString()
        }

        cartViewModel.table.observe(viewLifecycleOwner) {
            tableNumberText.text =
                if (it == null) getString(R.string.no_table_selected)
                else getString(R.string.template_table_number, it)
        }

        menuViewModel.menuCategories.observe(viewLifecycleOwner) {
            (menuCategoryRecyclerView.adapter as MenuCategoryAdapter).updateList(it)
        }
    }
}