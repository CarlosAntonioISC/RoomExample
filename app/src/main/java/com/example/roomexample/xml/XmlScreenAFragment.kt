package com.example.roomexample.xml

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomexample.R

class XmlScreenAFragment : Fragment(R.layout.fragment_xml_screen_a) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_go_to_b)?.setOnClickListener {
            findNavController().navigate(R.id.action_xmlScreenA_to_xmlScreenB)
        }
    }
}
