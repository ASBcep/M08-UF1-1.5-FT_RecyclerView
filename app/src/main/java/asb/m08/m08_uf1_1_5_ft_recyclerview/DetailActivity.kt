package asb.m08.m08_uf1_1_5_ft_recyclerview

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity: AppCompatActivity()
{
    //creem una constant per a usar-la en ambdúes classes MainActivity.kt i DetailActivity.kt
    object planetConstants
    {
        const val SATELLITE = "SATELLITE"
        const val PLANET = "Planet"
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //obtenim "Intent" que hem enviat des d'ActivityMain.kt
        val intent = getIntent()
        val planet = intent.getSerializableExtra(planetConstants.PLANET) as Planet

        val lblPlanetName = findViewById(R.id.LblDetailPlanetName) as TextView
        val imgPlanetImage = findViewById(R.id.ImgDetailPlanetImage) as ImageView
        val lstSatellites = findViewById(R.id.LstSatellites) as GridView
        val btnBack = findViewById(R.id.BtnBack) as Button

        lblPlanetName.text = planet.name

        val planetPath = this.getFilesDir().toString() + "/img/" + planet.image
        val bitmap = BitmapFactory.decodeFile(planetPath)
        imgPlanetImage.setImageBitmap(bitmap)
        planet.image = imgPlanetImage.toString()

        //imgPlanetImage.setImageResource(planet.image)
        val adapter = SatellitesAdapter(this, R.layout.satellites_item, planet.satellites)
        lstSatellites.adapter = adapter

        //event que s'activa un cop es cliqui a la gridview de satèl·lits
        lstSatellites.onItemClickListener = AdapterView.OnItemClickListener()
        {
                _, _, i, _ ->
            //passarem un intent a la classe MainActivity.kt amb el nom del satèl·lit
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(planetConstants.SATELLITE, planet.satellites[i].name)

            //tancar activity retornant un resultat
            setResult(RESULT_OK, intent)
            //tancar activity
            finish()

        }

        btnBack.setOnClickListener()
        {
            //tancar activity sense retornar cap resultat
            setResult(RESULT_CANCELED)
            //tancar activity
            finish()
        }

        //veure video 12 activities kotlin
    }
}