package se.magictechnology.pia11shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ShoppingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
        }

        view.findViewById<Button>(R.id.addShoppingButton).setOnClickListener {
            val addshopname = view.findViewById<EditText>(R.id.shoppingNameET).text.toString()

            val tempShopitem = ShoppingItem(addshopname)

            val database = Firebase.database
            val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
            shopRef.push().setValue(tempShopitem)
        }

    }


    fun loadShopping() {
        var massadata = mutableListOf<ShoppingItem>()
    }
}

