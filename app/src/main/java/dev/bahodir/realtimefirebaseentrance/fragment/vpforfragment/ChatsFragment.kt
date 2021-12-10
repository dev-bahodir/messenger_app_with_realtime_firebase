package dev.bahodir.realtimefirebaseentrance.fragment.vpforfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dev.bahodir.realtimefirebaseentrance.R
import dev.bahodir.realtimefirebaseentrance.adapters.AccountAdapter
import dev.bahodir.realtimefirebaseentrance.databinding.FragmentChatsBinding
import dev.bahodir.realtimefirebaseentrance.models.Account
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentChatsBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var list: ArrayList<Account>
    private lateinit var accountAdapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChatsBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users")
        list = ArrayList()

        accountAdapter = AccountAdapter(object : AccountAdapter.OnItemClickListener {
            override fun onItemClick(account: Account, position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("account", account)
                findNavController().navigate(R.id.action_accountFragment_to_messageFragment, bundle)
            }

        })
        binding.rv.adapter = accountAdapter

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                val children = snapshot.children

                for (child in children) {
                    val value = child.getValue(Account::class.java)
                    if (value != null && value.uid != firebaseAuth.uid) {
                        list.add(value)

                    }
                }
                accountAdapter.submitList(list)
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}