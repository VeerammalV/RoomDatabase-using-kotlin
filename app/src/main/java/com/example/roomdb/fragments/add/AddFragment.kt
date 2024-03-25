package com.example.roomdb.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdb.R
import com.example.roomdb.viewmodel.UserViewModel
import com.example.roomdb.databinding.FragmentAddBinding
import com.example.roomdb.model.User
import java.lang.Integer.parseInt

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentAddBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.button.setOnClickListener {
            insertDataToDatabase()
        }
        return binding.root

    }

    private fun insertDataToDatabase() {
        val firstName = binding.firstname.text.toString()
        val lastName = binding.lastname.text.toString()
        val age = binding.age.text.toString()

        if(inputCheck(firstName, lastName, age)){

            // Create User Object
            val user = User(0, firstName,lastName, parseInt(age))

            //Add data to database
            mUserViewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()

            //Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && age.toInt() > 0)
    }
}