package asb.m08.m08_uf1_1_5_ft_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity()
{
    //aquesta funció permet retornar variables des d'una altra activity
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            val lblSatellite = findViewById(R.id.LblSelectedSatellite) as TextView
            if(it.resultCode == RESULT_OK) {
                //retornar un string
                val satellite = it.data?.getStringExtra(DetailActivity.planetConstants.SATELLITE)
                /*retornar un objecte
                val satellite = it.data?.getSerializableExtra()
                */

                lblSatellite.text = "Selected Satellite: " + satellite
            }else if (it.resultCode == RESULT_CANCELED){ //opcional, si no restorna RESULT_OK hauria de donar RESULT_CANCELLED
                lblSatellite.text = ""
            }else { //menys codi que amb else if
                lblSatellite.text = ""
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val planets = getPlanets()

        //3 maneres de cridar IDs:
        //val lstPlanets: ListView = findViewById(R.id.LstPlanets)
        //val lstPlanets = findViewById<ListView>(R.id.LstPlanets)
        //val lstPlanets = this.findViewById(R.id.LstPlanets) as ListView

        val lstPlanets = this.findViewById(R.id.LstPlanets) as RecyclerView


        val BtnOk = this.findViewById(R.id.BtnOk) as Button

        val adapter = PlanetsAdapter(this, planets)
        lstPlanets.hasFixedSize()
        lstPlanets.layoutManager = LinearLayoutManager(this)
        lstPlanets.adapter = adapter

        adapter.setOnClickListener()
        {
            val intent = Intent(this, DetailActivity::class.java)
            val planet = planets[lstPlanets.getChildAdapterPosition(it)]
            intent.putExtra(DetailActivity.planetConstants.PLANET, planet)
            getResult.launch(intent)
        }
        adapter.setOnLongClickListener()
        {
            //Em deso el planeta per poder fer el toast després
            val planet = planets[lstPlanets.getChildAdapterPosition(it)]
            //elimino el planeta de la llista
            planets.removeAt(lstPlanets.getChildAdapterPosition(it))
            adapter.notifyDataSetChanged()
            //mostro toast informant de l'eliminació
            Toast.makeText(this, "Planet " + planet.name + " was deleted!", Toast.LENGTH_LONG).show()

            true
        }


        BtnOk.setOnClickListener()
        {
            //agafo valors del'editText de nou planeta
            val txtNewPlanet = findViewById(R.id.TxtNewPlanet) as EditText

            //codi per un array d'strings
            // val newPlanet = txtNewPlanet.text.toString()

            val newPlanetName = txtNewPlanet.text.toString()

            //agafo valors del'editText de número de satèl·lits
            val txtNumSatellites = findViewById(R.id.TxtSatellites) as EditText
            val numSatellitesString = txtNumSatellites.text.toString()
            var numSatellites = 0


            if(newPlanetName != "")
            {
                //comprovo que s'hagi escrit un número de satèl·lits
                if (numSatellitesString != "")
                {
                    var numSatellites = numSatellitesString.toInt()
                }
                val newPlanet = Planet(newPlanetName, numSatellites, "eris.png", listOf())
                planets.add(newPlanet)
                txtNewPlanet.setText("")
                txtNumSatellites.setText("")
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "You must write the planet name", Toast.LENGTH_SHORT).show()

            }
        }
    }
    fun getPlanets(): MutableList<Planet> {
        return mutableListOf(Planet("Mercury", 0, "mercury.png", listOf()),
            Planet("Venus",0, "venus.png", listOf()),
            Planet("Earth",1, "earth.png",
                listOf(Satellite("Moon", "moon.png"))),
            Planet("Mars",2, "mars.jpg", listOf()),
            Planet("Jupiter",72, "jupiter.jpg",
                listOf(Satellite("Io", "io.png"),
                    Satellite("Europa", "europa.png"),
                    Satellite("Ganymede", "ganymede.png"),
                    Satellite("Callisto", "callisto.png")
                )),
            Planet("Saturn",82, "saturn.png",
                listOf(Satellite("Titan", "titan.png"))),
            Planet("Uranus",27, "uranus.png", listOf()),
            Planet("Neptune",14, "neptune.jpg",
                listOf(Satellite("Triton", "triton.png")))
        )
    }
}