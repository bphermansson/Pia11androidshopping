package se.magictechnology.pia11shopping

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class ShoplistViewModel : ViewModel() {

    //var shopitems = mutableListOf<ShoppingItem>()


    val shopitems: MutableLiveData<List<ShoppingItem>> by lazy {
        MutableLiveData<List<ShoppingItem>>()
    }

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    //fun addShopping(addshopname : String, addshopamount : Int, callback: () -> Unit) {
    fun addShopping(addshopname : String, addshopamount : String) {

        if(addshopname == "") {
            errorMessage.value = "Ange text"
            return
        }
        val shopamount = addshopamount.toIntOrNull()
        if(shopamount == null) {
            errorMessage.value = "Skriv en siffra"
            return
        }

        errorMessage.value = ""

        val tempShopitem = ShoppingItem(addshopname, shopamount)

        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
        shopRef.push().setValue(tempShopitem).addOnCompleteListener {
            loadShopping()
        }

    }

    //fun loadShopping(callback: () -> Unit) {
    fun loadShopping() {

        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
        shopRef.get().addOnSuccessListener {
            val shoplist = mutableListOf<ShoppingItem>()
            it.children.forEach { childsnap ->
                val tempShop = childsnap.getValue<ShoppingItem>()!!
                tempShop.fbid = childsnap.key
                shoplist.add(tempShop)
            }
            shopitems.value = shoplist
        }

    }

    //fun deleteShop(delitem : ShoppingItem, callback: () -> Unit) {
    fun deleteShop(delitem : ShoppingItem) {
        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)

        shopRef.child(delitem.fbid!!).removeValue().addOnCompleteListener {
            loadShopping()
        }
    }

    fun doneShop(doneitem : ShoppingItem, isdone : Boolean) {
        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)

        var saveitem = doneitem
        saveitem.shopdone = isdone
        shopRef.child(doneitem.fbid!!).setValue(saveitem).addOnCompleteListener {
            //loadShopping()
        }
    }

    fun saveShopItem(saveitem : ShoppingItem) {
        val database = Firebase.database
        val shopRef = database.getReference("androidshopping").child(Firebase.auth.currentUser!!.uid)
        shopRef.child(saveitem.fbid!!).setValue(saveitem).addOnCompleteListener {
            loadShopping()
        }
    }

    fun saveShopImage(saveitem : ShoppingItem, saveimage : Bitmap) {

        val smallerImage = resizePhoto(saveimage, 500)

        var storageRef = Firebase.storage.reference
        var imageRef = storageRef.child("shoppingimages").child(Firebase.auth.currentUser!!.uid)

        val baos = ByteArrayOutputStream()
        smallerImage.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val data = baos.toByteArray()

        imageRef.child(saveitem.fbid!!).putBytes(data).addOnFailureListener {
            Log.i("PIA11DEBUG", "UPLOAD FAIL")
        }.addOnSuccessListener {
            Log.i("PIA11DEBUG", "UPLOAD OK")
        }
    }

    fun resizePhoto(bitmap: Bitmap, newWidth : Int): Bitmap {
        val aspRat : Float = bitmap.height.toFloat() / bitmap.width.toFloat()
        val newHeight = newWidth * aspRat
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight.toInt(), false)
    }
}