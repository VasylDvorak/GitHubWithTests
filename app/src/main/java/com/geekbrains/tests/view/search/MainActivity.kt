package com.geekbrains.tests.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.tests.R
import com.geekbrains.tests.application.App
import com.geekbrains.tests.databinding.ActivityMainBinding
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.presenter.search.PresenterSearchContract
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.view.details.DetailsActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    val adapter = SearchResultAdapter()
    private val presenter: PresenterSearchContract = SearchPresenter().apply {
        App.instance.appComponent.inject(this)
    }
    var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onAttach(this)
        setUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.onDetach()
    }

    fun setUI() {
        binding.toDetailsActivityButton.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setRecyclerView()
    }

    fun setRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

//    fun setQueryListener() {
//        binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                val query = binding.searchEditText.text.toString()
//                if (query.isNotBlank()) {
//                    presenter.searchGitHub(query)
//                    return@OnEditorActionListener true
//                } else {
//                    Toast.makeText(
//                        this@MainActivity,
//                        getString(R.string.enter_search_word),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@OnEditorActionListener false
//                }
//            }
//            false
//        })
//    }

    fun setQueryListener() {
binding.searchButton.setOnClickListener {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                }
    }
    }


    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        with(binding.totalCountTextViewMainActivity) {
            visibility = View.VISIBLE
            text =
                String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
        }

        this.totalCount = totalCount
        adapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
