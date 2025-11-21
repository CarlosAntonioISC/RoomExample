package com.example.roomexample.xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.R
import com.example.roomexample.databinding.FragmentContactListBinding

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactListBinding.bind(view)

        binding?.toolbar?.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding?.fabAddContact?.setOnClickListener {
            findNavController().navigate(R.id.action_contactList_to_contactDetail)
        }

        // Placeholder for a list similar to the Compose LazyRow
        binding?.contactList?.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = EmptyAdapter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class EmptyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = View(parent.context)
            view.layoutParams = RecyclerView.LayoutParams(0, 0)
            return object : RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            // No-op
        }

        override fun getItemCount(): Int = 0
    }
}
