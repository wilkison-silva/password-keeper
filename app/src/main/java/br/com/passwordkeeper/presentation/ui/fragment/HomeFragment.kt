package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.passwordkeeper.R
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.domain.model.CardType
import br.com.passwordkeeper.domain.model.UserView
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.TypeAdapter
import br.com.passwordkeeper.presentation.ui.viewModel.HomeViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private val navController by lazy {
        findNavController()
    }

    private val arguments by  navArgs<HomeFragmentArgs>()
    private val userView: UserView by lazy {
        arguments.userView
    }
    private val homeViewModel: HomeViewModel by inject()
    private lateinit var binding: HomeFragmentBinding
    private val typeAdapter = TypeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding
            .inflate(
                inflater,
                container,
                false
            )
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewTypes.adapter = typeAdapter
        typeAdapter.updateList(
            listCardType = listOf(
                CardType("streaming", 25, R.drawable.ic_stream_type),
                CardType("social media", 8, R.drawable.ic_social_media),
                CardType("banks", 3, R.drawable.ic_bank),
                CardType("Education", 5, R.drawable.ic_education),
                CardType("Work", 2, R.drawable.ic_work),
                CardType("Card", 7, R.drawable.ic_card)
            )
        )
        updateAdviceState()
        observeAdviceState()
        setupAskForAdviceButton()
        bindUserInfo()
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