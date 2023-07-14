package com.example.sneakerhead

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.sneakerhead.Extensions.toast
import com.example.sneakerhead.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : Fragment() {



  private var binding:FragmentSignUpBinding?=null
    private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=FirebaseAuth.getInstance()
        view.btnSignUp.setOnClickListener{
            if(view.etEmailSignUp.text.isNotEmpty() && view.etPasswordSignUp.text.isNotEmpty() && view.etNameSignUp.text.isNotEmpty())
            {
                createUser(view.etEmailSignUp.text.toString(),view.etPasswordSignUp.text.toString())
            }

        }
        view.tvNavigateToSignIn.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_signUpFragment_to_signInFragment)
        }

    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                requireActivity().toast("User Created")
                Navigation.findNavController(view!!).navigate(R.id.action_signUpFragment_to_signInFragment)

            }
            else
            {
                requireActivity().toast(it.exception.toString())
            }
        }

    }




}