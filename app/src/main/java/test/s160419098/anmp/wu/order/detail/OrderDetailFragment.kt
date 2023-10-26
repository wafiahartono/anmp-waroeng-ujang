package test.s160419098.anmp.wu.order.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.order.OrderViewModel
import test.s160419098.anmp.wu.toCurrency

class OrderDetailFragment : BottomSheetDialogFragment() {
    private val cartViewModel: CartViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val orderDetailViewModel: OrderDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderDetailViewModel.getOrderFn = { table ->
            orderViewModel.orders.value!!.first { it.table == table }
        }

        orderDetailViewModel.table.value =
            OrderDetailFragmentArgs.fromBundle(requireArguments()).table
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tableNumberText: TextView = view[R.id.text_table_number]
        val orderTotalText: TextView = view[R.id.text_order_total]
        val orderDurationChrono: Chronometer = view[R.id.text_order_duration]
        val orderDetailText: TextView = view[R.id.text_order_detail]
        val closeBillButton: Button = view[R.id.button_close_bill]
        val orderMoreButton: Button = view[R.id.button_order_more]

        closeBillButton.setOnClickListener {
            orderViewModel.removeOrder(orderDetailViewModel.order.value!!)
            Toast.makeText(requireContext(), R.string.bill_closed, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        orderMoreButton.setOnClickListener {
            val order = orderDetailViewModel.order.value!!
            cartViewModel.table.value = order.table
            cartViewModel.setItems(order.items)
            Toast.makeText(
                requireContext(),
                getString(R.string.template_table_set, order.table),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigateUp()
        }

        orderDetailViewModel.order.observe(viewLifecycleOwner) { order ->
            if (order == null) return@observe

            tableNumberText.text = order.table.toString()
            orderTotalText.text =
                (order.items.fold(0) { acc, item -> acc + item.menu.price * item.quantity } * 1.1F).toCurrency()
            orderDurationChrono.base = order.processedAt
            orderDurationChrono.start()
            orderDetailText.text = order.items.joinToString {
                "${if (it.quantity > 1) "${it.quantity}x " else ""}${it.menu.name}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val orderDurationChrono: Chronometer = requireView()[R.id.text_order_duration]
        orderDurationChrono.stop()
    }
}