package edu.arimanius.letsqueeze.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import edu.arimanius.letsqueeze.MainActivity
import edu.arimanius.letsqueeze.R
import edu.arimanius.letsqueeze.data.LetsQueezeDatabase
import edu.arimanius.letsqueeze.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.play.setOnClickListener{
            val loggedInUser = LetsQueezeDatabase.getInstance(requireContext()).userDao().getLoggedInUser()
            loggedInUser.observe(viewLifecycleOwner){
                if (it == null){
                    val appContext = context?.applicationContext?: return@observe
                    Toast.makeText(appContext,R.string.login_prompt, Toast.LENGTH_LONG).show()
                }else{
                    findNavController().navigate(R.id.action_homeFragment_to_queezeFragment)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).isHome = true
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).isHome = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
