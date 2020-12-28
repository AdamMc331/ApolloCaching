package com.adammcneilly.apollocaching.countrydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.adammcneilly.apollocaching.databinding.FragmentCountryDetailBinding
import com.adammcneilly.apollocaching.visibleIf
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CountryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailBinding

    private val args: CountryDetailFragmentArgs by navArgs()

    private val viewModel: CountryDetailViewModel by viewModel {
        parametersOf(args.countryOverview)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            processViewState(viewState)
        }
    }

    private fun processViewState(viewState: CountryDetailViewState) {
        binding.progressBar.visibleIf(viewState.showLoading)
        binding.countryDetailContent.visibleIf(viewState.showDetail)

        binding.countryName.text = viewState.countryName
        binding.countryContinent.text = viewState.countryContinent
        binding.countryCapital.text = viewState.countryCapital
        binding.countryEmoji.text = viewState.countryEmoji
    }
}
