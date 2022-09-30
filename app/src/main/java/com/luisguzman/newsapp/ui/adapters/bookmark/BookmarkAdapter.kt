package com.luisguzman.newsapp.ui.adapters.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.luisguzman.newsapp.R
import com.luisguzman.newsapp.data.model.Article
import com.luisguzman.newsapp.databinding.ItemNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class BookmarkAdapter(
    private val bookmarkList: List<Article>,
    private val onItemClickListener: (Article) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val itemBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookmarkList[position], onItemClickListener)
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class BookmarkViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            article: Article,
            onItemClickListener: (Article) -> Unit,
        ) {
            binding.title.text = article.title
            binding.description.text = article.description
            binding.content.text = article.content

            // This is in case the url doesn't have an image
            if (article.urlToImage.isNullOrEmpty()) {
                binding.imgUrl.setImageResource(R.drawable.person_empty)
            } else {
                Glide.with(itemView.context).load(article.urlToImage).centerCrop().into(binding.imgUrl)
            }

            // This is in case you don't have an author
            if (article.author.isNullOrEmpty()) {
                binding.author.text = "Anonymous"
            } else {
                binding.author.text = article.author
            }

            // Regex for published new
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = dateFormat.parse(article.publishedAt)
            binding.releasedDate.text = date?.let { dateFormat.format(it) }

            binding.bookmark.setOnClickListener {
                deleteItem(article)
            }

            itemView.setOnClickListener { onItemClickListener(article) }

        }

        private fun deleteItem(article: Article) {
            val db = Firebase.firestore.collection("bookmarks")
            db.document(article.id).delete()
        }

    }
}