package com.example.zazor.ui.photo.editPhoto.delegates

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zazor.R
import com.example.zazor.databinding.BottomSheetAddNoteBinding
import com.example.zazor.databinding.IncludeNotesContentBinding
import com.example.zazor.databinding.IncludePaintContentBinding
import com.example.zazor.ui.photo.editPhoto.EditPhotoContract
import com.example.zazor.ui.photo.editPhoto.EditPhotoViewModel
import com.example.zazor.utils.colorPicker.CircleProperty
import com.example.zazor.utils.colorPicker.ColorPickerAdapter
import com.example.zazor.utils.colorPicker.OnSelectedColorListener
import com.example.zazor.utils.extensions.hide
import com.example.zazor.utils.extensions.show
import java.lang.ref.WeakReference

class AddPaintDelegate(sheetBinding: BottomSheetAddNoteBinding,
                      viewModel: EditPhotoViewModel) : EditPhotoDelegate(sheetBinding, viewModel),
    OnSelectedColorListener {

    private val binding = IncludePaintContentBinding.bind(sheetBinding.clRoot)

    override fun observeState(state: EditPhotoContract.State?) {
        when (state) {
            is EditPhotoContract.State.PaintScreen -> {
                binding.clPaintContainer.show()
                sheetBinding.tvTitle.text = getString(R.string.paint)
            }
            else -> binding.clPaintContainer.hide()
        }
    }

    override fun onShown() {
        super.onShown()
        binding.run {
            rvPaintColorPicker.layoutManager =
                LinearLayoutManager(
                    root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            val colorPickerAdapter = ColorPickerAdapter(
                root.context,
                root.context.resources
                    .getIntArray(R.array.material_colors)
                    .map { CircleProperty(it, ContextCompat.getColor(root.context, R.color.material_color_yellow_900)) },
                WeakReference(this@AddPaintDelegate)
            )
            rvPaintColorPicker.adapter = colorPickerAdapter
        }
    }

    override fun clear() {
        viewModel.sendEvent(EditPhotoContract.Event.ClearPaint)
    }

    override fun onSelectedColor(color: Int) {
        viewModel.sendEvent(EditPhotoContract.Event.PaintColorPicked(color))
    }
}