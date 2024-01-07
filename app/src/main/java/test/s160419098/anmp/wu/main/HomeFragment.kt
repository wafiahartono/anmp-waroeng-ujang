package test.s160419098.anmp.wu.main

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import test.s160419098.anmp.wu.AfterTextChanged
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.databinding.FragmentHomeBinding
import test.s160419098.anmp.wu.order.OrderViewModel
import test.s160419098.anmp.wu.requireInput
import test.s160419098.anmp.wu.session.SessionViewModel
import test.s160419098.anmp.wu.setError

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val session: SessionViewModel by activityViewModels()
    private val cart: CartViewModel by activityViewModels()
    private val orders: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.session = session
        binding.cart = cart

        binding.queryChangedAction = AfterTextChanged {
            checkTableNumberInput(it)
        }

        binding.serveTableHandler = OnClickListener {
            val table = binding.editTextTableNumber.text.toString().toIntOrNull()
                ?: return@OnClickListener

            cart.set(
                table = table,
                items = orders.getTableOrder(table)?.items,
            )

            findNavController().navigate(HomeFragmentDirections.openMenu())
        }

        binding.changeTableHandler = OnClickListener {
            cart.empty()
        }

        return binding.root
    }

    private fun checkTableNumberInput(text: Editable?) {
        binding.textInputTableNumber.requireInput() ?: return

        val table = text.toString().toIntOrNull()

        if (table == null || table < 1 || table > 15) {
            binding.textInputTableNumber.setError(R.string.valid_table_number_helper)
        } else {
            binding.textInputTableNumber.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
