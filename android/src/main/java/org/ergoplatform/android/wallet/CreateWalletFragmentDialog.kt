package org.ergoplatform.android.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import org.ergoplatform.android.databinding.FragmentCreateWalletDialogBinding
import org.ergoplatform.android.ui.FullScreenFragmentDialog
import org.ergoplatform.android.ui.navigateSafe
import org.ergoplatform.android.ui.showSensitiveDataCopyDialog
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import org.ergoplatform.android.databinding.MnemonicLayoutBinding


/**
 * Create a new wallet, step 1
 */
class CreateWalletFragmentDialog : FullScreenFragmentDialog() {

    private var _binding: FragmentCreateWalletDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        dialog?.window?.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )

        // Inflate the layout for this fragment
        _binding = FragmentCreateWalletDialogBinding.inflate(inflater, container, false)

        binding.buttonNextStep.setOnClickListener {
            binding.tvMnemonic.text?.toString()?.let {
                NavHostFragment.findNavController(requireParentFragment())
                    .navigateSafe(
                        CreateWalletFragmentDialogDirections.actionCreateWalletDialogToConfirmCreateWalletFragment(
                            it
                        )
                    )
            }
        }

        binding.buttonCopy.setOnClickListener {
            binding.tvMnemonic.text?.toString()
                ?.let { showSensitiveDataCopyDialog(requireContext(), it) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val string: String = ViewModelProvider(this).get(CreateWalletViewModel::class.java).mnemonic
        binding.tvMnemonic.text = string
        val layoutManager = GridLayoutManager(context, 2)

        // Create a custom SpanSizeLookup where the first item spans both columns

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        binding.mnemonicRecyclerView.layoutManager = layoutManager
        binding.mnemonicRecyclerView.adapter = MnemonicAdapter(string)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MnemonicAdapter(string: String) :
    RecyclerView.Adapter<MnemonicViewHolder>() {
    var mnemonicList = string.split("\\s".toRegex())
        set(value) {
            val diffCallback = MnemonicDiffCallback(field, value)
            field = value
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MnemonicViewHolder {
        val mnemonicBinding =
            MnemonicLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val cardViewHolder = MnemonicViewHolder(mnemonicBinding)
        return cardViewHolder
    }

    override fun onBindViewHolder(holder: MnemonicViewHolder, position: Int) {
        holder.bind((position + 1), mnemonicList.get(position))
    }

    override fun getItemCount(): Int {
        return mnemonicList.size
    }

}

class MnemonicViewHolder(val binding: MnemonicLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(number: Int, mnemonic: String) {
        binding.number.text = number.toString()
        binding.tvMnemonic.text = mnemonic
    }

}

class MnemonicDiffCallback(val oldList: List<String>, val newList: List<String>) :
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
