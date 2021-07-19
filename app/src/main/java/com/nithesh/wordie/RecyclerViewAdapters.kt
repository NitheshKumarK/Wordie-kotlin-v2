package com.nithesh.wordie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nithesh.wordie.databinding.StringListItemBinding
import com.nithesh.wordie.databinding.WordListItemBinding
import com.nithesh.wordie.network.Word
import com.nithesh.wordie.search.SearchViewModel

//word adapter is used to set words to recycler view
//it takes SearchViewModel to use dataBinding in the layout
class WordAdapter(private val viewModel: SearchViewModel) :
//it uses the list adapter to increase the efficiency of the recycler view
//WordDiffCallBack sends only changed word so that recycler view changes the word instead of
//the whole list
    ListAdapter<Word, WordAdapter.ViewHolder>(WordDiffCallBack()) {


    // private val TAG: String = WordAdapter::class.java.simpleName
    //this method creates WordViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //this method binds data with the view holder and also with WordClickListener
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), WordClickListener { word ->
            //this triggers the navigation with the word object
            viewModel.navigateToDetailFragment(word)
        })


    }

    //this is word ViewHolder receives binding object
    class ViewHolder(private val binding: WordListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //this method binds the word and clickListener with the views inside the ViewHolder
        fun bind(word: Word, clickListener: WordClickListener) {
            binding.headWordText.text = word.hwi?.hw ?: "null"
            binding.definitionWordText.text = word.meta.shortDef?.def?.get(0) ?: "null"
            binding.word = word
            binding.clickListener = clickListener
        }

        companion object {
            //this companion method receives ViewGroup and return ViewHolder
            fun from(parent: ViewGroup): ViewHolder {
                val binding = WordListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

}
class WordDiffCallBack : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}

class StringAdapter(private val viewModel: SearchViewModel) :
    ListAdapter<String, StringAdapter.ViewHolder>(StringCallback()) {
    //this method called each time ViewHolder is created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //this callback gets called each time data binds with ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), StringClickListener { newQueryWord ->
            //this is called every time user clicks the viewHolder("String list item") to
            //to search new word
            viewModel.searchWithNewWord(newQueryWord)

        })
    }

    //this is string ViewHolder class
    class ViewHolder(private val binding: StringListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //this method binds the string object, clickListener and view object
        fun bind(string: String, clickListener: StringClickListener) {
            binding.suggestionTextView.text = string
            binding.suggestionWord = string
            binding.clickListener = clickListener
        }

        //this creates ViewHolder from viewGroup
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = StringListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }
}


class StringCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class WordClickListener(val clickListener: (word: Word) -> Unit) {
    fun onClick(word: Word) = clickListener(word)
}

class StringClickListener(val clickListener: (string: String) -> Unit) {
    fun onClick(string: String) = clickListener(string)
}