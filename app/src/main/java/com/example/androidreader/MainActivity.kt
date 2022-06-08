package com.example.androidreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.androidreader.databinding.ActivityMainBinding
import com.example.androidreader.domain.model.BookFormatDomainModel
import com.example.androidreader.domain.model.StrangeWordListDomainModel
import com.example.androidreader.presentation.model.PageViewStatus
import com.example.androidreader.presentation.screen.*
import com.example.androidreader.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BookFragmentCallback, BookListFragmentCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        showBookListFragment()
        setContentView(binding.root)
    }

    private fun showBookListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                BookListFragment.newInstance(),
                BOOK_LIST_FRAGMENT
            ).addToBackStack(BOOK_LIST_FRAGMENT)
            .commit()
    }

    override fun onWordClicked(word: String) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                WordInfoFragment.newInstance(word),
                WORD_INFO_FRAGMENT
            ).addToBackStack(WORD_INFO_FRAGMENT)
            .commit()
    }

    override fun onItemClicked(item: String) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                BookFragment.newInstance(item),
                BOOK_FRAGMENT
            ).addToBackStack(BOOK_FRAGMENT)
            .commit()    }

    companion object {
        val BOOK_FRAGMENT = "book_fragment"
        val WORD_INFO_FRAGMENT = "word_info_fragment"
        val BOOK_LIST_FRAGMENT = "book_list_fragment"
    }
}