package com.example.recipecatalog.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipecatalog.R
import com.example.recipecatalog.model.UserProfile
import com.example.recipecatalog.ui.viewmodel.UserViewModel
import com.example.recipecatalog.util.Validator
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
@AndroidEntryPoint
class ProfileEditFragment : Fragment(R.layout.fragment_profile_edit) {

    private val userViewModel: UserViewModel by viewModels()

    private lateinit var fullNameEditText: EditText
    private lateinit var birthDateTextView: TextView
    private lateinit var descriptionEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var profileImageUrlEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var occupationEditText: EditText
    private lateinit var interestsEditText: EditText
    private lateinit var saveButton: Button

    private var selectedBirthDate: String? = null
    private var selectedGender: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
        bottomNavView.menu.findItem(R.id.profileFragment)?.isChecked = true

        val genderSpinner: Spinner = view.findViewById(R.id.genderSpinner)
        val genderOptions = listOf("Male", "Female", "Undefined")

        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        genderSpinner.adapter = adapter

        fullNameEditText = view.findViewById(R.id.fullNameEditText)
        descriptionEditText = view.findViewById(R.id.descriptionEditText)
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText)
        addressEditText = view.findViewById(R.id.addressEditText)
        profileImageUrlEditText = view.findViewById(R.id.imageUrlEditText)
        occupationEditText = view.findViewById(R.id.occupationEditText)
        interestsEditText = view.findViewById(R.id.interestsEditText)
        saveButton = view.findViewById(R.id.saveButton)
        birthDateTextView = view.findViewById(R.id.birthDateTextView)


        birthDateTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedBirthDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    birthDateTextView.text = selectedBirthDate
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        userViewModel.userState.observe(viewLifecycleOwner) { userState ->
            userState.userProfile?.let { userProfile ->
                fullNameEditText.setText(userProfile.fullName)

                birthDateTextView.text = userProfile.birthDate
                descriptionEditText.setText(userProfile.description)
                phoneNumberEditText.setText(userProfile.phoneNumber)
                addressEditText.setText(userProfile.address)
                profileImageUrlEditText.setText(userProfile.profilePictureUrl)


                val genderPosition = genderOptions.indexOf(userProfile.gender)
                if (genderPosition != -1) {
                    genderSpinner.setSelection(genderPosition)
                }

                occupationEditText.setText(userProfile.occupation)
                interestsEditText.setText(userProfile.interests)
            }
        }

        saveButton.setOnClickListener {
            selectedGender = genderSpinner.selectedItem?.toString()


            val userProfile = UserProfile(
                fullName = fullNameEditText.text.toString(),
                birthDate = selectedBirthDate ?: birthDateTextView.text.toString(),
                description = descriptionEditText.text.toString(),
                phoneNumber = phoneNumberEditText.text.toString(),
                address = addressEditText.text.toString(),
                profilePictureUrl = profileImageUrlEditText.text.toString(),
                gender = selectedGender,
                occupation = occupationEditText.text.toString(),
                interests = interestsEditText.text.toString()
            )

            val validationError = Validator.validateProfile(userProfile)
            if (validationError != null) {
                Toast.makeText(context, validationError, Toast.LENGTH_SHORT).show()
            } else {

                if (userProfile.profilePictureUrl.isNullOrBlank()){
                    userProfile.profilePictureUrl = "https://img.freepik.com/premium-vector/chef-pan-with-spoon-pan-pasta_1087929-12778.jpg"; // sets a default profile picture
                }

                userViewModel.saveUserProfile(userProfile)
                findNavController().navigate(R.id.action_profileEditFragment_to_profileViewFragment)
            }
        }
    }
}