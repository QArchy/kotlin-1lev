package ru.vk.kotlinhomework

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import ru.vk.kotlinhomework.databinding.FragmentMainBinding

class MainFragment(): Fragment() {

                        // Variables declaration

    private lateinit var binding: FragmentMainBinding
    private lateinit var gridElements: MutableList<GridElement>

                        // Override functions

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        gridElements = mutableListOf()
        binding.fragmentMainRv.adapter = MainAdapter(gridElements)
        setLayoutManager()
        binding.fragmentMainAddButton.setOnClickListener { listenerButtonAddRow() }

        if (savedInstanceState != null) {
            var tmp = savedInstanceState.getInt(GRID_ELEMENTS_SIZE)
            while (tmp > 0) {
                gridElements.add(genGridElement())
                tmp--
            }
        }
        else {
            val tmp = arguments?.getInt("INITIAL_GRID_ELEMENTS_SIZE")
            if (tmp != null) {
                for (i in 0.. tmp) {
                    gridElements.add(genGridElement())
                    binding.fragmentMainRv.adapter!!.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(GRID_ELEMENTS_SIZE, gridElements.size)
    }

                        // Init functions

    private fun setLayoutManager() {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.fragmentMainRv.layoutManager = GridLayoutManager(binding.root.context, 3)
        else
            binding.fragmentMainRv.layoutManager = GridLayoutManager(binding.root.context, 4)
    }

                        // Listeners

    @SuppressLint("NotifyDataSetChanged")
    private fun listenerButtonAddRow() {
        gridElements.add(genGridElement())
        binding.fragmentMainRv.adapter!!.notifyDataSetChanged()
    }

                        // Action helper functions

    private fun genGridElement(): GridElement {
        return if ((gridElements.size + 1) % 2 == 0) {
            GridElement(Pair(Triple(255,0, 0), gridElements.size + 1))
        } else {
            GridElement(Pair(Triple(0,0, 139), gridElements.size + 1))
        }
    }

    companion object {
        const val GRID_ELEMENTS_SIZE = "GRID_ELEMENTS_SIZE"

        fun Create(item_count: Int): MainFragment {
            val frag = MainFragment()
            val b = Bundle()
            b.apply {
                putInt("INITIAL_GRID_ELEMENTS_SIZE", item_count)
            }
            frag.arguments = b
            return frag
        }
    }
}