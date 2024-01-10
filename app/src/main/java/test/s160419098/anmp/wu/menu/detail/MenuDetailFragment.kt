package test.s160419098.anmp.wu.menu.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.cart.CartViewModel
import test.s160419098.anmp.wu.databinding.FragmentMenuDetailBinding

class MenuDetailFragment : Fragment() {
    private var _binding: FragmentMenuDetailBinding? = null
    private val binding get() = _binding!!

    private val cart: CartViewModel by activityViewModels()
    private val viewModel: MenuDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetch(
            MenuDetailFragmentArgs.fromBundle(requireArguments()).menuId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding.cart = cart
        binding.viewModel = viewModel

        binding.addMenuHandler = OnClickListener {
            cart.setItemQuantity(
                menu = viewModel.menu.value!!,
                quantity = viewModel.quantity.value!!,
            )

            Toast.makeText(requireContext(), getString(R.string.menu_added), Toast.LENGTH_SHORT)
                .show()

            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
