package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.NewNoteSuccessBinding

class NewNoteSuccessFragment : Fragment(R.layout.new_note_success) {

    private val navController by lazy {
        findNavController()
    }

    private lateinit var binding: NewNoteSuccessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = NewNoteSuccessBinding.bind(view)
    }

}