package com.juan.superheroapp_bbdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.room.Room
import com.juan.superheroapp_bbdd.SuperheroListActivity.Companion.EXTRA_ID
import com.juan.superheroapp_bbdd.data.ApiService
import com.juan.superheroapp_bbdd.data.PowerStatsResponse
import com.juan.superheroapp_bbdd.data.SuperHeroDetailResponse
import com.juan.superheroapp_bbdd.data.database.HeroDatabase
import com.juan.superheroapp_bbdd.data.database.entities.HeroDetailEntity
import com.juan.superheroapp_bbdd.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {

    private lateinit var room: HeroDatabase

    private lateinit var binding: ActivityDetailSuperheroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        room = Room.databaseBuilder(this, HeroDatabase::class.java, "superheroes").build()

        val id: String = intent.getStringExtra("idHero").orEmpty()
        getSuperheroInformation(id)
    }

    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            System.out.println(id)
            val superheroDetail = room.getHeroDao().getHeroDetailById(id.toInt())
            System.out.println(superheroDetail)
            runOnUiThread { createUI(superheroDetail) }

        }
    }

    private fun createUI(superhero: HeroDetailEntity) {
        //Picasso.get().load(superhero.image.url).into(binding.ivSuperhero)
        //binding.tvSuperheroName.text = superhero.name
        binding.tvSuperheroRealName.text = superhero.fullName
        binding.tvPublisher.text = superhero.publisher
        prepareStats(superhero)
    }

    private fun prepareStats(powerstats: HeroDetailEntity) {
        updateHeight(binding.viewIntelligence, powerstats.intelligence)
        updateHeight(binding.viewStrength, powerstats.strength)
        updateHeight(binding.viewSpeed, powerstats.speed)
        updateHeight(binding.viewDurability, powerstats.durability)
        updateHeight(binding.viewPower, powerstats.power)
        updateHeight(binding.viewCombat, powerstats.combat)
    }

    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        if (stat == "null") {
            params.height = pxToDp(0.toFloat())
        } else {
            params.height = pxToDp(stat.toFloat())
        }
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
    }

}