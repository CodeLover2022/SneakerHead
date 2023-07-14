package com.example.sneakerhead

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.sneakerhead.Extensions.toast
import com.example.sneakerhead.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.view.*


class SignInFragment : Fragment() {



    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


   return inflater.inflate(R.layout.fragment_sign_in,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=FirebaseAuth.getInstance()
        view.btnSignIn.setOnClickListener{
            if(view.etEmailSignIn.text.isNotEmpty() && view.etPasswordSignIn.text.isNotEmpty())
            {
                signInUser(view.etEmailSignIn.text.toString(),view.etPasswordSignIn.text.toString())
            }
        }
        view.tvNavigateToSignUp.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun signInUser(email: String, password:String) {
        auth.signInWithEmailAndPassword(email, password) .addOnCompleteListener {
            if(it.isSuccessful){
                requireActivity().toast("User Signed in")
                findNavController().navigate(R.id.action_signInFragment_to_mainFragment2)
            }
            else {
                requireActivity().toast(it.exception!!.localizedMessage!!)

            }
        }

    }


}