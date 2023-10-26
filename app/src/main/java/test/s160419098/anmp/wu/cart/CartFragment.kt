package test.s160419098.anmp.wu.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.order.OrderViewModel
import test.s160419098.anmp.wu.toCurrency

class CartFragment : Fragment() {
    private val cartViewModel: CartViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableNumberText: TextView = view[R.id.text_table_number]
        val cartRecyclerView: RecyclerView = view[R.id.recycler_view_cart]
        val subtotalText: TextView = view[R.id.text_subtotal]
        val taxText: TextView = view[R.id.text_tax]
        val totalText: TextView = view[R.id.text_total]
        val processItemsButton: Button = view[R.id.button_process_to_kitchen]

        cartRecyclerView.adapter = CartItemAdapter { item ->
            cartViewModel.setItem(item)
        }

        processItemsButton.setOnClickListener {
            orderViewModel.setOrder(cartViewModel.table.value!!, cartViewModel.items.value!!)
            cartViewModel.table.value = null
            cartViewModel.emptyCart()
            Toast.makeText(requireContext(), getString(R.string.order_set), Toast.LENGTH_SHORT)
                .show()
        }

        cartViewModel.table.observe(viewLifecycleOwner) { table ->
            tableNumberText.text =
                if (table == null) getString(R.string.no_table_selected)
                else getString(R.string.template_table_number, table)
        }

        cartViewModel.items.observe(viewLifecycleOwner) { items ->
            (cartRecyclerView.adapter as CartItemAdapter).updateList(items)
            processItemsButton.isEnabled = items.isNotEmpty()
        }

        cartViewModel.subtotal.observe(viewLifecycleOwner) { subtotalText.text = it.toCurrency() }
        cartViewModel.tax.observe(viewLifecycleOwner) { taxText.text = it.toCurrency() }
        cartViewModel.total.observe(viewLifecycleOwner) { totalText.text = it.toCurrency() }
    }
}