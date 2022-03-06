package org.ergoplatform.android.wallet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ergoplatform.android.databinding.FragmentRestoreWalletBinding
import org.ergoplatform.android.databinding.MnemonicInputLayoutBinding
import org.ergoplatform.android.ui.*
import org.ergoplatform.uilogic.wallet.RestoreWalletUiLogic


/**
 * Restores a formerly generated wallet from mnemonic
 */
class RestoreWalletFragmentDialog : FullScreenFragmentDialog() {

    private var _binding: FragmentRestoreWalletBinding? = null
    private val binding get() = _binding!!
    var mnemonicList = mutableListOf<String>("", "","", "","", "","", "","", "","", "","", "","")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestoreWalletBinding.inflate(inflater, container, false)
        val uiLogic = AndroidRestoreWalletUiLogic(requireContext())

        val layoutManager = GridLayoutManager(context, 2)

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        binding.mnemonicInputRecyclerView.layoutManager = layoutManager
        binding.mnemonicInputRecyclerView.adapter = MnemonicInputAdapter(mnemonicList)

        binding.buttonRestore.setOnClickListener {
            uiLogic.doRestore()
        }


        return binding.root
    }

    inner class AndroidRestoreWalletUiLogic(context: Context) :
        RestoreWalletUiLogic(AndroidStringProvider(context)) {

        public override fun getEnteredMnemonic(): CharSequence = mnemonicList.joinToString(separator=" ")
        override fun setErrorLabel(error: String?) {
            val errorLabel = error ?: ""
            showDialogWithCopyOption(binding.root.context, errorLabel)
        }

        override fun navigateToSaveWalletDialog(mnemonic: String) {
            NavHostFragment.findNavController(requireParentFragment())
                .navigateSafe(
                    RestoreWalletFragmentDialogDirections.actionRestoreWalletFragmentDialogToSaveWalletFragmentDialog(
                        mnemonic
                    )
                )
        }

        override fun hideForcedSoftKeyboard() {
            //TODO hide keyboard efter button restore

        }
    }

    class MnemonicInputAdapter(var mnemonicList: MutableList<String>) :
        RecyclerView.Adapter<MnemonicInputAdapter.MnemonicInputViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MnemonicInputViewHolder {
            val mnemonicInputBinding =
                MnemonicInputLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return MnemonicInputViewHolder(mnemonicInputBinding)
        }

        override fun onBindViewHolder(holder: MnemonicInputViewHolder, position: Int) {
            holder.bind(position)
        }

        override fun getItemCount(): Int {
            return 15
        }

        inner class MnemonicInputViewHolder(val binding: MnemonicInputLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int) {
                val pos = if (position % 2 == 0) position + 1-position/2 else position + 1 + (itemCount - position)/2
                binding.number.text = (pos).toString()
                binding.tvMnemonic.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        var newPhrase: String = binding.tvMnemonic.text.toString().trim()
                        if (newPhrase.isEmpty()) {

                        }
                        mnemonicList[pos-1]= newPhrase
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    }
                })
            }
        }

    }



}




