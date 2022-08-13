package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.presentation.ui.viewmodel.HomeViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val navController by lazy {
        findNavController()
    }

    private val homeViewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var binding: HomeFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(view)
        mainViewModel.updateBottomNavigationVisibility(visibility = true)
        updateCurrentUser()
        observeCurrentUserState()
        updateAdviceState()
        observeAdviceState()
        setupAskForAdviceButton()
    }

    private fun observeCurrentUserState() {
        homeViewModel.currentUserState.observe(viewLifecycleOwner) { currentUserState ->
            when (currentUserState) {
                is CurrentUserState.ErrorUnknown -> {

                }
                is CurrentUserState.Success -> {
                    bindUserInfo(currentUserState.userView)
                }
            }
        }
    }

    private fun updateCurrentUser() {
        homeViewModel.updateCurrentUser()
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
                    binding.textViewTheAdviceAbove.text =
                        getString(R.string.the_advice_above, adviceView.quantityWords)
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

    private fun bindUserInfo(userView: UserView) {
        binding.textViewName.text = userView.name
        binding.textViewFirstLetterName.text = userView.firstCharacterName
    }
}