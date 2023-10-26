package test.s160419098.anmp.wu.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.TextInput
import test.s160419098.anmp.wu.auth.AuthViewModel
import test.s160419098.anmp.wu.auth.Sex
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.loremFlickr
import test.s160419098.anmp.wu.order.OrderViewModel
import test.s160419098.anmp.wu.requireInput
import test.s160419098.anmp.wu.setError

class HomeFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val waiterImage: ImageView = view[R.id.image_waiter]
        val waiterNameText: TextView = view[R.id.text_waiter_name]
        val waiterCaptionText: TextView = view[R.id.text_waiter_caption]

        val layoutNotServing: ConstraintLayout = view[R.id.layout_not_serving]
        val tableNumberTextInput = TextInput(
            view, R.id.text_input_layout_table_number, R.id.edit_text_table_number,
        )
        val serveButton: Button = view[R.id.button_serve]

        val layoutServing: ConstraintLayout = view[R.id.layout_serving]
        val tableNumberText: TextView = view[R.id.text_table_number]
        val changeTableButton: Button = view[R.id.button_change_table_number]

        tableNumberTextInput.editText.doAfterTextChanged {
            checkTableNumberInput(tableNumberTextInput)
        }

        serveButton.setOnClickListener {
            val table = checkTableNumberInput(tableNumberTextInput) ?: return@setOnClickListener
            cartViewModel.table.value = table
            orderViewModel.orders.value!!.firstOrNull { it.table == table }?.let {
                cartViewModel.setItems(it.items)
            }
            Toast.makeText(
                requireContext(), getString(R.string.template_table_set, table), Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(HomeFragmentDirections.openMenu())
        }

        changeTableButton.setOnClickListener {
            cartViewModel.table.value = null
        }

        authViewModel.user.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            waiterImage.load(it.name.loremFlickr(400))
            waiterNameText.text = it.name
            waiterCaptionText.text = when (it.sex) {
                Sex.MALE -> "Waiter"
                Sex.FEMALE -> "Waitress"
            }
        }

        cartViewModel.table.observe(viewLifecycleOwner) {
            if (it == null) {
                layoutNotServing.visibility = View.VISIBLE
                layoutServing.visibility = View.INVISIBLE
            } else {
                layoutNotServing.visibility = View.INVISIBLE
                layoutServing.visibility = View.VISIBLE

                tableNumberText.text = getString(R.string.template_table_number, it)
            }
        }
    }

    private fun checkTableNumberInput(textInput: TextInput): Int? {
        val table = (textInput.requireInput() ?: return null).toIntOrNull()

        if (table == null || table < 1 || table > 15) {
            textInput.layout.setError(R.string.valid_table_number_helper)
            return null
        } else {
            textInput.layout.error = null
        }

        return table
    }
}