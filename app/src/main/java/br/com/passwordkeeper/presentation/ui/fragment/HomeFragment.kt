package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.extensions.showSnackBar
import br.com.passwordkeeper.presentation.ui.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val navController by lazy {
        findNavController()
    }

    private val arguments by  navArgs<HomeFragmentArgs>()
    private val userView: UserView by lazy {
        arguments.userView
    }
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        updateAdviceState()
        observeAdviceState()
        setupAskForAdviceButton()
        bindUserInfo()
        binding.bottomNavigation.setOnItemSelectedListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.menu_icon_new_note -> {

                }
                R.id.menu_icon_settings -> {

                }
            }
            true
        }
    }

    private fun observeAdviceState() {
        homeViewModel.adviceState.observe(viewLifecycleOwner) {
            when (it) {
                is GetAdviceStateResult.Loading -> {
                    binding.textViewMessage.text = getString(R.string.loading)
                    binding.firstLetter.text = ""
                }
                is GetAdviceStateResult.Success -> {
                    val adviceView = it.adviceView
                    binding.textViewMessage.text = adviceView.advice
                    binding.textViewTheAdviceAbove.text = getString(R.string.the_advice_above, adviceView.quantityWords)
                    binding.firstLetter.text = adviceView.firstLetter
                }
                is GetAdviceStateResult.SuccessWithoutMessage -> {
                    binding.textViewMessage.text = getString(R.string.no_message_found)
                }
                is GetAdviceStateResult.ErrorUnknown -> {
                    binding.textViewMessage.text = getString(R.string.error)
                }
            }
        }
    }

    private fun updateAdviceState() {
        homeViewModel.updateAdvice()
    }

    private fun setupAskForAdviceButton() {
        val buttonAskForAdvice: Button = binding.buttonAskForAdvice
        buttonAskForAdvice.setOnClickListener {
            homeViewModel.updateAdvice()
        }
    }

    private fun bindUserInfo() {
        binding.textViewName.text = userView.name
        binding.textViewFirstLetterName.text = userView.firstCharacterName
    }
}