package com.example.sneakerhead

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.sneakerhead.Extensions.toast
import com.example.sneakerhead.Models.ShoeDisplayModel



import com.example.sneakerhead.rvadapters.MainCategoryAdapter
import com.example.sneakerhead.rvadapters.ProductOnClickInterface
import com.example.sneakerhead.rvadapters.ShoeDisplayAdapter
import com.google.android.play.integrity.internal.c
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_main.view.*


class MainFragment : Fragment(),ProductOnClickInterface {
private lateinit var productList:ArrayList<ShoeDisplayModel>

private lateinit var databaseReference: DatabaseReference
private lateinit var auth:FirebaseAuth
private lateinit var productsAdapter:ShoeDisplayAdapter
private var likeDBRef= Firebase.firestore.collection("Liked Products")

    private lateinit var categoryList:ArrayList<String>
    private lateinit var categoryAdapter:MainCategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList= ArrayList()
        auth=FirebaseAuth.getInstance()
        productList= ArrayList()
        databaseReference=FirebaseDatabase.getInstance().getReference("Products")
        // region implements category Recycler view
        categoryList.add("Trending")
        view.rvMainCategories.setHasFixedSize(true)
        val categoryLayoutManager= LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        view.rvMainCategories.layoutManager=categoryLayoutManager
        setCategoryList()
        categoryAdapter=MainCategoryAdapter(context!!,categoryList)
        view.rvMainCategories.adapter=categoryAdapter

        val productLayoutManager=GridLayoutManager(context,2)
        setProductsData()
        Log.d("hello",productList.toString())
        productsAdapter=ShoeDisplayAdapter(requireContext(),productList,this)
        view.rvMainProductList.layoutManager=productLayoutManager
        view.rvMainProductList.adapter=productsAdapter

        navigationBarWorking()

    }

    private fun navigationBarWorking() {
        view!!.bnvMain.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.mainFragment -> {
     findNavController().navigate(R.id.action_mainFragment2_self)
                    true
                }
                R.id.likeFragment -> {
                    findNavController().navigate(R.id.action_mainFragment2_to_likeFragment2)
                    true

                }
                R.id.cartFragment -> {
           findNavController().navigate(R.id.action_mainFragment2_to_cartFragment2)
                    true

                }

                else -> false
            }



        }
    }

    private fun setProductsData() {
        val valueEvent=object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                if(snapshot.exists())
                {for(i in snapshot.children)
                {
                    val products=i.getValue(ShoeDisplayModel::class.java)
                    productList.add(products!!)
                }
                    productsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
            }


        }
        databaseReference.addValueEventListener(valueEvent)
    }



    private fun setCategoryList() {
         val valueEvent=object: ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                 categoryList.clear()
                 categoryList.add("Trending")
                 if(snapshot.exists())
                 {
                     for(dataSnapshot in snapshot.children)
                     {
                         val products=dataSnapshot.getValue(ShoeDisplayModel::class.java)
                         if(!categoryList.contains(products!!.brand)) {
                             categoryList.add(products!!.brand!!)
                         }
                     }

                     categoryAdapter.notifyDataSetChanged()
                 }
             }

             override fun onCancelled(error: DatabaseError) {
                 Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
             }

         }
        databaseReference.addValueEventListener(valueEvent)
    }

    override fun onCLickProduct(item: ShoeDisplayModel) {
findNavController().navigate(R.id.action_mainFragment2_to_detailsFragment,Bundle().apply {
    putString("itemId",item.id)
})



           }



       }



