package test.s160419098.anmp.wu.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import test.s160419098.anmp.wu.databinding.FragmentAccountBinding
import test.s160419098.anmp.wu.requireInput

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val session: SessionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.session = session

        binding.updatePasswordHandler = OnClickListener {
            binding.textInputCurrentPassword.requireInput() ?: return@OnClickListener
            binding.textInputNewPassword.requireInput() ?: return@OnClickListener
            binding.textInputRepeatPassword.requireInput() ?: return@OnClickListener
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
