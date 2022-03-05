package org.ergoplatform.android.wallet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ergoplatform.android.R
import org.ergoplatform.android.databinding.FragmentRestoreWalletBinding
import org.ergoplatform.android.databinding.MnemonicLayoutBinding
import org.ergoplatform.android.ui.*
import org.ergoplatform.uilogic.wallet.RestoreWalletUiLogic

/**
 * Restores a formerly generated wallet from mnemonic
 */
class RestoreWalletFragmentDialog : FullScreenFragmentDialog() {

    private var _binding: FragmentRestoreWalletBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: HashMap<Int, String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestoreWalletBinding.inflate(inflater, container, false)

//        val uiLogic = AndroidRestoreWalletUiLogic(requireContext())

//        binding.tvMnemonic.editText?.setOnEditorActionListener { _, _, _ ->
//            uiLogic.doRestore()
//            true
//        }
//        binding.tvMnemonic.editText?.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                uiLogic.userChangedMnemonic()
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//        })

        val layoutManager = GridLayoutManager(context, 2)

        // Create a custom SpanSizeLookup where the first item spans both columns

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.mnemonicInputRecyclerView.layoutManager = layoutManager
        binding.mnemonicInputRecyclerView.adapter = MnemonicInputAdapter()

//        binding.buttonRestore.setOnClickListener { uiLogic.doRestore() }
//
//        binding.labelWordListHint.enableLinks()

        return binding.root
    }
//
//    override fun onResume() {
//        super.onResume()
////        binding.tvMnemonic.editText?.requestFocus()
//        forceShowSoftKeyboard(requireContext())
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    inner class AndroidRestoreWalletUiLogic(context: Context) :
//        RestoreWalletUiLogic(AndroidStringProvider(context)) {
//
//        override fun getEnteredMnemonic(): CharSequence? = binding.tvMnemonic.editText?.text
//        override fun setErrorLabel(error: String?) {
////            binding.tvMnemonic.error = error
//        }
//
//        override fun navigateToSaveWalletDialog(mnemonic: String) {
//            NavHostFragment.findNavController(requireParentFragment())
//                .navigateSafe(
//                    RestoreWalletFragmentDialogDirections.actionRestoreWalletFragmentDialogToSaveWalletFragmentDialog(
//                        mnemonic
//                    )
//                )
//        }
//
//        override fun hideForcedSoftKeyboard() {
//            hideForcedSoftKeyboard(requireContext(), binding.tvMnemonic.editText!!)
//        }
    }




class MnemonicInputAdapter() :
    RecyclerView.Adapter<MnemonicInputViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MnemonicInputViewHolder {
        val mnemonicBinding =
            MnemonicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val cardViewHolder = MnemonicInputViewHolder(mnemonicBinding)
        return cardViewHolder
    }

    override fun onBindViewHolder(holder: MnemonicInputViewHolder, position: Int) {
        holder.bind(position + 1)
    }

    override fun getItemCount(): Int {
        return 15
    }

}

class MnemonicInputViewHolder(val binding: MnemonicLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(number: Int) {
        binding.number.text = number.toString()
    }

}

class MnemonicInputDiffCallback(val oldList: List<String>, val newList: List<String>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // always redraw
        return false
    }

}