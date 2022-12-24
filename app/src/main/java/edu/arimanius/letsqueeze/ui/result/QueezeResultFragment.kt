package edu.arimanius.letsqueeze.ui.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.databinding.FragmentQueezeBinding
import edu.arimanius.letsqueeze.databinding.FragmentQueezeResultBinding

class QueezeResultFragment : Fragment() {

    private var _binding: FragmentQueezeResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueezeResultBinding.inflate(inflater, container, false)
        return binding.root
    }


}