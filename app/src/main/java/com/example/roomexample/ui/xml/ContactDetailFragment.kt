package com.example.roomexample.ui.xml

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.roomexample.R
import com.example.roomexample.data.AppDatabase
import com.example.roomexample.data.ContactRepository
import com.example.roomexample.databinding.FragmentContactDetailBinding
import com.example.roomexample.ui.ContactDetailViewModel
import com.example.roomexample.ui.ContactDetailViewModelFactory
import kotlinx.coroutines.launch

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private val contactId: Int by lazy { arguments?.getInt("contactId") ?: -1 }
    private val repository by lazy {
        ContactRepository(AppDatabase.getDatabase(requireContext()).contactDao())
    }
    private val viewModel: ContactDetailViewModel by viewModels {
        ContactDetailViewModelFactory(repository, contactId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactDetailBinding.bind(view)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.inflateMenu(R.menu.menu_contact_detail)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> {
                    val contact = viewModel.contact.value
                    if (contact != null) {
                        findNavController().navigate(
                            R.id.action_contactDetail_to_addContactSheet,
                            bundleOf(
                                "contactId" to contact.id,
                                "initialName" to contact.name,
                                "initialPhone" to contact.phone
                            )
                        )
                    }
                    true
                }

                R.id.action_delete -> {
                    viewModel.deleteContact { findNavController().popBackStack() }
                    true
                }

                else -> false
            }
        }

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contact.collect { contact ->
                    binding.cardContact.isVisible = contact != null
                    binding.textEmpty.isVisible = contact == null
                    binding.textContactName.text = contact?.name.orEmpty()
                    binding.textContactPhone.text = contact?.phone.orEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
