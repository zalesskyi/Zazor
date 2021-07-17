package com.example.zazor.ui.settings.list

import android.os.Bundle
import android.view.View
import com.example.zazor.R
import com.example.zazor.data.models.MainSettingType
import com.example.zazor.databinding.FragmentSettingsListBinding
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.settings.list.di.injectViewModel
import com.example.zazor.utils.viewBinding.viewBinding

class SettingsListFragment : BaseFragment<SettingsListContract.State, SettingsListContract.Event>(R.layout.fragment_settings_list) {

    override val viewModel by injectViewModel()

    private val binding by viewBinding(FragmentSettingsListBinding::bind)

    override fun observeState(state: SettingsListContract.State?) {
        when (state) {
            is SettingsListContract.State.Initial -> {
                binding.rvSettings.adapter = SettingsListAdapter(state.settings) { type ->
                    when (type) {
                        MainSettingType.PIN_CODE -> Unit
                        MainSettingType.CLEAR_CODE -> Unit
                        MainSettingType.NOTES -> Unit
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}