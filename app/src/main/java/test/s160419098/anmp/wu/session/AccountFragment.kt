package test.s160419098.anmp.wu.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.databinding.FragmentAccountBinding
import test.s160419098.anmp.wu.requireInput
import test.s160419098.anmp.wu.setError

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
            val currentPassword = binding.textInputCurrentPassword.requireInput(trim = false)
                ?: return@OnClickListener

            val newPassword = binding.textInputNewPassword.requireInput(trim = false)
                ?: return@OnClickListener

            val newPasswordConfirm = binding.textInputRepeatPassword.requireInput(trim = false)
                ?: return@OnClickListener

            if (newPassword != newPasswordConfirm) {
                binding.textInputRepeatPassword.setError(R.string.password_confirmation_doesnt_match)
                return@OnClickListener
            } else {
                binding.textInputRepeatPassword.error = null
            }

            session.updatePassword(currentPassword, newPassword)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session.updatePasswordResult.observe(viewLifecycleOwner, ::observeUpdatePasswordResult)
    }

    private fun observeUpdatePasswordResult(result: Result<Unit>) = when (result) {
        is Result.Loading -> {}

        is Result.Success -> {
            Toast.makeText(requireContext(), R.string.password_updated, Toast.LENGTH_LONG).show()
        }

        is Result.Error -> {
            Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
