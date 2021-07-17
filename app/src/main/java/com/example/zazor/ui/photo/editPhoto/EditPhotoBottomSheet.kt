package com.example.zazor.ui.photo.editPhoto

import com.example.zazor.databinding.BottomSheetAddNoteBinding
import com.example.zazor.ui.base.BasePersistentBottomSheet
import com.example.zazor.ui.photo.PhotoHandler
import com.example.zazor.ui.photo.di.injectViewModel
import com.example.zazor.ui.photo.editPhoto.delegates.AddNoteDelegate
import com.example.zazor.ui.photo.editPhoto.delegates.AddOverlayDelegate
import com.example.zazor.ui.photo.editPhoto.delegates.AddPaintDelegate
import com.example.zazor.ui.photo.editPhoto.delegates.EditPhotoDelegate
import com.example.zazor.utils.extensions.toggle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.ref.WeakReference

class EditPhotoBottomSheet(val binding: BottomSheetAddNoteBinding, callback: PhotoHandler)
    : BasePersistentBottomSheet<EditPhotoContract.State, EditPhotoContract.Event>(){

    override val viewModel by injectViewModel()

    override val view = binding.root

    override val behavior = BottomSheetBehavior.from(binding.clRoot)

    private val callbackRef = WeakReference(callback)

    private val delegates = hashMapOf<EditPhotoContract.State, EditPhotoDelegate>()

    override fun observeState(state: EditPhotoContract.State?) {
        when  (state) {
            EditPhotoContract.State.NotesScreen -> {
                binding.ivNote.toggle(true)
                binding.ivPaint.toggle(false)
                binding.ivText.toggle(false)
            }
            EditPhotoContract.State.PaintScreen -> {
                binding.ivNote.toggle(false)
                binding.ivPaint.toggle(true)
                binding.ivText.toggle(false)
            }
            EditPhotoContract.State.TextScreen -> {
                binding.ivNote.toggle(false)
                binding.ivPaint.toggle(false)
                binding.ivText.toggle(true)
            }
        }
    }

    override fun onShown() {
        initDelegates()
        super.onShown()
        behavior.peekHeight = 120
        with(binding) {
            ivNote.setOnClickListener {
                viewModel.sendEvent(EditPhotoContract.Event
                    .NotesTabPressed)
            }
            ivPaint.setOnClickListener {
                viewModel.sendEvent(EditPhotoContract.Event.PaintTabPressed)
            }
            ivText.setOnClickListener {
                viewModel.sendEvent(EditPhotoContract.Event.TextTabPressed)
            }
            tvCancel.setOnClickListener {
                viewModel.sendEvent(EditPhotoContract.Event.CancelPressed)
            }
            tvDone.setOnClickListener {
                viewModel.sendEvent(EditPhotoContract.Event.DonePressed)
            }
        }
    }

    fun clearAll() {
        delegates.values.forEach {
            it.clear()
        }
    }

    private fun initDelegates() {
        delegates[EditPhotoContract.State.NotesScreen] = AddNoteDelegate(binding, viewModel)
        delegates[EditPhotoContract.State.TextScreen] = AddOverlayDelegate(binding, viewModel)
        delegates[EditPhotoContract.State.PaintScreen] = AddPaintDelegate(binding, viewModel)
        delegates.values.forEach {
            it.onShown()
        }
    }
}