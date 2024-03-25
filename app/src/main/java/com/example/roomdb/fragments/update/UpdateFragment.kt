package com.example.roomdb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdb.R
import androidx.navigation.fragment.navArgs
import com.example.roomdb.databinding.FragmentUpdateBinding
import com.example.roomdb.model.User
import com.example.roomdb.viewmodel.UserViewModel
import java.lang.Integer.parseInt


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mUserViewModel: UserViewModel
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        val view = binding.root

        binding.updateFirstname.setText(args.person.firstName)
        binding.updateLastname.setText(args.person.lastName)
        binding.updateAge.setText(args.person.age.toString())

        binding.updateButton.setOnClickListener {
            updateItem()
        }

        //Add menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName = binding.updateFirstname.text.toString()
        val lastName = binding.updateLastname.text.toString()
        val age = binding.updateAge.text.toString()

        if(inputCheck(firstName, lastName, age)) {

            //Create User Object
            val updatedUser = User(args.person.id, firstName, lastName, parseInt(age))

            //Update Current User
            mUserViewModel.updateUser(updatedUser)

            //Navigate back
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
}

    private fun inputCheck(firstName: String, lastName: String, age: String): Boolean {
        return (firstName.isNotEmpty() && lastName.isNotEmpty() && age.isNotEmpty() && age.toInt() > 0)    }


    //Delete User


    //Delete one User
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onContextItemSelected(item)

    }


    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.person)
            Toast.makeText(requireContext(), "Successfully deleted: ${args.person.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.person.firstName}?")
        builder.setMessage("Are you sure want to delete ${args.person.firstName}")
        builder.create().show()

        }
    }

