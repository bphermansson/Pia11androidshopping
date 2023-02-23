package se.paheco.pia11shopping

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    lateinit var frag : ShoppingFragment

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val shoppingName : TextView
        val shoppingDelete : ImageView
        val shoppingCheckbox : CheckBox

        val shoppingAmount : TextView
        val shoppingImageview : ImageView

        init {
            shoppingName = view.findViewById(R.id.shopNameTV)
            shoppingDelete = view.findViewById(R.id.shopDeleteImage)
            shoppingCheckbox = view.findViewById(R.id.shopCheckbox)
            shoppingAmount = view.findViewById(R.id.shopAmountTV)
            shoppingImageview = view.findViewById(R.id.shopImageview)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentShop = frag.model.shopitems.value!![position]

        var storageRef = Firebase.storage.reference
        var imageRef = storageRef.child("shoppingimages").child(Firebase.auth.currentUser!!.uid)
        var shopImageRef = imageRef.child(currentShop.fbid!!)


        holder.shoppingImageview.setImageBitmap(null)

        /*
        shopImageRef.downloadUrl.addOnSuccessListener {
            Glide.with(frag).load(it.toString()).into(holder.shoppingImageview)
        }
        */
        // TODO: Fixa glide
        //Glide.with(frag).load(shopImageRef).into(holder.shoppingImageview)


        imageRef.child(currentShop.fbid!!).getBytes(1_000_000).addOnSuccessListener {
            var bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)

            holder.shoppingImageview.setImageBitmap(bitmap)

        }.addOnFailureListener {

        }


        holder.shoppingName.text = currentShop.shopname

        if(currentShop.shopamount == null) {
            holder.shoppingAmount.text = ""
        } else {
            holder.shoppingAmount.text = currentShop.shopamount!!.toString()
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


        holder.itemView.setOnClickListener {
            frag.requireActivity().supportFragmentManager.commit {
                add(R.id.mainFragCon, ShopDetailFragment(currentShop))
                addToBackStack(null)
            }
        }

    }

    override fun getItemCount(): Int {
        frag.model.shopitems.value?.let {
            return it.size
        }
        return 0
    }

}