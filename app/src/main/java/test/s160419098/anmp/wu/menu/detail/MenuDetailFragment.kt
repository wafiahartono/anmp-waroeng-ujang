package test.s160419098.anmp.wu.menu.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.cart.CartItem
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.loremFlickr
import test.s160419098.anmp.wu.toCurrency

class MenuDetailFragment : Fragment() {
    private val cartViewModel: CartViewModel by activityViewModels()
    private val menuDetailViewModel: MenuDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuDetailViewModel.menuId.value =
            MenuDetailFragmentArgs.fromBundle(requireArguments()).menuId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuImage: ImageView = view[R.id.image_menu]
        val menuNameText: TextView = view[R.id.text_menu_name]
        val menuDescriptionText: TextView = view[R.id.text_menu_description]
        val menuPriceText: TextView = view[R.id.text_menu_price]
        val decrementMenuQuantityButton: Button = view[R.id.button_decrement_menu_quantity]
        val menuQuantityText: TextView = view[R.id.text_menu_quantity]
        val incrementMenuQuantityButton: Button = view[R.id.button_increment_menu_quantity]
        val addToCartButton: Button = view[R.id.button_add]

        decrementMenuQuantityButton.setOnClickListener { menuDetailViewModel.decrementQuantity() }
        incrementMenuQuantityButton.setOnClickListener { menuDetailViewModel.incrementQuantity() }

        addToCartButton.setOnClickListener {
            val menu = menuDetailViewModel.menu.value!!
            val quantity = menuDetailViewModel.menuQuantity.value!!
            this.cartViewModel.setItem(CartItem(menu, quantity))
            Toast.makeText(
                requireContext(),
                getString(R.string.template_menu_added, quantity, menu.name),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }

        this.cartViewModel.table.observe(viewLifecycleOwner) {
            addToCartButton.isEnabled = it != null
        }

        menuDetailViewModel.menu.observe(viewLifecycleOwner) {
            menuImage.load(it.name.loremFlickr(400))
            menuNameText.text = it.name
            menuDescriptionText.text = it.description
            menuPriceText.text = it.price.toCurrency()
        }

        menuDetailViewModel.menuQuantity.observe(viewLifecycleOwner) { quantity ->
            decrementMenuQuantityButton.isEnabled = quantity > 1
            menuQuantityText.text = quantity.toString()
            incrementMenuQuantityButton.isEnabled = quantity < 100
        }
    }
}