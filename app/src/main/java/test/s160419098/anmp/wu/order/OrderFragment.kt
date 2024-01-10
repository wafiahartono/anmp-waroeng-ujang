package test.s160419098.anmp.wu.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import test.s160419098.anmp.wu.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewOrder.apply {
            adapter = OrderAdapter()
            setHasFixedSize(true)
        }

        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            (binding.recyclerViewOrder.adapter as OrderAdapter).updateOrders(orders)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel.fetch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
