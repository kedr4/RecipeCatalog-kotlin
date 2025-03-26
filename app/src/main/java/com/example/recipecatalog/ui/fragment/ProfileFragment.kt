package com.example.recipecatalog.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipecatalog.R
import com.example.recipecatalog.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.isProfileComplete.observe(viewLifecycleOwner) { isComplete ->
            if (!isComplete) {
                findNavController().navigate(R.id.action_profileFragment_to_profileEditFragment)
            } else {
                findNavController().navigate(R.id.action_profileFragment_to_profileViewFragment)
            }
        }
    }
}
