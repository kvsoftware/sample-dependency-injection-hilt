# Sample dependency injection with hilt
This is sample application demonstrates how to implement the dependency injection with Hilt.

## Step
### Setup the project
1. Add dependencies in build.gradle file in root project.
```
buildscript {
  ...
  ext.hilt_version = '2.33-beta'
  dependencies {
    ...
    classpath "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
  }
}
```

2. Add dependencies in build.gradle file in app module.
```
apply plugin: 'kotlin-kapt'
apply plugin: 'dagger.hilt.android.plugin'
...
dependencies {
  ...
  implementation "com.google.dagger:hilt-android:$hilt_version"
  kapt "com.google.dagger:hilt-compiler:$hilt_version"
}
```

### Coding
1. Create Application class that is annotated with @HiltAndroidApp.
```
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DependencyInjectionHilt : Application()
```

2. Create Hilt modules by creating the class that is annotated with @Module and @InstallIn annotations which provide the lifetime.

#### Component lifetimes

| Generated component       | Created at             | Destroyed at            |
| ------------------------- | ---------------------- | ----------------------- |
| SingletonComponent        | Application#onCreate() | Application#onDestroy() |
| ActivityRetainedComponent | Activity#onCreate()    | Activity#onDestroy()    |
| ViewModelComponent        | ViewModel created      | ViewModel destroyed     |
| ActivityComponent	        | Activity#onCreate()	   | Activity#onDestroy()    |
| FragmentComponent	        | Fragment#onAttach()    | Fragment#onDestroy()    |
| ViewComponent	            | View#super()	         | View destroyed          |
| ViewWithFragmentComponent	| View#super()           | View destroyed          |
| ServiceComponent	        | Service#onCreate()     | Service#onDestroy()     |

As below, 'ApplicationModule' class is the Hilt module which inform Hilt to create the instance at 'Application#onCreate()' and destroy the instance at 'Application#onDestroy()'.
```
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideRestClient(): RestClient {
        return RestClient(ConstantHelper.BASE_API_URL)
    }

    ...

}
```

As below, 'ViewModelModule' class is the Hilt module which inform Hilt to create the instance at 'ViewModel created' and destroy the instance at 'ViewModel destroyed'.
```
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideCountryRepository(restClient: RestClient): CountryRepository {
        return CountryRepository(restClient)
    }

    ...

}
```

3. Create ViewModel that is annotated with @HiltViewModel and inject the constructor with @Inject.
```
@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel()
```

4. Create Activity, Fragment, or other view class that is annotated with @AndroidEntryPoint and initialize the ViewModel by using viewModels() which provided by JetPack.
```
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
...
    private val viewModel: MainViewModel by viewModels()
...
}
```

Reference : https://developer.android.com/training/dependency-injection/hilt-android