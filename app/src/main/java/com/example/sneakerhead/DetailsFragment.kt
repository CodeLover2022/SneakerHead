package com.example.sneakerhead

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sneakerhead.Extensions.toast
import com.example.sneakerhead.Models.ProductOrderModel
import com.example.sneakerhead.Models.ShoeDisplayModel
import com.example.sneakerhead.rvadapters.SizeAdapter
import com.example.sneakerhead.rvadapters.SizeOnClickInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlin.random.Random


class DetailsFragment : Fragment(),SizeOnClickInterface {
    // TODO: Rename and change types of parameters
  private lateinit var dbRef:DatabaseReference
  private lateinit var dbRefOrder:DatabaseReference
  private lateinit var auth:FirebaseAuth
private var orderSize:String?=null
    private lateinit var orderName:String
    private lateinit var orderImgUrl:String
    private lateinit var currentUid:String
    private lateinit var price:String
    private  var productId:String?=null
    private var quantity:Int=-1
   private lateinit var orderedProduct: ProductOrderModel

private lateinit var sizeList:ArrayList<String>
private lateinit var adapter:SizeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val v= inflater.inflate(R.layout.fragment_details, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id_item=requireArguments().getString("itemId")
        auth=FirebaseAuth.getInstance()
currentUid=auth.uid.toString()
        dbRef=FirebaseDatabase.getInstance().getReference("Products")
        showingInDetail(id_item!!)
        sizeList= ArrayList()
        sizeList.add("5")
        sizeList.add("6")
        sizeList.add("7")
        sizeList.add("8")
        sizeList.add("9")
        sizeList.add("10")
        view.rvSelectSize.adapter=SizeAdapter(requireContext(),sizeList,this)
        view.detailActualToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        view.btnDetailsAddToCart.setOnClickListener {
            orderedProduct=ProductOrderModel(currentUid,productId,orderImgUrl,orderName,orderSize,quantity,price)
            if(orderSize.isNullOrEmpty())
            {
                requireActivity().toast("Please select a size")
            }
            else
            {

                addToCart(orderedProduct)
                findNavController().navigate(R.id.action_detailsFragment_to_cartFragment2)
            }
        }
        dbRefOrder=FirebaseDatabase.getInstance().getReference("Orders")

    }

    private fun addToCart(orderedProduct:ProductOrderModel) {
        dbRefOrder.child(currentUid).child(productId!!).setValue(orderedProduct).addOnCompleteListener {
            if(it.isSuccessful)
            {
                requireActivity().toast("Order Successfully delivered")
            }
            else
            {
                requireActivity().toast(it.exception.toString())
            }
        }

    }

    private fun showingInDetail(itemId:String) {
        val valueEvent=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for( i in snapshot.children)
                    {

                        val products=i.getValue(ShoeDisplayModel::class.java)
                        if(products!!.id==itemId)
                        {
                            orderName=products!!.name!!
                            orderImgUrl=products!!.imageUrl!!
                            productId=products!!.id!!
                            price=products!!.price!!

                            tvDetailsProductDescription.text=products.description
                            tvDetailsProductName.text=products.name
                            tvDetailsProductPrice.text=products.price
                            Glide.with(requireContext()).load(products.imageUrl).into(ivDetails)

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                requireActivity().toast(error.message)
            }


        }
        dbRef.addValueEventListener(valueEvent)
    }

    override fun onClick(button: Button, position: Int) {
        orderSize=button.text.toString()
        requireActivity().toast("Size ${orderSize} selected")

    }


}