package com.example.hungryhub.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hungryhub.model.CartItems
import com.example.hungryhub.PayOutActivity
import com.example.hungryhub.adaptar.CartAdapter
import com.example.hungryhub.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var imageuris: MutableList<String>
    private lateinit var ingredients: MutableList<String>
    private lateinit var quantitys: MutableList<Int>// it for containing quantities thats why its a Intp
    private lateinit var userId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        retreriveCartItems()

        val user = mAuth.currentUser

        if (user != null) {
            // User is signed in, you can access the UID
            val uid = user.uid

        } else {
            // User is signed out
            Toast.makeText(requireContext(), "mot signgneedind", Toast.LENGTH_SHORT).show()
        }


        binding.proceedbutton.setOnClickListener {

            getOrderItemsDetails()     // this function will get first data from firebase and then after getting it will pass all details to payoutactivity

        }
        return binding.root
    }

    companion object {
    }

    private fun setAdapter() {
        cartAdapter =
            CartAdapter(
                requireContext(),
                foodNames,
                foodPrices,
                foodDescriptions,
                imageuris,
                quantitys
            )                                                  // contains corresping uri of otem of cart item
        binding.CartRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.CartRecyclerView.adapter = cartAdapter
    }

    private fun retreriveCartItems() {
        // Get reference to the Firebase database
        database = FirebaseDatabase.getInstance()
        userId = mAuth.currentUser?.uid ?: ""

        val foodRef: DatabaseReference =
            database.reference.child("Users").child(userId).child("CartItems")

        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        imageuris = mutableListOf()
        ingredients = mutableListOf()
        quantitys = mutableListOf()

        // Fetch data from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Loop through each food item

                for (foodSnapshot in dataSnapshot.children) {
                    // Get the FoodItem object from the child node
                    val cartItem = foodSnapshot.getValue(CartItems::class.java)
                    // Add the foodname to the foodNames list
                    cartItem?.foodNames?.let {
                        foodNames.add(it)
                    }
                    cartItem?.foodPrice?.let {
                        foodPrices.add(it)
                    }
                    cartItem?.foodDescription?.let {
                        foodDescriptions.add(it)
                    }
                    cartItem?.foodImages?.let {
                        imageuris.add(it)
                    }
                    cartItem?.foodquantity?.let {
                        quantitys.add(it)
                    }

                }


                setAdapter()           // calling function after all the data is reterived from database


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here if any
                println("Error: ${databaseError.message}")
            }
        })

    }


    private fun getItemQuantitesFromAdapter(): MutableList<Int> {
        return cartAdapter.getUpdatedQuantity()
    }

    private fun getOrderItemsDetails()
    {
        var orderItemref: DatabaseReference = database.reference.child("Users").child(userId).child("CartItems")

        var orderFoodItemName = mutableListOf<String>()
        var orderFoodItemPrice = mutableListOf<String>()
        var orderFoodItemImage = mutableListOf<String>()
        var orderFoodItemDescription = mutableListOf<String>()
        var orderFoodItemQuantity = getItemQuantitesFromAdapter()



        orderItemref.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    // Get the FoodItem object from the child node
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    // Add the foodname to the foodNames list
                    orderItems?.foodNames?.let {
                        orderFoodItemName.add(it)
                    }
                    orderItems?.foodPrice?.let {
                        orderFoodItemPrice.add(it)
                    }
                    orderItems?.foodDescription?.let {
                        orderFoodItemDescription.add(it)
                    }
                    orderItems?.foodImages?.let {
                        orderFoodItemImage.add(it)
                    }


                }
                makeOrderNow(orderFoodItemName,orderFoodItemPrice,orderFoodItemImage,orderFoodItemDescription,orderFoodItemQuantity)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "order making failed please do it again ", Toast.LENGTH_SHORT).show()
            }

        }
        )






    }

    public fun makeOrderNow(
        orderFoodItemName: MutableList<String>,
        orderFoodItemPrice: MutableList<String>,
        orderFoodItemImage: MutableList<String>,
        orderFoodItemDescription: MutableList<String>,
        orderFoodItemQuantity: MutableList<Int>
    ) {

        if(isAdded && context != null) {

            val intent = Intent(requireContext(), PayOutActivity::class.java)


            intent.putExtra("OrdersFoodItemName", orderFoodItemName as ArrayList<String>)
            intent.putExtra("OrdersFoodItemPrice", orderFoodItemPrice as ArrayList<String>)
            intent.putExtra("OrdersFoodItemImage", orderFoodItemImage as ArrayList<String>)
            intent.putExtra(
                "OrdersFoodItemDescription",
                orderFoodItemDescription as ArrayList<String>
            )
            intent.putExtra("OrdersFoodItemQuantity", orderFoodItemQuantity as ArrayList<Int>)

            startActivity(intent)
        }
        else
        {
            Log.d("error", "please try again")
        }

    }


}