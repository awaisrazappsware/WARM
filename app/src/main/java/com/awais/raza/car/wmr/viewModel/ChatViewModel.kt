package com.awais.raza.car.wmr.viewModel

import androidx.lifecycle.*
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.repo.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val chatListResponse: LiveData<List<ChatData>> = liveData {
        val response = repository.fetchChats()
        emitSource(response)
    }

    val chatUserResponse: LiveData<List<ChatData>> = liveData {
        val user = savedStateHandle.get<String>("user") ?: ""
        val app = savedStateHandle.get<String>("app") ?: ""
        val response = repository.fetchMessagesByUser(user, app)
        emitSource(response)
    }
//
//    fun searchDatabase(query: String): LiveData<List<ChatData>> {
//            return repository.getAllChatByUser(query).asLiveData()
//    }

}