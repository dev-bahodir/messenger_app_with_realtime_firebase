package dev.bahodir.realtimefirebaseentrance.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import dev.bahodir.realtimefirebaseentrance.R
import dev.bahodir.realtimefirebaseentrance.adapters.MessageAdapter
import dev.bahodir.realtimefirebaseentrance.databinding.FragmentMessageBinding
import dev.bahodir.realtimefirebaseentrance.models.Account
import dev.bahodir.realtimefirebaseentrance.models.Message
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "account"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var account: Account? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            account = it.getSerializable(ARG_PARAM) as Account?
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var list: ArrayList<Message>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMessageBinding.inflate(inflater, container, false)

        Picasso.get().load(account?.photoUrl).into(binding.userImage)
        binding.nameUser.text = account?.displayName
        
        binding.left.setOnClickListener { 
            findNavController().popBackStack()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        binding.send.setOnClickListener {
            val messageEdit = binding.edit.text.toString()

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val message = Message(firebaseAuth.uid, account?.uid, messageEdit, "$hour:$minute")
            val key = databaseReference.push().key

            databaseReference
                .child(firebaseAuth.uid ?: "")
                .child("messages")
                .child(account?.uid ?: "")
                .child(key ?: "")
                .setValue(message)

            databaseReference
                .child(account?.uid ?: "")
                .child("messages")
                .child(firebaseAuth.uid ?: "")
                .child(key ?: "")
                .setValue(message)

            binding.edit.setText("")
        }

        list = ArrayList()
        messageAdapter = MessageAdapter(firebaseAuth.uid ?: "", list)
        binding.rvMessage.adapter = messageAdapter

        databaseReference
            .child(firebaseAuth.uid ?: "")
            .child("messages")
            .child(account?.uid ?: "")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (child in snapshot.children) {
                        val value = child.getValue(Message::class.java)
                        if (value != null) {
                            list.add(value)
                        }
                    }

                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param account Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(account: Account) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM, account)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }
}