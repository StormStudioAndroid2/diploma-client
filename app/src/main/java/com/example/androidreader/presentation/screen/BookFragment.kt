package com.example.androidreader.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidreader.R
import com.example.androidreader.ReaderApplication
import com.example.androidreader.databinding.ActivityMainBinding
import com.example.androidreader.databinding.MainFragmentBinding
import com.example.androidreader.domain.model.BookFormatDomainModel
import com.example.androidreader.domain.model.StrangeWordListDomainModel
import com.example.androidreader.presentation.model.PageViewStatus
import com.example.androidreader.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

interface BookFragmentCallback {

    fun onWordClicked(word: String)
}

class BookFragment : Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var binding: MainFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val title = arguments?.getString(TITLE_ARG) ?: throw Exception("no found argument!")

        val appComponent = (activity?.applicationContext as ReaderApplication).appComponent

        binding = MainFragmentBinding.inflate(inflater, container, false)
        appComponent.mainComponent().create().inject(this)
        mainViewModel.initViewModel(title)
        binding.nextButton.setOnClickListener {
            mainViewModel.onNextButtonClick()
        }
        binding.prevButton.setOnClickListener {
            mainViewModel.onPreviousButtonClick()
        }
        binding.pageView.setHighlightedWordListener {
            (activity as? BookFragmentCallback)?.onWordClicked(it)
        }
        subscribeToFlows()
        return binding.root
    }


    private fun subscribeToFlows() {
        mainViewModel.bookFlow.onEach {
            if (it != BookFormatDomainModel.empty()) {
                binding.pageView.setPageText(it.textList)

            }
        }.launchIn(lifecycleScope)
        mainViewModel.strangeWordListDomainModelFlow.onEach {
            if (it != StrangeWordListDomainModel.empty()) {
                binding.pageView.setWordMap(it.wordWithDefinitionMap)
            }
        }.launchIn(lifecycleScope)
        mainViewModel.pageViewStatusFlow.onEach {
            if (it == PageViewStatus.NEXT) {
                binding.pageView.turnToNextPage()
            } else {
                binding.pageView.turnToPreviousPage()
            }
        }.launchIn(lifecycleScope)
    }

    companion object {

        fun newInstance(title: String) =
            BookFragment().apply {
                arguments = bundleOf(TITLE_ARG to title)
            }
        const val TITLE_ARG = "title"
    }


}