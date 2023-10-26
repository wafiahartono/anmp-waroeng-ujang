package test.s160419098.anmp.wu.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import test.s160419098.anmp.wu.R
import test.s160419098.anmp.wu.TextInput
import test.s160419098.anmp.wu.get
import test.s160419098.anmp.wu.loremFlickr
import test.s160419098.anmp.wu.requireInput

class AccountFragment : Fragment() {
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val waiterImage: ImageView = view[R.id.image_waiter]
        val waiterNameText: TextView = view[R.id.text_waiter_name]
        val waiterCaptionText: TextView = view[R.id.text_waiter_caption]

        val textInputCurrentPassword = TextInput(
            view, R.id.text_input_layout_current_password, R.id.edit_text_current_password
        )
        val textInputNewPassword = TextInput(
            view, R.id.text_input_layout_new_password, R.id.edit_text_new_password
        )
        val textInputRepeatPassword = TextInput(
            view, R.id.text_input_layout_repeat_password, R.id.edit_text_repeat_password
        )
        val updatePasswordButton: Button = view[R.id.button_update_password]

        updatePasswordButton.setOnClickListener {
            textInputCurrentPassword.requireInput() ?: return@setOnClickListener
            textInputNewPassword.requireInput() ?: return@setOnClickListener
            textInputRepeatPassword.requireInput() ?: return@setOnClickListener
        }

        updatePasswordButton.setOnLongClickListener {
            authViewModel.signOut()
            return@setOnLongClickListener true
        }

        authViewModel.user.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            waiterImage.load(it.name.loremFlickr(800))
            waiterNameText.text = it.name
            waiterCaptionText.text = getString(R.string.template_worked_since, it.entryDate)
        }
    }
}