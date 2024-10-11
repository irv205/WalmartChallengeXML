package com.irv205.walmartchallengexml.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.irv205.walmartchallengexml.databinding.ActivityMainBinding
import com.irv205.walmartchallengexml.presentation.util.CircularProgressView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private val productAdapter = ProductAdapter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var circularProgressView: CircularProgressView
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvMain
        swipeRefreshLayout = binding.swipeRefresh
        circularProgressView = binding.circularProgressView
        errorTextView = binding.errorText

        setupRecyclerView()
        setupSwipeRefreshLayout()
        observeProducts()
        observeLoading()
        observeErrors()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getProducts()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeProducts() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.products.collect { products ->
                productAdapter.submitList(products)
            }
        }
    }

    private fun observeLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.loading.collect { isLoading ->
                if (isLoading) {
                    circularProgressView.visibility = View.VISIBLE
                    circularProgressView.startAnimation()
                    circularProgressView.setProgressColor(Color.RED)
                    circularProgressView.setStrokeWidth(12f)
                } else {
                    circularProgressView.visibility = View.GONE
                    circularProgressView.stopAnimation()
                }
            }
        }
    }

    private fun observeErrors() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.errorMessage.collect { errorMessage ->
                if (errorMessage != null) {
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = errorMessage
                } else {
                    errorTextView.visibility = View.GONE
                }
            }
        }
    }
}