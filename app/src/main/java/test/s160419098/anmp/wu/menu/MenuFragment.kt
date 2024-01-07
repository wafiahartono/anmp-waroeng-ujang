package test.s160419098.anmp.wu.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val cart: CartViewModel by activityViewModels()
    private val viewModel: MenuViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.cart = cart
        binding.viewModel = viewModel

        binding.changeTableHandler = OnClickListener {
            findNavController().navigate(MenuFragmentDirections.changeTable())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCategory.apply {
            adapter = CategoryAdapter()
            setHasFixedSize(true)
        }

        viewModel.categories.observe(viewLifecycleOwner) {
            (binding.recyclerViewCategory.adapter as CategoryAdapter).updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
