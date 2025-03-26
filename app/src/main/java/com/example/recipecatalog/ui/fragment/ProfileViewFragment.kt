package com.example.recipecatalog.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipecatalog.R
import com.example.recipecatalog.ui.activity.AuthActivity
import com.example.recipecatalog.ui.viewmodel.UserAuthViewModel
import com.example.recipecatalog.ui.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileViewFragment : Fragment(R.layout.fragment_profile_view) {

    private val userViewModel: UserViewModel by viewModels()
    private val userAuthViewModel: UserAuthViewModel by viewModels()

    private lateinit var userNameTextView: TextView
    private lateinit var birthDateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var genderTextView: TextView
    private lateinit var occupationTextView: TextView
    private lateinit var interestsTextView: TextView
    private lateinit var editProfileButton: Button
    private lateinit var profileImageView: ImageView
    private lateinit var logoutButton: Button
    private lateinit var deleteButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavView.menu.findItem(R.id.profileFragment)?.isChecked = true


        userNameTextView = view.findViewById(R.id.userNameTextView)
        birthDateTextView = view.findViewById(R.id.birthDateTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView)
        addressTextView = view.findViewById(R.id.addressTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        genderTextView = view.findViewById(R.id.genderTextView)
        occupationTextView = view.findViewById(R.id.occupationTextView)
        interestsTextView = view.findViewById(R.id.interestsTextView)
        editProfileButton = view.findViewById(R.id.editProfileButton)
        profileImageView = view.findViewById(R.id.profileImageView)
        logoutButton = view.findViewById(R.id.logoutButton)
        deleteButton = view.findViewById(R.id.deleteButton)


        userViewModel.isProfileComplete.observe(viewLifecycleOwner) { isComplete ->
            if (!isComplete) {
                findNavController().navigate(R.id.action_profileViewFragment_to_profileEditFragment)
            }
        }

        userViewModel.userState.observe(viewLifecycleOwner) { userState ->
            userState.userProfile?.let { userProfile ->
                userNameTextView.text = userProfile.fullName
                birthDateTextView.text = userProfile.birthDate
                descriptionTextView.text = userProfile.description
                phoneNumberTextView.text = userProfile.phoneNumber
                addressTextView.text = userProfile.address
                emailTextView.text = userProfile.email
                genderTextView.text = userProfile.gender
                occupationTextView.text = userProfile.occupation
                interestsTextView.text = userProfile.interests

                Glide.with(this)
                    .load(userProfile.profilePictureUrl)
                    .into(profileImageView)
            }
        }

        editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileViewFragment_to_profileEditFragment)
        }

        logoutButton.setOnClickListener {
            userAuthViewModel.logout()
            activity?.finish()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            userAuthViewModel.delete()
        }

        userAuthViewModel.deleteResult.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "An error occurred while deleting", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
