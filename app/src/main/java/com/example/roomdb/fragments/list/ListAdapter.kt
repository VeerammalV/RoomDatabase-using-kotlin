package com.example.roomdb.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.databinding.CustomRowBinding
import com.example.roomdb.model.User
import java.util.Collections


class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var userList = emptyList<User>()

    inner class MyViewHolder(private val binding: CustomRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = userList[position]
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                    it.findNavController().navigate(action)
                }
            }
        }

        fun bind(user: User) {
            binding.idTxt.text = user.id.toString()
            binding.firstNameTxt.text = user.firstName
            binding.lastNameTxt.text = user.lastName
            binding.ageTxt.text = user.age.toString()

            binding.root.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(user)
                it.findNavController().navigate(action)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<User>) {
        this.userList = user
        notifyDataSetChanged()
    }
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val itemToMove = userList[fromPosition]
        userList = userList.toMutableList().apply {
            removeAt(fromPosition)
            add(toPosition, itemToMove)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun removeItem(position: Int) {
        val mutableList = userList.toMutableList()
        mutableList.removeAt(position)
        userList = mutableList.toList()
        notifyItemRemoved(position)
    }
}


