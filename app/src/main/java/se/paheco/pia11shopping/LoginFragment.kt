package se.paheco.pia11shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import se.paheco.pia11shopping.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    val model by viewModels<LoginViewModel>()

    var _binding : FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false)

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val errorObserver = Observer<String> { errorMess ->
            Toast.makeText(requireContext(), errorMess, Toast.LENGTH_LONG).show()
        }
        model.errorMessage.observe(viewLifecycleOwner, errorObserver)


        binding.loginButton.setOnClickListener {
            val userEmail = binding.loginEmailET.text.toString()
            val userPassword = binding.loginPasswordET.text.toString()

            model.login(userEmail, userPassword)
        }

        binding.registerButton.setOnClickListener {
            val userEmail = binding.loginEmailET.text.toString()
            val userPassword = binding.loginPasswordET.text.toString()

            model.register(userEmail, userPassword)
        }


    }
}