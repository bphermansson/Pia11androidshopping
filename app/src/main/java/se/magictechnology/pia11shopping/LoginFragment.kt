package se.magictechnology.pia11shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val userEmail = view.findViewById<EditText>(R.id.loginEmailET).text.toString()
            val userPassword = view.findViewById<EditText>(R.id.loginPasswordET).text.toString()


            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Toast.makeText(requireContext(), "Login ok.", Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "Login fail.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        view.findViewById<Button>(R.id.registerButton).setOnClickListener {

            val userEmail = view.findViewById<EditText>(R.id.loginEmailET).text.toString()
            val userPassword = view.findViewById<EditText>(R.id.loginPasswordET).text.toString()

            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Toast.makeText(requireContext(), "Register ok.", Toast.LENGTH_SHORT).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(requireContext(), "Register failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }
}