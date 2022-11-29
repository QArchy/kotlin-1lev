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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log

class MainFragment : Fragment() {

	private val viewModel by viewModels<MainViewModel>()

	private val catAdapter = CatAdapter()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_main, container, false)
	}

	@SuppressLint("SetTextI18n")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<RecyclerView>(R.id.recycler).apply {
			layoutManager = GridLayoutManager(context, 2)
			adapter = catAdapter

			addOnScrollListener(object: OnScrollListener() {
				override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
					super.onScrolled(rv, dx, dy)

					if (dy > 0) { // only when scrolling up
						val visibleThreshold = 2
						val layoutManager = rv.layoutManager as GridLayoutManager
						val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
						val currentTotalCount = layoutManager.itemCount
						if (currentTotalCount <= lastItem + visibleThreshold) {
							viewLifecycleOwner.lifecycleScope.launch {
								//try {
									val list = withContext(Dispatchers.IO) { viewModel.getCats(4) }
									catAdapter.submitList(catAdapter.currentList + list)
								//} catch (error: Throwable) {

								//}
							}
						}
					}
				}
			})

		}

		val error_text = view.findViewById<TextView>(R.id.error)
		val progress_bar = view.findViewById<ProgressBar>(R.id.progressBar)

		viewLifecycleOwner.lifecycleScope.launch {
			progress_bar.isVisible = true

			try {
				val list = withContext(Dispatchers.IO) { viewModel.getCats(50) }
				catAdapter.submitList(list)
				progress_bar.isVisible = false
			} catch (error: Throwable) {
				error_text.isVisible = true
				error_text.text = "Error: ${error.message} Click to retry"
				Log.println(Log.ASSERT, "ABOBA", "${error.message}")
				error.printStackTrace()
				error_text.setOnClickListener {
					// retry
				}
			}
		}
	}


	companion object {
		fun newInstance() = MainFragment()
	}
}