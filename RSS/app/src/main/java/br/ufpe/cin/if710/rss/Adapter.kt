package br.ufpe.cin.if710.rss

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import br.ufpe.cin.if710.rss.db.SQLiteRSSHelper


class Adapter(private val db: SQLiteRSSHelper) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(group: ViewGroup, type: Int): Adapter.ViewHolder {
        val layout = LayoutInflater.from(group.context).inflate(R.layout.itemlista, group, false) as LinearLayout
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        val vhText = viewHolder.layout.findViewById(R.id.item_data) as TextView
        val vhTitle = viewHolder.layout.findViewById(R.id.item_titulo) as TextView
        vhText.text = db.items[pos].description
        vhTitle.text = db.items[pos].title
        vhTitle.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(db.items[pos].link))
            db.markAsRead(db.items[pos].link)
            startActivity(vhTitle.context, intent, null)
        }
    }

    override fun getItemCount() = db.items.size
}
