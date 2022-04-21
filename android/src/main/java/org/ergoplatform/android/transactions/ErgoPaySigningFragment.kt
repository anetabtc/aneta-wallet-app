package org.ergoplatform.android.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.ergoplatform.transactions.MessageSeverity
import org.ergoplatform.android.AppDatabase
import org.ergoplatform.android.Preferences
import org.ergoplatform.android.R
import org.ergoplatform.android.RoomWalletDbProvider
import org.ergoplatform.android.databinding.FragmentErgoPaySigningBinding
import org.ergoplatform.android.ui.AndroidStringProvider
import org.ergoplatform.android.ui.getSeverityDrawableResId
import org.ergoplatform.android.ui.navigateSafe
import org.ergoplatform.transactions.reduceBoxes
import org.ergoplatform.uilogic.transactions.ErgoPaySigningUiLogic
import org.ergoplatform.wallet.addresses.getAddressLabel
import org.ergoplatform.wallet.getNumOfAddresses

class ErgoPaySigningFragment : SubmitTransactionFragment() {
    private var _binding: FragmentErgoPaySigningBinding? = null
    private val binding get() = _binding!!

    private val args: ErgoPaySigningFragmentArgs by navArgs()

    override val viewModel: ErgoPaySigningViewModel
        get() = ViewModelProvider(this).get(ErgoPaySigningViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErgoPaySigningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = this.viewModel
        val context = requireContext()
        viewModel.uiLogic.init(
            args.request,
            args.walletId,
            args.derivationIdx,
            RoomWalletDbProvider(AppDatabase.getInstance(context)),
            Preferences(context),
            AndroidStringProvider(context)
        )

        viewModel.uiStateRefresh.observe(viewLifecycleOwner) { state ->
            binding.layoutTransactionInfo.visibility =
                visibleWhen(state, ErgoPaySigningUiLogic.State.WAIT_FOR_CONFIRMATION)
            binding.layoutProgress.visibility =
                visibleWhen(state, ErgoPaySigningUiLogic.State.FETCH_DATA)
            binding.layoutDoneInfo.visibility =
                visibleWhen(state, ErgoPaySigningUiLogic.State.DONE)
            binding.layoutChooseAddress.visibility =
                visibleWhen(state, ErgoPaySigningUiLogic.State.WAIT_FOR_ADDRESS)

            when (state) {
                ErgoPaySigningUiLogic.State.WAIT_FOR_ADDRESS -> {
                    // nothing to do
                }
                ErgoPaySigningUiLogic.State.FETCH_DATA -> showFetchData()
                ErgoPaySigningUiLogic.State.WAIT_FOR_CONFIRMATION -> showTransactionInfo()
                ErgoPaySigningUiLogic.State.DONE -> showDoneInfo()
                null -> {} // impossible
            }
        }

        viewModel.addressChosen.observe(viewLifecycleOwner) {
            val walletLabel = viewModel.uiLogic.wallet?.walletConfig?.displayName ?: ""
            val addressLabel =
                it?.getAddressLabel(AndroidStringProvider(requireContext()))
                    ?: getString(
                        R.string.label_all_addresses,
                        viewModel.uiLogic.wallet?.getNumOfAddresses()
                    )
            binding.addressLabel.text =
                getString(R.string.label_sign_with, addressLabel, walletLabel)
        }

        // Click listeners
        binding.transactionInfo.buttonSignTx.setOnClickListener {
            startAuthFlow()
        }
        binding.buttonDismiss.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonChooseAddress.setOnClickListener {
            showChooseAddressList(false)
        }
    }

    override fun onAddressChosen(addressDerivationIdx: Int?) {
        super.onAddressChosen(addressDerivationIdx)
        // redo the request - can't be done within uilogic because the context is needed
        val uiLogic = viewModel.uiLogic
        uiLogic.lastRequest?.let {
            val context = requireContext()
            uiLogic.hasNewRequest(
                it,
                Preferences(context),
                AndroidStringProvider(context)
            )
        }
    }

    private fun visibleWhen(
        state: ErgoPaySigningUiLogic.State?,
        visibleWhen: ErgoPaySigningUiLogic.State
    ) =
        if (state == visibleWhen) View.VISIBLE else View.GONE

    private fun showFetchData() {
        // nothing special to do yet
    }

    private fun showDoneInfo() {
        // use normal tx done message in case of success
        val uiLogic = viewModel.uiLogic
        binding.tvMessage.text = uiLogic.getDoneMessage(AndroidStringProvider(requireContext()))
        binding.tvMessage.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,
            uiLogic.getDoneSeverity().getSeverityDrawableResId(),
            0, 0
        )
    }

    private fun showTransactionInfo() {
        val uiLogic = viewModel.uiLogic
        binding.transactionInfo.bindTransactionInfo(
            uiLogic.transactionInfo!!.reduceBoxes(),
            { tokenId ->
                findNavController().navigateSafe(
                    ErgoPaySigningFragmentDirections.actionErgoPaySigningToTokenInformation(tokenId)
                )
            },
            layoutInflater
        )
        binding.layoutTiMessage.visibility = uiLogic.epsr?.message?.let {
            binding.tvTiMessage.text = getString(R.string.label_message_from_dapp, it)
            val severityResId =
                (uiLogic.epsr?.messageSeverity ?: MessageSeverity.NONE).getSeverityDrawableResId()
            binding.imageTiMessage.setImageResource(severityResId)
            binding.imageTiMessage.visibility = if (severityResId == 0) View.GONE else View.VISIBLE
            View.VISIBLE
        } ?: View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}