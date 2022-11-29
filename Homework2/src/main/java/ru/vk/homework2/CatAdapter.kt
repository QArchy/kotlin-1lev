package ru.vk.homework2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

class CatDiffitemCallback: DiffUtil.ItemCallback<DataObject>() {
	override fun areItemsTheSame(oldItem: DataObject, newItem: DataObject): Boolean {
		return oldItem.id == newItem.id
	}

	override fun areContentsTheSame(oldItem: DataObject, newItem: DataObject): Boolean {
		return oldItem == newItem
	}
}

class CatAdapter: ListAdapter<DataObject, CatAdapter.CatViewHolder>(CatDiffitemCallback()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
		return CatViewHolder(view)
	}

	override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
		val cat = getItem(position)
		holder.bind(cat)
	}

	class CatViewHolder(view: View): RecyclerView.ViewHolder(view) {
		protected val image by lazy { view.findViewById<ImageView>(R.id.image) }
		protected val imageLoader by lazy { Glide.with(image) }

		fun bind(cat: DataObject) {
			val circularProgressDrawable = CircularProgressDrawable(image.context)
			circularProgressDrawable.strokeWidth = 5f
			circularProgressDrawable.centerRadius = 30f
			circularProgressDrawable.start()

			imageLoader.load(cat.images.ogImage.url).preload()

			imageLoader.load(cat.images.ogImage.url).placeholder(
				circularProgressDrawable
			).error(R.drawable.ic_launcher_foreground).into(image)

			image.setOnClickListener {
				imageLoader.load(cat.images.ogImage.url).placeholder(
					circularProgressDrawable
				).error(R.drawable.ic_launcher_foreground).into(image)
			}
		}
	}
}