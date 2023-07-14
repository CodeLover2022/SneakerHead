package com.example.sneakerhead

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakerhead.Extensions.toast
import com.example.sneakerhead.Models.CartModel
import com.example.sneakerhead.rvadapters.CartAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*


class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth:FirebaseAuth
    private lateinit var cart:ArrayList<CartModel>
    private var subTotalPrice = 0
    private var totalPrice = 240
    private lateinit var dbRefss:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_cart, container, false)
    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=FirebaseAuth.getInstance()
        dbRefss=FirebaseDatabase.getInstance().getReference("Orders").child(auth.uid.toString())
        cart= ArrayList()
        CartShowing()
        view.rvCartItems.layoutManager=LinearLayoutManager(requireContext())
        view.rvCartItems.adapter=CartAdapter(context!!,cart)
        view.cartActualToolbar.setOnClickListener{
            Navigation.findNavController(requireView()).popBackStack()
        }


    }

    private fun CartShowing() {
        val value=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    var cnt=0
                    for(i in snapshot.children)
                    {
                        val carts=i.getValue(CartModel::class.java)
                        cart.add(carts!!)
                        cnt++;
                        subTotalPrice += carts.price!!.toInt()
                        totalPrice += carts.price!!.toInt()
                       view?.tvLastSubTotalprice?.text = subTotalPrice.toString()
                        view?.tvLastTotalPrice?.text = totalPrice.toString()
                       view?.tvLastSubTotalItems?.text = "SubTotal Items(${cnt})"
                        view?.delivery?.text="Rs240"

                    }
                    view?.rvCartItems?.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                requireActivity().toast(error.message)
            }

        }
        dbRefss.addValueEventListener(value)
    }


}