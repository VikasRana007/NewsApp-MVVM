package me.vikas.newsapp.ui.countrywisenews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.databinding.CountryItemLayoutBinding
import me.vikas.newsapp.utils.CountrySelect

class CountryWiseNewsAdapter(
    private val countries: MutableList<Country>
) : RecyclerView.Adapter<CountryWiseNewsAdapter.CountryWiseNewsViewHolder>() {

    private var countryWiseNewsClick: CountrySelect? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, position: Int
    ): CountryWiseNewsViewHolder {

        val binding = CountryItemLayoutBinding.inflate(
            LayoutInflater.from(
                viewGroup.context
            ), viewGroup, false
        )
        return CountryWiseNewsViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CountryWiseNewsViewHolder, position: Int) {
        viewHolder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun submitList(newListOfCountries: List<Country>) {
        countries.addAll(newListOfCountries)
    }

    fun onCountrySelect(countryWiseNewsClick: CountrySelect) {
        this.countryWiseNewsClick = countryWiseNewsClick
    }


    inner class CountryWiseNewsViewHolder(private val binding: CountryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.apply {
                countryName.text = country.name
                root.setOnClickListener {
                    countryWiseNewsClick?.invoke(country)
                }
            }
        }
    }
}