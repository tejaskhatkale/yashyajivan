package com.beingdeveloper.updateprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beingdeveloper.jivanseva.activities.EditProfile
import com.beingdeveloper.jivanseva.databinding.FragmentHomeBinding
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.BankDetailsFragment
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.KYCUpdateFragment
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.ManageContactFragment
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.PasswordFragment
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.PersonalDetailsFragment
import com.beingdeveloper.jivanseva.fragmentsForEditProfile.NomineeDetailsFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.personalDetailsButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(PersonalDetailsFragment(), true)
        }
        binding.kycUpdateButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(KYCUpdateFragment(), true)
        }
        binding.bankDetailsButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(BankDetailsFragment(), true)
        }
        binding.nomineeDetailsButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(NomineeDetailsFragment(), true)
        }
        binding.manageContactButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(ManageContactFragment(), true)
        }
        binding.passwordButton.setOnClickListener {
            (activity as EditProfile).navigateToFragment(PasswordFragment(), true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
