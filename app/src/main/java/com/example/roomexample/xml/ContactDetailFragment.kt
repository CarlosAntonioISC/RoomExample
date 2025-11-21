package com.example.roomexample.xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomexample.R
import com.example.roomexample.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentContactDetailBinding.bind(view)

        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
