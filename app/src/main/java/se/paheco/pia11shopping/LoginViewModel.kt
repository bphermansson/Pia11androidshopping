package se.paheco.pia11shopping

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    val errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun login(email : String, password : String) {

        if(email == "") {
            errorMessage.value = "Fyll i epost"
            return
        }
        if(password == "") {
            errorMessage.value = "Fyll i lösenord"
            return
        }

        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful == false) {
                errorMessage.value = "Fel inloggning"
            }
        }

    }

    fun register(email : String, password : String) {

        if(email == "") {
            errorMessage.value = "Fyll i epost"
            return
        }
        if(password == "") {
            errorMessage.value = "Fyll i lösenord"
            return
        }

        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Toast.makeText(requireContext(), "Register ok.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    //Toast.makeText(requireContext(), "Register failed.", Toast.LENGTH_SHORT).show()
                    //errorMessage.value = "Fel registrering"
                    errorMessage.value = task.exception!!.localizedMessage!!
                    Log.i("pia11debug", task.exception!!.message!!)
                    Log.i("pia11debug", task.exception!!.localizedMessage!!)
                }
            }
    }

}