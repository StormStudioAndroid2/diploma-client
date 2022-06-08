package com.example.androidreader.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidreader.ReaderApplication
import com.example.androidreader.databinding.BookListFragmentBinding
import com.example.androidreader.databinding.MainFragmentBinding
import com.example.androidreader.presentation.adapter.BookListAdapter
import com.example.androidreader.presentation.viewmodel.BookListViewModel
import com.example.androidreader.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

interface BookListFragmentCallback {
    fun onItemClicked(item: String)
}

class BookListFragment : Fragment() {

    private lateinit var binding: BookListFragmentBinding

    @Inject
    lateinit var bookListViewModel: BookListViewModel

    private lateinit var bookListData: List<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val appComponent = (activity?.applicationContext as ReaderApplication).appComponent
        appComponent.bookListComponent().create().inject(this)

        val bookListAdapter = BookListAdapter(emptyList()) {
            (activity as? BookListFragmentCallback)?.onItemClicked(item = bookListData[it])
        }
        binding = BookListFragmentBinding.inflate(inflater, container, false)
        binding.bookList.adapter = bookListAdapter
        bookListViewModel.bookTitleListDomainModelFlow.onEach {
            bookListData = it.titles
            bookListAdapter.setData(it.titles)
        }.launchIn(lifecycleScope)
        bookListViewModel.initViewModel()
        return binding.root
    }

    companion object {
        fun newInstance() = BookListFragment()
    }
}