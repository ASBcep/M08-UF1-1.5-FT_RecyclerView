package asb.m08.m08_uf1_1_5_ft_recyclerview

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PlanetsAdapter (private val context: Context, private val planets: MutableList<Planet>):
    RecyclerView.Adapter<PlanetsAdapter.PlanetsViewHolder>(),
    View.OnClickListener,
    View.OnLongClickListener
{
    private val layout = R.layout.planets_items
    private var clickListener: View.OnClickListener? = null
    private var longClickListener: View.OnLongClickListener? = null

    class PlanetsViewHolder(val view: View): RecyclerView.ViewHolder(view)

    {
        var lblPlanetName = view.findViewById<TextView>(R.id.LblListPlanetName)
        var lblSatellites = view.findViewById<TextView>(R.id.LblListSatellites)
        //var lblPlanetName = TextView
        //var lblSatellites = TextView
        var imgPlanetImage: ImageView

        init
            {
                imgPlanetImage = view.findViewById(R.id.ImgListPlanet)
                //lblPlanetName = view.findViewById(R.id.LblListPlanetName)
                //lblSatellites = view.findViewById(R.id.LblListSatellites)
                //var lblPlanetName = view.findViewById<TextView>(R.id.LblListPlanetName)
                //var lblSatellites = view.findViewById<TextView>(R.id.LblListSatellites)
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        view.setOnClickListener(this)
        view.setOnLongClickListener(this)

        return PlanetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetsViewHolder, position: Int) {
        val planet = planets[position]
        bindPlanet(holder, planet)
    }

    /*aquest mètode es pot reemplaçar per la següent línia
    override fun getItemCount(): Int {
        return planets.size
    }
    */
    override fun getItemCount() = planets.size

    //per ficar les dades d'un planeta a l'element de la llista corresponent
    fun bindPlanet(holder: PlanetsViewHolder, planet: Planet)
    {
        val planetPath = context.getFilesDir().toString() + "/img/" + planet.image
        val bitmap = BitmapFactory.decodeFile(planetPath)
        holder.imgPlanetImage?.setImageBitmap(bitmap)
        holder.lblPlanetName?.text = planet.name
        holder.lblSatellites?.text = "Satellites: " + planet.numSatellites
    }

    fun setOnClickListener(listener: View.OnClickListener)
    {
        clickListener = listener
    }

    override fun onClick(view: View?)
    {
        clickListener?.onClick(view)
    }

    fun setOnLongClickListener(listener: View.OnLongClickListener)
    {
        longClickListener = listener
    }

    override fun onLongClick(view: View?):Boolean
    {
        longClickListener?.onLongClick(view)
        return true
        //return longClickListener?.onLongClick(view) ?: false//chatgpt suggereix
    }
}