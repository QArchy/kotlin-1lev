package ru.vk.kotlinhomework

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.vk.kotlinhomework.databinding.ListElementBinding

class MainAdapter(private val gridElements: List<GridElement>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_element, null)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            binding.imageViewListElement.setBackgroundColor(
                Color.rgb(gridElements[position].properties.first.first,
                gridElements[position].properties.first.second,
                gridElements[position].properties.first.third)
            )
            binding.textViewListElement.text = gridElements[position].properties.second.toString()
            binding.root.setOnClickListener {
                Snackbar.make(binding.root, gridElements[position].properties.second.toString(),
                    Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return gridElements.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding: ListElementBinding

        init {
            binding = ListElementBinding.bind(itemView)
        }
    }
}