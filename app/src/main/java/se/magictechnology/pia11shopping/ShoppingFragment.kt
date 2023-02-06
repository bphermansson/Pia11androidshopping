package se.magictechnology.pia11shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import se.magictechnology.pia11shopping.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment() {

    var _binding : FragmentShoppingBinding? = null
    val binding get() = _binding!!

    var shopadapter = ShoppingAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_shopping, container, false)

        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingRV.adapter = shopadapter
        binding.shoppingRV.layoutManager = LinearLayoutManager(requireContext())



        /*
        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            Firebase.auth.signOut()
        }
        */

        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
        }

        binding.addShoppingButton.setOnClickListener {
            val addshopname = binding.shoppingNameET.text.toString()

            val tempShopitem = ShoppingItem(addshopname)

            val database = Firebase.database
            val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
            shopRef.push().setValue(tempShopitem)

            loadShopping()
        }

        loadShopping()
    }


    fun loadShopping() {
        /*
        var shoplist = mutableListOf<ShoppingItem>()

        var s1 = ShoppingItem("banan")
        var s2 = ShoppingItem("apelsin")

        shoplist.add(s1)
        shoplist.add(s2)

        shopadapter.shopitems = shoplist
        */


        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
        shopRef.get().addOnSuccessListener {
            val shoplist = mutableListOf<ShoppingItem>()
            it.children.forEach { childsnap ->
                shoplist.add(childsnap.getValue<ShoppingItem>()!!)
            }
            shopadapter.shopitems = shoplist
            shopadapter.notifyDataSetChanged()
        }

    }
}

