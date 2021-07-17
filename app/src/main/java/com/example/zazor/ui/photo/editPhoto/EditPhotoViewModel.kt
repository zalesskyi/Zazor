package com.example.zazor.ui.photo.editPhoto

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.photo.basic.BasicPhotoContract
import kotlinx.coroutines.flow.MutableSharedFlow

interface EditPhotoViewModel : BaseViewModel<EditPhotoContract.State, EditPhotoContract.Event>

class EditPhotoViewModelImpl(private val editPhotoFlow: MutableSharedFlow<EditPhotoContract.Flow>) : BaseViewModelImpl<EditPhotoContract.State, EditPhotoContract.Event>(), EditPhotoViewModel {

    override suspend fun initialState(): EditPhotoContract.State = EditPhotoContract.State.NotesScreen

    override fun onEventArrived(event: EditPhotoContract.Event?) {
        when (event) {
            is EditPhotoContract.Event.NotesTabPressed -> {
                uiState.value = EditPhotoContract.State.NotesScreen
                disallowPaint()
            }
            is EditPhotoContract.Event.PaintTabPressed -> {
                uiState.value = EditPhotoContract.State.PaintScreen
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.AllowPaint(null))
                }
            }
            is EditPhotoContract.Event.TextTabPressed -> {
                uiState.value = EditPhotoContract.State.TextScreen
                disallowPaint()
            }
            is EditPhotoContract.Event.DonePressed -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.Done)
                }
            }
            is EditPhotoContract.Event.CancelPressed -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.Cancel)
                }
            }
            is EditPhotoContract.Event.NotesEntered -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.AddNote(event.notes))
                }
            }
            is EditPhotoContract.Event.PaintColorPicked -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.AllowPaint(event.color))
                }
            }
            is EditPhotoContract.Event.OverlayEntered -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.AddOverlay(
                        event.text, event.color, event.fontId))
                }
            }
            is EditPhotoContract.Event.ClearPaint -> {
                launch {
                    editPhotoFlow.emit(EditPhotoContract.Flow.ClearPaint)
                }
            }
        }
    }

    private fun disallowPaint() {
        launch {
            editPhotoFlow.emit(EditPhotoContract.Flow.DisallowPaint)
        }
    }
}