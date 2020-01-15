https://thoughtbot.com/blog/android-networking-view-state

https://engineering.udacity.com/modeling-viewmodel-states-using-kotlins-sealed-classes-a5d415ed87a7



Controlando ViewStates na ViewModel


Por que fazer assim?

Vamos pegar um exemplo basico de uma chamda simples de uma API de filmes.

``
//ViewModel

private val movieResult = MutableLiveData<List<Movie>>()
val movies = movieResult

private fun loadMovies() {

	isLoading.value =  true
	viewModelScope.launch(Dispatchers.IO) {
		try {
			val response = repository.getPage()
			response.let {
				movieResult.postValue(it.items)
			}
		} catch (e: Exception) {
			Log.e("TAG", "error")
		}
	}
}
``



``
//MainActivity

viewModel.loadMovies()

viewModel.githubRepositories
.observe(this, Observer { 
	binding.tvNoRepositories.visibility = View.GONE
	adapter.submitList(it.response)
	hideLoading()
})


``
Essa é uma chamada de um fluxo simples onde a View precisa apenas mostrar os dados, 
sem que haja interações extras com componentes da tela. 
Porém, e se quisermos mostrar um loading antes de carregar os dados como fariamos?


ViewModel
``
//    private val movieResult = MutableLiveData<List<Movie>>()
//    val movies = movieResult
//
//    private val responseIsLoading = MutableLiveData<Boolean>()
//    val isLoading = responseIsLoading
//
//    private fun loadMovies() {
//
//        isLoading.value =  true
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = repository.getPage()
//                response.let {
//                    movieResult.postValue(it.items)
//                }
//            } catch (e: Exception) {
//                Log.e("TAG", "error")
//            }
//            isLoading.postValue( false)
//
//        }
//    }

``


MainActivity


viewModel.loadMovies()

viewModel.movies
.observe(this, Observer { 
	binding.tvNoRepositories.visibility = View.GONE
	adapter.submitList(it.response)
	hideLoading()
})

viewModel.isLoading
.observe(this, Observer { 
	showLoading()
})


Começou a ficar bagunçado não é? agora a View vai ter que ter 2 observers separados, 
mas não para por ai, caso queiramos adicionar mais ações a a View como, mostrar um Toast, 
ou até mesmo um feedback de mensagens de erro
teremos que criar mais observers? ou tiramos a lógica do ViewModel e colocamos na View?

Existe um jeito muito simples de controlar esses "estados da tela" usando SealedClass

Pimeiro criamos uma SealedClass que vai representar os estados da view, 
em nosso caso como estamos na tela de Movie vamos criar a MovieViewState


``
//sealed class MovieViewState
//class Movies(val result: Movie) : MovieViewState()
//class LongToast (val message: String): MovieViewState()
//class GenericError (val message: String): MovieViewState()

//object ShowMovieLoading : MovieViewState()
//object EmptyMovieResults : MovieViewState()
``


Agora dentro da ViewModel vamos instanciar o MutableLiveData<MovieViewState>

``
//	  
//    private val movieResult = MutableLiveData<MovieViewState>()
//    val movies = movieResult
//
//    private fun loadMovies() {
//
//        movies.value =  ShowMovieLoading
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = repository.getPage()
//                response.let {
//                    if (it.items.isEmpty()) {
//                        movieResult.postValue(EmptyMovieResults)
//                    } else {
//                        movieResult.postValue(Movies(it.items))
//                    }
//                }
//            } catch (e: Exception) {
//                movieResult.postValue(GenericError(e.message.toString()))
//            }
//        }
//    }
//
``

MainActivity

//viewModel.githubRepositories.observe(this,
//Observer {
//	when (it) {
//		is Results -> {
//			binding.tvNoRepositories.visibility = View.GONE
//			adapter.submitList(it.response)
//			hideLoading()
//		}
//		is EmptyResults -> {
//			binding.tvNoRepositories.visibility = View.VISIBLE
//			hideLoading()
//		}
//		is Error -> {
//			binding.tvNoRepositories.visibility = View.VISIBLE
//			hideLoading()
//			Log.e("Bro", it.error)
//		}
//		is ShowLoading -> {
//			showLoading()
//		}
//	}
//})








