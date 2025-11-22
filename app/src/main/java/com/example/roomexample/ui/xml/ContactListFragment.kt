package com.example.roomexample.ui.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.roomexample.R
import com.example.roomexample.data.AppDatabase
import com.example.roomexample.data.ContactRepository
import com.example.roomexample.databinding.FragmentContactListBinding
import com.example.roomexample.databinding.ItemContactBinding
import com.example.roomexample.domain.Contact
import com.example.roomexample.ui.ContactViewModel
import com.example.roomexample.ui.ContactViewModelFactory
import kotlinx.coroutines.launch

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy {
        ContactRepository(AppDatabase.getDatabase(requireContext()).contactDao())
    }
    private val viewModel: ContactViewModel by activityViewModels {
        ContactViewModelFactory(repository)
    }
    private val contactAdapter by lazy {
        ContactAdapter { contact ->
            findNavController().navigate(
                R.id.action_contactList_to_contactDetail,
                bundleOf("contactId" to contact.id)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactListBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.contactList.layoutManager = LinearLayoutManager(requireContext())
        binding.contactList.adapter = contactAdapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.root.updatePadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        binding.fabAddContact.setOnClickListener {
            findNavController().navigate(R.id.action_contactList_to_addContactSheet)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect { contacts ->
                    binding.contactList.isVisible = contacts.isNotEmpty()
                    contactAdapter.submitList(contacts)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class ContactAdapter(
        val onItemClick: (Contact) -> Unit
    ) : ListAdapter<Contact, ContactViewHolder>(DIFF_CALLBACK) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val binding = ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ContactViewHolder(binding, onItemClick)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
                override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                    oldItem == newItem
            }
        }
    }

    private class ContactViewHolder(
        private val binding: ItemContactBinding,
        private val onItemClick: (Contact) -> Unit
    ) : ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.textContactName.text = contact.name
            binding.textContactPhone.text = contact.phone
            binding.root.setOnClickListener { onItemClick(contact) }
        }
    }
}