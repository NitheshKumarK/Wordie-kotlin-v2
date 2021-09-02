package com.nithesh.wordie.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nithesh.wordie.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var viewModel: Lazy<DetailViewModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        val word = DetailFragmentArgs.fromBundle(requireArguments()).detailWord
        val currentUser = Firebase.auth.currentUser
        val factory = DetailViewModelFactory(word!!, currentUser)
        viewModel = viewModels { factory }
        binding.viewModel = viewModel.value
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.value.showToast.observe(viewLifecycleOwner) { show ->
            if (show) {
                Toast.makeText(this.context, "User account is null", Toast.LENGTH_SHORT).show()
                viewModel.value.toastEventFinished()
            }
        }
        return binding.root
    }

}