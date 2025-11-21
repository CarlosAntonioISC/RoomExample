package com.example.roomexample.xml

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomexample.R

class XmlScreenBFragment : Fragment(R.layout.fragment_xml_screen_b) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_back_to_a)?.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
