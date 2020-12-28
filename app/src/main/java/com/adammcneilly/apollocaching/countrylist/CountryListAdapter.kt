package com.adammcneilly.apollocaching.countrylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adammcneilly.apollocaching.R
import com.adammcneilly.apollocaching.models.CountryOverview

class CountryListAdapter(
    private val clickListener: CountryListItemClickListener
) : RecyclerView.Adapter<CountryListAdapter.CountryOverviewViewHolder>() {

    var countryOverviews: List<CountryOverview> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryOverviewViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_country_overview, parent, false)
        return CountryOverviewViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: CountryOverviewViewHolder, position: Int) {
        val country = countryOverviews[position]
        holder.bindCountryOverview(country)
    }

    override fun getItemCount(): Int {
        return countryOverviews.size
    }

    class CountryOverviewViewHolder(
        private val view: View,
        private val clickListener: CountryListItemClickListener
    ) : RecyclerView.ViewHolder(view) {
        private val countryNameTextView: TextView = view.findViewById(R.id.country_name)

        fun bindCountryOverview(country: CountryOverview) {
            this.countryNameTextView.text = country.name

            view.setOnClickListener {
                clickListener.onCountryClicked(country)
            }
        }
    }
}
