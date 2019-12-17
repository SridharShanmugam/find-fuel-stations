package com.sridhar.findfuelstations.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sridhar.findfuelstations.R
import com.sridhar.findfuelstations.viewmodel.FuelStationViewModel
import com.sridhar.findfuelstations.vo.FuelStation
import kotlinx.android.synthetic.main.layout_fuel_station_list_item.view.*


class FuelStationListAdapter(private val viewModel: FuelStationViewModel) :
    RecyclerView.Adapter<FuelStationListAdapter.ViewHolder>(), Filterable {

    lateinit var fuelStations: List<FuelStation>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_fuel_station_list_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return if (::fuelStations.isInitialized) fuelStations.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.fuelStationNameTv.text = fuelStations[position].stationName
        val phone = fuelStations[position].stationPhone
        if (phone.isNullOrBlank()) {
            holder.itemView.fuelStationPhoneTv.visibility = View.GONE
        } else {
            holder.itemView.fuelStationPhoneTv.visibility = View.VISIBLE
            holder.itemView.fuelStationPhoneTv.text = phone
        }
        holder.itemView.fuelStationCityTv.text = fuelStations[position].city
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredValues = mutableListOf<FuelStation>()
                if (constraint.isNullOrBlank()) {
                    filteredValues.addAll(viewModel.fuelStationResponse.value!!.fuelStations)
                } else {
                    filteredValues.addAll(viewModel.fuelStationResponse.value!!.fuelStations.filter {
                        it.stationName.contains(constraint, true)
                    })
                }
                val filterResults = FilterResults()
                filterResults.values = filteredValues
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                fuelStations = results?.values as MutableList<FuelStation>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}