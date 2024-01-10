package test.s160419098.anmp.wu.session

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.databinding.FragmentSignInBinding
import test.s160419098.anmp.wu.main.MainActivity
import test.s160419098.anmp.wu.requireInput

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val session: SessionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.signInHandler = OnClickListener {
            val username = binding.textInputUsername.requireInput()
                ?: return@OnClickListener

            val password = binding.textInputPassword.requireInput(trim = false)
                ?: return@OnClickListener

            session.signIn(username, password)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        session.signInResult.observe(viewLifecycleOwner, ::observeSignInResult)
    }

    private fun observeSignInResult(result: Result<Unit>) = when (result) {
        is Result.Loading -> {}

        is Result.Success -> {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
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
