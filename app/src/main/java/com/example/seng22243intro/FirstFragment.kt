package com.example.seng22243intro

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.seng22243intro.api.UserAPIService
import com.example.seng22243intro.databinding.FragmentFirstBinding
import com.example.seng22243intro.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService =UserAPIService.create()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            // val user = userAPIService.getUser("1");

            val editText = binding.editText.editableText
            val user = userAPIService.getUser(editText.toString());
            Log.i("FirstFragment", "buttonFirst")
            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it?.name)
                        binding.textviewUserID.text="User details"
                        binding.textviewUserName.text="Name: "+it.username
                        binding.textviewUserEmail.text="Email: "+it.email
                        binding.textviewUserWebsite.text="Website: "+it.website

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    TODO("Not yet implemented")
                }


            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

