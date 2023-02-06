package se.magictechnology.pia11shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    lateinit var frag : ShoppingFragment

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

        val currentShop = frag.model.shopitems[position]

        if(currentShop.shopamount == null) {
            holder.shoppingName.text = currentShop.shopname
        } else {
            holder.shoppingName.text = currentShop.shopname + " " + currentShop.shopamount!!.toString()
        }

        holder.itemView.setOnClickListener {
            frag.model.deleteShop(currentShop) {
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount(): Int {
        return frag.model.shopitems.size
    }

}