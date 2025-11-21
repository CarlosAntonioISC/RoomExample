package com.example.roomexample.ui.xml

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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

        val initialToolbarPaddingTop = binding?.toolbar?.paddingTop ?: 0

        binding?.root?.let { root ->
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                binding?.toolbar?.updatePadding(top = initialToolbarPaddingTop + systemBars.top)

                insets
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
