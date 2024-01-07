package test.s160419098.anmp.wu.order.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val table = OrderDetailFragmentArgs.fromBundle(requireArguments()).table

        viewModel.setOrder(orders.getTableOrder(table)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.closeBillHandler = View.OnClickListener {
            orders.removeTableOrder(viewModel.getTable())

            findNavController().navigateUp()
        }

        binding.orderMoreHandler = View.OnClickListener {
            cart.set(viewModel.getOrderSkipValueAccessor())

            findNavController().navigate(OrderDetailFragmentDirections.openMenu())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.order.observe(viewLifecycleOwner) {
            binding.textOrderDuration.base = it.datetime
            binding.textOrderDuration.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
