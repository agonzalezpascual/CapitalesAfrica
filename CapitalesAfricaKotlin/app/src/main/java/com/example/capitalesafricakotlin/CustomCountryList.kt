package com.example.capitalesafricajava

import android.app.Activity
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class CustomCountryList(
    private val context: Activity,
    private val countryNames: Array<String?>,
    private val capitalNames: Array<String?>,
    private val imageid: Array<Int?>
) : ArrayAdapter<Any?>(
    context, R.layout.row_layout, countryNames) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        val inflater = context.layoutInflater
        if (convertView == null) row = inflater.inflate(R.layout.row_layout, null, true)
        val textViewCountry = row!!.findViewById<View>(R.id.textViewCountry) as TextView
        val textViewCapital = row.findViewById<View>(R.id.textViewCapital) as TextView
        val imageFlag = row.findViewById<View>(R.id.imageViewFlag) as ImageView
        textViewCountry.text = countryNames[position]
        textViewCapital.text = capitalNames[position]
        imageid[position]?.let { imageFlag.setImageResource(it) }
        return row
    }
}