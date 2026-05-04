package psti.nufa.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import psti.nufa.studentcontactapp.utils.PrefManager

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var prefManager: PrefManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val username = prefManager.getUsername()

        tvWelcome.text = "Welcome, $username!"
    }
}