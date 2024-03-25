package com.example.roomdb.fragments.list

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
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.R
import com.example.roomdb.viewmodel.UserViewModel
import com.example.roomdb.databinding.FragmentListBinding
import com.example.roomdb.model.User
import com.google.android.material.snackbar.Snackbar
import java.util.Collections


class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var adapter: ListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentListBinding.inflate(inflater, container, false)
        adapter = ListAdapter()


        //Recycler view
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer<List<User>> { user ->
            adapter.setData(user)
        })

        // Floating add button
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //Add menu
        setHasOptionsMenu(true)

        //Drag and drop recycler view
        val itemTouchHelper = ItemTouchHelper(simpleCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return binding.root

    }

    private fun simpleCallback() = object: ItemTouchHelper.SimpleCallback (ItemTouchHelper.UP or ItemTouchHelper.DOWN
    or ItemTouchHelper.START or ItemTouchHelper.END, 0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {

            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(adapter.userList ,fromPosition,toPosition)
            adapter.moveItem(fromPosition, toPosition)

            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.adapterPosition
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    adapter.removeItem(position)
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                }
                ItemTouchHelper.RIGHT -> {
                    adapter.removeItem(position)
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()                }
                }

//            val position = viewHolder.adapterPosition
//            adapter.userList.removeItem(adapter.userList[position])
        }


    }














    //Delete

    //Delete All users

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if(item.itemId == R.id.menu_delete) {
           deleteAllUser()
       }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(), "Successfully deleted everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure want to delete everything?")
        builder.create().show()    }
}














//        override fun onSelectedChanged(viewHolder:RecyclerView.ViewHolder?,actionState: Int) {
//            super.onSelectedChanged(viewHolder, actionState)
//
////            val position = viewHolder?.adapterPosition
////                adapter.removeItem(position)
//
//            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//                activity?.findViewById<View>(R.id.drag_delete)?.visibility = View.VISIBLE
//            }
//        }


//        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
//            super.clearView(recyclerView, viewHolder)
//            activity?.findViewById<View>(R.id.drag_delete)?.visibility = View.INVISIBLE
//        }