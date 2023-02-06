package se.magictechnology.pia11shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    var shopitems = mutableListOf<ShoppingItem>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val shoppingName : TextView

        init {
            shoppingName = view.findViewById(R.id.shopNameTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shoppingName.text = shopitems[position].shopname
    }

    override fun getItemCount(): Int {
        return shopitems.size
    }

}