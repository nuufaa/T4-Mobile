package psti.nufa.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import psti.nufa.studentcontactapp.utils.PrefManager
import psti.nufa.studentcontactapp.utils.SettingsManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var prefManager: PrefManager
    private lateinit var settingsManager: SettingsManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        settingsManager = SettingsManager(requireContext())

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val switchDark = view.findViewById<Switch>(R.id.switchDarkMode)
        val switchNotif = view.findViewById<Switch>(R.id.switchNotification)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // TAMPILKAN USERNAME
        val username = prefManager.getUsername()
        tvUsername.text = "Username: $username"

        // SET STATUS AWAL DARK MODE
        switchDark.isChecked = settingsManager.isDarkMode

        // SET STATUS AWAL NOTIFICATION
        switchNotif.isChecked = settingsManager.isNotificationEnabled

        // LISTENER DARK MODE
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isDarkMode = isChecked
            Toast.makeText(requireContext(), "Dark Mode: $isChecked", Toast.LENGTH_SHORT).show()
        }

        // LISTENER NOTIFICATION
        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isNotificationEnabled = isChecked
            Toast.makeText(requireContext(), "Notification: $isChecked", Toast.LENGTH_SHORT).show()
        }

        // 🚪 LOGOUT
        btnLogout.setOnClickListener {
            prefManager.logout()

            // kembali ke login
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}