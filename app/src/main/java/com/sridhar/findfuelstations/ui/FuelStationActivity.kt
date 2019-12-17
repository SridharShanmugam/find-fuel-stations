package com.sridhar.findfuelstations.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sridhar.findfuelstations.R
import com.sridhar.findfuelstations.api.WebClient
import com.sridhar.findfuelstations.api.WebService
import com.sridhar.findfuelstations.repository.FuelStationRepository
import com.sridhar.findfuelstations.viewmodel.FuelStationViewModel
import com.sridhar.findfuelstations.viewmodel.MyViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class FuelStationActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var viewModel: FuelStationViewModel
    private lateinit var adapter: FuelStationListAdapter
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initView()
        bindData()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun initView() {
        val webService = WebClient.getWebClient().create(WebService::class.java)
        val mViewModelFactory = MyViewModelFactory(FuelStationRepository(webService))
        viewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(FuelStationViewModel::class.java)
        adapter = FuelStationListAdapter(viewModel)
        fuelStationRv.adapter = adapter
    }

    private fun bindData() {
        viewModel.fuelStationResponse.observe(this, Observer {
            adapter.fuelStations = it.fuelStations
            adapter.notifyDataSetChanged()
        })
        viewModel.getFuelStations()
    }

    private fun initSearchMenu(searchMenuItem: MenuItem) {
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                menu.findItem(R.id.action_refresh).isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                menu.findItem(R.id.action_refresh).isVisible = true
                if (viewModel.fuelStationResponse.value != null) {
                    adapter.fuelStations = viewModel.fuelStationResponse.value!!.fuelStations
                    adapter.notifyDataSetChanged()
                }
                return true
            }

        })
        (searchMenuItem.actionView as SearchView).setOnQueryTextListener(this)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (searchMenuItem.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (!query.isNullOrBlank() && viewModel.fuelStationResponse.value != null)
                adapter.filter.filter(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        initSearchMenu(menu.findItem(R.id.action_search))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_refresh -> {
                viewModel.getFuelStations()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (viewModel.fuelStationResponse.value != null) adapter.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (viewModel.fuelStationResponse.value != null) adapter.filter.filter(newText)
        return true
    }
}
