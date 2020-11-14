package com.example.samplekotlinapplication.adapters
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplekotlinapplication.R
import com.example.samplekotlinapplication.persistence.TestEntity

import java.util.ArrayList

class TestAdapter(private val activity: Activity, listdata: MutableList<TestEntity>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    internal var testEntities: MutableList<TestEntity> = ArrayList()


    init {
        this.testEntities = listdata
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.test_entities_layout, parent, false)
        return ViewHolder(rowView)

    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.IdText.text = "Id: " + testEntities[position].id
        holder.publicationDateText.text =
            "Publication Date: " + testEntities[position].publicationDate
        holder.articleTypeText.text = "Article Type: " + testEntities[position].articleType
        holder.abstractText.text = "Abstract: " + testEntities[position].abstract


    }


    override fun getItemCount(): Int {
        return testEntities.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var IdText: TextView
        internal var publicationDateText: TextView
        internal var articleTypeText: TextView
        internal var abstractText: TextView

        init {
            IdText = itemView.findViewById<View>(R.id.IdText) as TextView
            publicationDateText = itemView.findViewById<View>(R.id.publicationDateText) as TextView
            articleTypeText = itemView.findViewById<View>(R.id.articleTypeText) as TextView
            abstractText = itemView.findViewById<View>(R.id.abstractText) as TextView

        }


    }

}









