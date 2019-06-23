# Github-client
The read me covers the essential information when dealing with the repo

# 1-Requirements
- Android Studio V 3.0+
# 2- Dependencies
- Retrofit
- Room
- Rx Java/Android
- Timber
- Butterknife
- Glide

# 3- Architecture
I've used The MVP (Model-View-Presenter) design pattern to construct the base of the app along side with repository pattern and observer pattern, And here I will be covering the components of the Github_Client app.

## 1. The view
The view is everything related to android platform (Activities, Fragments, Adapters, ..etc)

## 2. The Model (Data)
data is the layer in which anything related to data reside, it's divided into:
- api: Responsible for network calls 
- database: Responsible for storing and getting offline data
- model: Hold the application data models (Data objects)
- repository: The consumer of the api and database methods, it's constructed of LocalRepository for consuming database methods, and a RemoteRepository for consuming api methods
- interactors: Interactors is the bridge between presenters and repositories, presenters shouldn't know the data source, so the interactor handle this.

## 3. The presenter
presenter acts like (a Man in the middle) between data layer and view layer.

# Architecture components interaction example.
1. The view calls the presenter to get the data
2. The presenter calls the interactor for the data
3. The interactor asks the remote repository to get the data, If the remote failed, it calls the local repository to get the data and return the result to the presenter
4. The presenter then return the result or the error to the view which display it.

