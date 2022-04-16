package com.plcoding.stockmarketapp.presentation.company_listing


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    val repository: StockRepository
):ViewModel() {

   var state by mutableStateOf(CompanyListingState())

    private var searchJob: Job?=null
    init {
        getCompanyListing()
    }

    fun onEvent(event: CompanyListingEvent){
        when(event){
            is CompanyListingEvent.Refresh->{
                getCompanyListing(fetchRemote = true)
            }
            is CompanyListingEvent.OnSearchQueryChange->{
                state=state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob=viewModelScope.launch {
                    delay(500L)
                    getCompanyListing(query = state.searchQuery)
                }

            }
        }
    }
    private fun getCompanyListing(
        query:String=state.searchQuery.lowercase(),
        fetchRemote:Boolean=false
    ){
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchRemote,query)
                .collect{ result->
                    when(result){
                        is Resource.Success->{
                            result.data?.let { listings->
                                state=state.copy(
                                    companies = listings
                                )
                            }

                        }
                        is Resource.Error->Unit
                        is Resource.Loading->{
                            state=state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }

    }
}


