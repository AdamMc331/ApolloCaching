package com.adammcneilly.apollocaching.countrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.adammcneilly.apollocaching.databinding.FragmentCountryListBinding
import com.adammcneilly.apollocaching.models.CountryOverview
import com.adammcneilly.apollocaching.visibleIf
import org.koin.android.viewmodel.ext.android.viewModel

class CountryListFragment : Fragment(), CountryListItemClickListener {

    private val viewModel: CountryListViewModel by viewModel()
    private lateinit var binding: FragmentCountryListBinding
    private lateinit var countryListAdapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        countryListAdapter = CountryListAdapter(this)
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        binding.countryList.adapter = countryListAdapter
        binding.countryList.layoutManager = LinearLayoutManager(requireContext())
        binding.countryList.addItemDecoration(divider)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            processViewState(viewState)
        }
    }

    private fun processViewState(viewState: CountryListViewState) {
        binding.progressBar.visibleIf(viewState.showLoading)
        binding.countryList.visibleIf(viewState.showCountries)

        viewState.countryOverviews?.let(countryListAdapter::countryOverviews::set)
    }

    override fun onCountryClicked(countryOverview: CountryOverview) {
        val directions = CountryListFragmentDirections.navigateToCountryDetail(
            countryOverview = countryOverview
        )

        findNavController().navigate(directions)
    }
}
