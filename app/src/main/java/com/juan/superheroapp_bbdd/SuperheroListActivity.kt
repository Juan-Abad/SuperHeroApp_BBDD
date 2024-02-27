package com.juan.superheroapp_bbdd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.juan.superheroapp_bbdd.databinding.ActivitySuperheroListBinding
import com.juan.superheroapp_bbdd.data.ApiService
import com.juan.superheroapp_bbdd.data.SuperHeroDataResponse
import com.juan.superheroapp_bbdd.data.SuperHeroDetailResponse
import com.juan.superheroapp_bbdd.data.database.HeroDatabase
import com.juan.superheroapp_bbdd.data.database.entities.HeroEntity
import com.juan.superheroapp_bbdd.data.database.entities.toDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuperheroListActivity : AppCompatActivity() {

    private lateinit var room: HeroDatabase

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivitySuperheroListBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = Room.databaseBuilder(this, HeroDatabase::class.java, "superheroes").build()

        retrofit = getRetrofit()

        fillDatabase()
        initUI()
    }

    private fun fillDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            room.getHeroDao().deleteAllHero()
            room.getHeroDao().deleteAllHeroDetail()
            CoroutineScope(Dispatchers.IO).launch {
                val myHeroResponse: Response<SuperHeroDataResponse> =
                    retrofit.create(ApiService::class.java).getSuperheroes()

                myHeroResponse.body()?.superheroes?.let {
                    room.getHeroDao().insertAll(it.map { it.toDatabase() })
                }
                val superheroes = myHeroResponse.body()?.superheroes
                superheroes?.iterator()?.let { iterator ->
                    while (iterator.hasNext()) {
                        val HeroDetail = retrofit.create(ApiService::class.java).getSuperheroDetail(iterator.next().superheroId)
                        HeroDetail.body()
                            ?.let { room.getHeroDao().insertHeroDetail(it.toDatabase()) }
                    }
                }
            }
        }
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = SuperheroAdapter { superheroId -> navigateToDetail(superheroId) }
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val listHero: List<HeroEntity> = room.getHeroDao().getHeroByName(query + "%")


            if (listHero.isNotEmpty()) {
                runOnUiThread {
                    adapter.updateList(listHero)
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra("idHero", id)
        startActivity(intent)
    }

}