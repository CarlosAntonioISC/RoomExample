package com.example.roomexample.ui.xml

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
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

        val initialToolbarPaddingTop = binding?.toolbar?.paddingTop ?: 0
        val initialListPadding = binding?.contactList?.let { recyclerView ->
            PaddingValues(
                left = recyclerView.paddingLeft,
                top = recyclerView.paddingTop,
                right = recyclerView.paddingRight,
                bottom = recyclerView.paddingBottom,
            )
        }
        val initialFabBottomMargin = binding?.fabAddContact?.let { fab ->
            (fab.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
        } ?: 0

        binding?.root?.let { root ->
            ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

                binding?.toolbar?.updatePadding(top = initialToolbarPaddingTop + systemBars.top)

                binding?.contactList?.let { recyclerView ->
                    initialListPadding?.let { padding ->
                        recyclerView.updatePadding(
                            left = padding.left + systemBars.left,
                            top = padding.top,
                            right = padding.right + systemBars.right,
                            bottom = padding.bottom + systemBars.bottom,
                        )
                    }
                }

                binding?.fabAddContact?.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = initialFabBottomMargin + systemBars.bottom
                }

                insets
            }
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
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

private data class PaddingValues(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int,
)
