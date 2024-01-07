package test.s160419098.anmp.wu.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.databinding.FragmentCartBinding
import test.s160419098.anmp.wu.order.OrderViewModel

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cart: CartViewModel by activityViewModels()
    private val orders: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = cart

        binding.processHandler = View.OnClickListener {
            orders.setOrder(cart.getOrder())
            cart.empty()

            Toast.makeText(requireContext(), getString(R.string.order_set), Toast.LENGTH_SHORT)
                .show()

            findNavController().navigate(CartFragmentDirections.changeTable())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCart.apply {
            adapter = CartItemAdapter(
                onItemChange = { item -> cart.setItem(item) }
            )

            setHasFixedSize(true)
        }

        cart.items.observe(viewLifecycleOwner) { items ->
            (binding.recyclerViewCart.adapter as CartItemAdapter).updateList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
