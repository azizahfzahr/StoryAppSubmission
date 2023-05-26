package com.example.storyappsubmission.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappsubmission.R
import com.example.storyappsubmission.adapter.StoryListAdapter
import com.example.storyappsubmission.databinding.ActivityMainBinding
import com.example.storyappsubmission.login.LoginActivity
import com.example.storyappsubmission.maps.MapsActivity
import com.example.storyappsubmission.post.PostActivity
import com.example.storyappsubmission.pref.UserPreference

class MainActivity : AppCompatActivity() {
    private val dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewMod: MainViewModel
    private lateinit var adapter: StoryListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStory.layoutManager = LinearLayoutManager(this)
        adapter = StoryListAdapter()


        viewMod = ViewModelProvider(this, MainViewModel.ViewModelFactory(this, UserPreference.getInstance(dataStore)))[MainViewModel::class.java]

        viewMod.getUser().observe(this) { user ->
            if (user.token.isNotEmpty()) {
                viewMod.getStoryPage(user.token).observe(this){
                    adapter.submitData(lifecycle, it)
                }
                Log.d("result main :", user.token)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.apply {
            rvStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStory.setHasFixedSize(true)
            rvStory.adapter = adapter
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_post -> {
                Intent(this, PostActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Add Story", Toast.LENGTH_SHORT).show()

                }

            }
            R.id.menu_logout -> {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Logout", Toast.LENGTH_SHORT).show()
                    viewMod.logout()
                }

            }
            R.id.menu_maps -> {
                Intent(this, MapsActivity::class.java).also {
                    startActivity(it)
                    Toast.makeText(this,"Maps", Toast.LENGTH_SHORT).show()

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}