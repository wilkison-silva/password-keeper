package br.com.passwordkeeper.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.domain.model.CardType
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.TypeAdapter

class HomeFragment: Fragment() {
    private val navController by lazy {
        findNavController()
    }

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
                CardType("streaming", 25),
                CardType("social media", 8),
                CardType("banks", 3),
                CardType("Education", 5),
                CardType("Work", 2),
                CardType("Card", 7)
            )
        )
    }


}