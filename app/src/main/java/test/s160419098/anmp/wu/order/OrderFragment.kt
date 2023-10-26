package test.s160419098.anmp.wu.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.get

class OrderFragment : Fragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderRecyclerView: RecyclerView = view[R.id.recycler_view_order]

        orderRecyclerView.adapter = OrderAdapter()

        orderViewModel.orders.observe(viewLifecycleOwner) { orders ->
            (orderRecyclerView.adapter as OrderAdapter).updateList(orders)
        }
    }
}