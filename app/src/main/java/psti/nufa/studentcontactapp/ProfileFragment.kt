package psti.nufa.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
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

        val switchDark = view.findViewById<SwitchCompat>(R.id.switchDarkMode)
        val switchNotif = view.findViewById<SwitchCompat>(R.id.switchNotification)
        val switchFont = view.findViewById<SwitchCompat>(R.id.switchFontSize)
        val btnViewNotes = view.findViewById<Button>(R.id.btnViewNotes)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        switchDark.isChecked = settingsManager.isDarkMode
        switchNotif.isChecked = settingsManager.isNotificationEnabled

        switchDark.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isDarkMode = isChecked
            Toast.makeText(requireContext(), "Dark Mode: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
            // Opsional: Panggil fungsi untuk ganti tema secara realtime di sini
        }

        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.isNotificationEnabled = isChecked
            Toast.makeText(requireContext(), "Notifikasi: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        switchFont?.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Font Size Adjusted: $isChecked", Toast.LENGTH_SHORT).show()
        }

        btnViewNotes.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, NoteListFragment()) // Pastikan ID frameContainer sesuai MainActivity
                .addToBackStack(null)
                .commit()
        }

        btnLogout.setOnClickListener {
            prefManager.logout()
            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}