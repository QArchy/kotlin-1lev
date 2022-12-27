package com.example.homework3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class LoginForm : Fragment() {

    private var editTextState = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_form, container, false)

        val editText = view.findViewById<EditText>(R.id.fragment_login_form_etText)
        val btn = view.findViewById<MaterialButton>(R.id.fragment_login_form_btnSingUp)

        btn.setOnClickListener {
            if (editTextState) {
                if (editText.text.isEmpty()) {
                    editText.hint = "Input email"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.text).matches()) {
                    editText.text.clear()
                    editText.hint = "Invalid email"
                } else {
                    editText.text.clear()
                    editText.hint = "Password"
                    editTextState = false
                }
            } else {
                if (editText.text.isEmpty()) {
                    editText.hint = "Input password"
                } else {
                    editText.text.clear()
                    findNavController().navigate(R.id.action_loginForm_to_mainPageFragment)
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(): LoginForm {
            return LoginForm()
        }
    }
}