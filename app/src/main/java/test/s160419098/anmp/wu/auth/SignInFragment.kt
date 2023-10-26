package test.s160419098.anmp.wu.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.Result
import test.s160419098.anmp.wu.TextInput
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.main.MainActivity
import test.s160419098.anmp.wu.requireInput
import test.s160419098.anmp.wu.setError

class SignInFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameTextInput = TextInput(
            view, R.id.text_input_layout_username, R.id.edit_text_username
        )
        val passwordTextInput = TextInput(
            view, R.id.text_input_layout_password, R.id.edit_text_password
        )
        val signInButton: Button = view[R.id.button_sign_in]

        signInButton.setOnClickListener {
            val username = usernameTextInput.requireInput() ?: return@setOnClickListener
            val password = passwordTextInput.requireInput() ?: return@setOnClickListener

            authViewModel.signIn(username, password)
        }

        authViewModel.signInResult.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    Toast.makeText(requireContext(), R.string.signing_in, Toast.LENGTH_SHORT).show()
                }

                is Result.Success -> {
                    if (it.data) {
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    } else {
                        usernameTextInput.layout.setError(R.string.invalid_username_or_password)
                    }
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(), it.exception.message.toString(), Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}