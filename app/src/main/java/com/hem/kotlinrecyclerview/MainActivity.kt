package com.hem.kotlinrecyclerview

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.hem.kotlinrecyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), ContactsListAdapter.ContactsClickListener {
    lateinit var mainContactViewModel: MainContactViewModel
    lateinit var mainBinding: ActivityMainBinding
    lateinit var contactList: List<Contact>
    private lateinit var searchView: SearchView
    private lateinit var progressDialog: Dialog  // Progress Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize binding
        initBinding()

        //initialize Recyclerview
        initRecyclerview()

        contactList = arrayListOf()

        // initialize progress dialog
        progressDialog = this.let { CustomProgressDialog.progressDialog(it) }

        //fetch Contact
        fetchContacts()
    }

    private fun initRecyclerview() {
        mainBinding.recyclerView.run { adapter = ContactsListAdapter(context, this@MainActivity) }
    }

    private fun initBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainContactViewModel = ViewModelProvider(this).get(MainContactViewModel::class.java)
    }


    private fun fetchContacts() {

        // show progress dialog
        progressDialog.show()

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                RestClient.api.clone().execute()
            }
            if (response.isSuccessful) {
                val items = Gson().fromJson(response.body?.string(), Contacts::class.java)
                launch(Dispatchers.Main) {
                    progressDialog.hide()
                    (mainBinding.recyclerView.adapter as ContactsListAdapter).setData(items)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_todo_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_todo)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (mainBinding.recyclerView.adapter as ContactsListAdapter).filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mainBinding.recyclerView.adapter as ContactsListAdapter).filter.filter(newText)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return true
    }

    override fun onItemClicked(contact: Contact) {

    }

}
