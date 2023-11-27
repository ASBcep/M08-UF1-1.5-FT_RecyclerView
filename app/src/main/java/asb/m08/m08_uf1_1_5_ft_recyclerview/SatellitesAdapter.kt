package asb.m08.m08_uf1_1_5_ft_recyclerview

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

/*
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
*/

class SatellitesAdapter (context: Context, val layout: Int, val satellites: List<Satellite>):
    ArrayAdapter<Satellite>(context, layout, satellites)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        var view: View

        if(convertView != null)
        {
            view = convertView
        } else {
            view = LayoutInflater.from(getContext()).inflate(layout, parent, false)
        }

        bindSatellite(view, satellites[position])

        return view
    }
    fun bindSatellite(view: View, satellite: Satellite)
    {
        val imgSatellite = view.findViewById(R.id.ImgListSatellite) as ImageView
        //imgSatellite.setImageResource(satellite.image)


        val satellitePath = context.getFilesDir().toString() + "/img/" + satellite.image
        val bitmap = BitmapFactory.decodeFile(satellitePath)
        imgSatellite.setImageBitmap(bitmap)

        satellite.image = imgSatellite.toString()

        val lblSatelliteName = view.findViewById(R.id.LblListSatelliteName) as TextView
        lblSatelliteName.text = satellite.name

    }
}

