package se.magictechnology.pia11shopping

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    lateinit var frag : ShoppingFragment

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val shoppingName : TextView
        val shoppingDelete : ImageView
        val shoppingCheckbox : CheckBox

        init {
            shoppingName = view.findViewById(R.id.shopNameTV)
            shoppingDelete = view.findViewById(R.id.shopDeleteImage)
            shoppingCheckbox = view.findViewById(R.id.shopCheckbox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentShop = frag.model.shopitems.value!![position]

        if(currentShop.shopamount == null) {
            holder.shoppingName.text = currentShop.shopname
        } else {
            holder.shoppingName.text = currentShop.shopname + " " + currentShop.shopamount!!.toString()
        }

        holder.shoppingDelete.setOnClickListener {
            frag.model.deleteShop(currentShop)
        }

        holder.shoppingCheckbox.isChecked = false
        currentShop.shopdone?.let {
            holder.shoppingCheckbox.isChecked = it
        }

        /*
        holder.shoppingCheckbox.setOnCheckedChangeListener { compoundButton, shopchecked ->
            Log.i("pia11debug", "CHECK CHANGED " + shopchecked.toString())
            frag.model.doneShop(currentShop, shopchecked)
        }
        */

        holder.shoppingCheckbox.setOnClickListener {
            Log.i("pia11debug", "CHECK CHANGED " + holder.shoppingCheckbox.isChecked.toString())
            frag.model.doneShop(currentShop, holder.shoppingCheckbox.isChecked)
        }

        /*
        holder.itemView.setOnClickListener {
            // TODO: Gå till läs mer
            frag.model.deleteShop(currentShop)
        }
        */
    }

    override fun getItemCount(): Int {
        frag.model.shopitems.value?.let {
            return it.size
        }
        return 0
    }

}