package com.example.roomexample.ui.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.roomexample.R
import com.example.roomexample.databinding.BottomSheetAddContactBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.roomexample.data.AppDatabase
import com.example.roomexample.data.ContactRepository
import com.example.roomexample.domain.Contact
import com.example.roomexample.ui.ContactDetailViewModel
import com.example.roomexample.ui.ContactDetailViewModelFactory
import com.example.roomexample.ui.ContactViewModel
import com.example.roomexample.ui.ContactViewModelFactory

class AddContactBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddContactBinding? = null
    private val binding get() = _binding!!
    private val contactId: Int by lazy { arguments?.getInt("contactId") ?: -1 }
    private val repository by lazy {
        ContactRepository(AppDatabase.getDatabase(requireContext()).contactDao())
    }
    private val contactViewModel: ContactViewModel by activityViewModels {
        ContactViewModelFactory(repository)
    }
    private val contactDetailViewModel: ContactDetailViewModel by viewModels {
        ContactDetailViewModelFactory(repository, contactId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textTitle.text = getString(R.string.title_add_contact)
        binding.editTextName.setText(arguments?.getString("initialName").orEmpty())
        binding.editTextPhone.setText(arguments?.getString("initialPhone").orEmpty())

        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonConfirm.setOnClickListener {
            val name = binding.editTextName.text?.toString().orEmpty()
            val phone = binding.editTextPhone.text?.toString().orEmpty()

            if (contactId >= 0) {
                contactDetailViewModel.updateContact(name, phone)
            } else {
                contactViewModel.addContact(Contact(id = 0, name = name, phone = phone))
            }

            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
