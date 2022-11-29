package ru.vk.homework2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

	private val viewModel by viewModels<MainViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_main, container, false)
	}

	@SuppressLint("SetTextI18n")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val errorText = view.findViewById<TextView>(R.id.error)
		val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
		errorText.setOnClickListener {
			viewLifecycleOwner.lifecycleScope.launch {
				errorText.isVisible = false
				progressBar.isVisible = true
				try {
					val list: List<DataObject>
					if (viewModel.gifAdapter.itemCount >= 20)
						list = viewModel.getGifs(4)
					else {
						list = viewModel.getGifs(20)
					}
					viewModel.gifAdapter.submitList(viewModel.gifAdapter.currentList + list)
					progressBar.isVisible = false
				} catch (error: Throwable) {
					progressBar.isVisible = false
					errorText.isVisible = true
					error.printStackTrace()
					errorText.text = "Error: ${error.message} " +
							"Click to retry"
				}
			}
		}

		view.findViewById<RecyclerView>(R.id.recycler).apply {
			layoutManager = GridLayoutManager(context, 2)
			adapter = viewModel.gifAdapter

			addOnScrollListener(object: OnScrollListener() {
				override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(rv, dx, dy)

					if (dy > 0 || dx < 0) { // only when scrolling up
						val visibleThreshold = 2
						val layoutManager = rv.layoutManager as GridLayoutManager
						val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
						val currentTotalCount = layoutManager.itemCount
						if (currentTotalCount <= lastItem + visibleThreshold) {
							viewLifecycleOwner.lifecycleScope.launch {
								try {
									val list = viewModel.getGifs(4)
									viewModel.gifAdapter.submitList(viewModel.gifAdapter.currentList + list)
								} catch (error: Throwable) {
									error.printStackTrace()
								}
							}
						}
					}
				}
			})

		}

		viewLifecycleOwner.lifecycleScope.launch {
			progressBar.isVisible = true
			try {
				val list: List<DataObject>
				if (viewModel.gifAdapter.itemCount >= 20)
					list = viewModel.getGifs(4)
				else {
					list = viewModel.getGifs(20)
				}
				viewModel.gifAdapter.submitList(viewModel.gifAdapter.currentList + list)
				progressBar.isVisible = false
			} catch (error: Throwable) {
				error.printStackTrace()
				progressBar.isVisible = false
				errorText.isVisible = true
				errorText.text = "Error: ${error.message} " +
						"Click to retry"
			}
		}
	}


	companion object {
		fun newInstance() = MainFragment()
	}
}