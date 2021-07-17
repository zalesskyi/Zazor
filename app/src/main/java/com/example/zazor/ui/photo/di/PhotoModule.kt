package com.example.zazor.ui.photo.di

import androidx.activity.ComponentActivity
import com.example.zazor.ui.photo.PhotoActivity
import com.example.zazor.ui.photo.PhotoViewModel
import com.example.zazor.ui.photo.PhotoViewModelImpl
import com.example.zazor.ui.photo.editPhoto.EditPhotoBottomSheet
import com.example.zazor.ui.photo.editPhoto.EditPhotoContract
import com.example.zazor.ui.photo.editPhoto.EditPhotoViewModel
import com.example.zazor.ui.photo.editPhoto.EditPhotoViewModelImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ADD_NOTE_FLOW = "add_note_flow"

val photoModule = module {

    single(named(ADD_NOTE_FLOW)) { MutableSharedFlow<EditPhotoContract.Flow>() }

    viewModel { PhotoViewModelImpl(get<MutableSharedFlow<EditPhotoContract.Flow>>(named(ADD_NOTE_FLOW)), get()) }

    viewModel { EditPhotoViewModelImpl(get(named(ADD_NOTE_FLOW))) }
}

fun PhotoActivity.injectViewModel(): Lazy<PhotoViewModel> =
      lazy { getViewModel<PhotoViewModelImpl>(null) }

fun EditPhotoBottomSheet.injectViewModel(): Lazy<EditPhotoViewModel> =
    lazy { (binding.root.context as? ComponentActivity)!!.getViewModel<EditPhotoViewModelImpl>() }