package ocs.com.dr_tips.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ocs.com.dr_tips.model.Country


class CountriesHintArrayAdapter(context: Context?, resource: Int, textViewResourceId: Int, objects: MutableList<Country>?) : ArrayAdapter<Country>(context, resource, textViewResourceId, objects) {
    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        (view.findViewById(android.R.id.text1) as TextView).text = getItem(position).name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view.findViewById(android.R.id.text1) as TextView).text = getItem(position).name
        if (position == 0) {
            // Set the hint text color gray
            (view.findViewById(android.R.id.text1) as TextView).setTextColor(Color.GRAY);
        } else {
            (view.findViewById(android.R.id.text1) as TextView).setTextColor(Color.BLACK);
        }
        return view
    }

    override fun getCount(): Int = super.getCount()
}