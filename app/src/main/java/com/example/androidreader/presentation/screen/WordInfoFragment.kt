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
import com.example.androidreader.databinding.MainFragmentBinding
import com.example.androidreader.databinding.WordInfoFragmentBinding
import com.example.androidreader.presentation.adapter.MentionAdapter
import com.example.androidreader.presentation.viewmodel.WordInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

class WordInfoFragment : Fragment() {
    @Inject
    lateinit var wordInfoViewModel: WordInfoViewModel

    private lateinit var binding: WordInfoFragmentBinding
    private val adapter = MentionAdapter(emptyList())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val word = arguments?.getString(WORD_KEY) ?: throw Exception("no found argument!")
        val appComponent = (activity?.applicationContext as ReaderApplication).appComponent
        appComponent.wordInfoComponent().create().inject(this)

        binding = WordInfoFragmentBinding.inflate(inflater, container, false)
        binding.mentionList.adapter = adapter
        wordInfoViewModel.getWordByArgument(word)
        wordInfoViewModel.wordInfoFlow.onEach {
            binding.definitionTextView.text = getString(R.string.definition_string, it.word, it.definition)
            adapter.setData(it.mentions)
        }.launchIn(lifecycleScope)
        return binding.root
    }

    companion object {
        fun newInstance(word: String): WordInfoFragment {
            return WordInfoFragment().apply {
                arguments = bundleOf(WORD_KEY to word)
            }
        }

        val WORD_KEY = "word_key"
    }
}