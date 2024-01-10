package test.s160419098.anmp.wu.order.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.databinding.FragmentOrderDetailBinding
import test.s160419098.anmp.wu.order.OrderViewModel

class OrderDetailFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private val cart: CartViewModel by activityViewModels()
    private val orders: OrderViewModel by activityViewModels()
    private val viewModel: OrderDetailViewModel by viewModels()

    private val orderId
        get() = OrderDetailFragmentArgs.fromBundle(requireArguments()).orderId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetch(orderId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.viewModel = viewModel

        binding.closeBillHandler = OnClickListener {
            orders.closeOrder(orderId)

            findNavController().navigateUp()
        }

        binding.orderMoreHandler = OnClickListener {
            orders.moveOrderToCart(orderId)
            cart.setCartFromOrder(orderId)

            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
