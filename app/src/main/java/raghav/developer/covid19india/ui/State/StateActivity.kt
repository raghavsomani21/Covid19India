package raghav.developer.covid19india.ui.State

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.MergeAdapter
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import raghav.developer.covid19india.databinding.ActivityStateBinding
import raghav.developer.covid19india.ui.District.DistrictActivity
import raghav.developer.covid19india.ui.adapters.StateAdapter
import raghav.developer.covid19india.ui.adapters.TotalAdapter
import raghav.developer.covid19india.utils.State
import raghav.developer.covid19india.utils.applyTheme
import raghav.developer.covid19india.utils.getPeriod
import raghav.developer.covid19india.utils.isDarkTheme
import java.util.Observer
import java.util.concurrent.TimeUnit

class StateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateBinding

    private val viewModel: StateModel by viewModel()

    private val mTotalAdapter = TotalAdapter()
    private val mStateAdapter = StateAdapter(this::navigateToStateDetailsActivity)
    private val adapter = MergeAdapter(mTotalAdapter, mStateAdapter)

    // Useful when back navigation is pressed.
    private var backPressedTime = 0L
    private val backSnackbar by lazy {
        Snackbar.make(binding.root, BACK_PRESSED_MESSAGE, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initData()
        initWorker()
    }

    private fun initViews() {
        setSupportActionBar(binding.appBarlayout.toolbar)

        binding.recycler.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(androidx.work.R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            androidx.work.R.id.menu_uimode -> {
                val uiMode = if (isDarkTheme()) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                applyTheme(uiMode)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun initData() {
        viewModel.covidLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false

                    val list = state.data.stateWiseDetails
                    mTotalAdapter.submitList(list.subList(0, 1))
                    mStateAdapter.submitList(list.subList(1, list.size - 1))

                    // Set Last Updated Time
                    supportActionBar?.subtitle = getString(
                        androidx.work.R.string.text_last_updated,
                        getPeriod(
                            list[0].lastUpdatedTime.toDateFormat()
                        )
                    )
                }
            }
        })

        if (viewModel.covidLiveData.value !is State.Success) {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getData()
    }

    private fun navigateToStateDetailsActivity(details: Statewise) {
        startActivity(Intent(this, DistrictActivity::class.java).apply {
            putExtra(DistrictActivity.KEY_STATE_DETAILS, details)
        })
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            JOB_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backSnackbar.dismiss()
            super.onBackPressed()
            return
        } else {
            backSnackbar.show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    companion object {
        const val JOB_TAG = "notificationWorkTag"
        const val BACK_PRESSED_MESSAGE = "Press back again to exit"
    }
}