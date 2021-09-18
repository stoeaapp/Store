package com.imagine.mohamedtaha.store.manager.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.imagine.mohamedtaha.store.R
import com.imagine.mohamedtaha.store.manager.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), Navigator {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var activityStarter: ActivityStarter
    @Inject
    lateinit var navigationFactory: FragmentNavigationFactory
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        // handler(this)
        // navigationFactory = FragmentNavigationFactory(handler)
    }

//    fun navigator(navigator: Navigator) {
//        this.navigator = navigator
//    }

    fun <F : BaseFragment> getCurrentFragment(): Fragment? {
        return if (findFragmentPlaceHolder() == 0) null else supportFragmentManager.findFragmentById(findFragmentPlaceHolder())
    }

    abstract fun findFragmentPlaceHolder(): Int
    fun showErrorMessage(view: View, message: String?) {
        val snackBar = Snackbar.make(view, message.toString(), Snackbar.LENGTH_SHORT)
        snackBar.behavior = BaseTransientBottomBar.Behavior()
        val snackView = snackBar.view
        snackView.alpha = 0.5f
        snackBar.setAction("Ok") { snackBar.dismiss() }
        snackBar.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
        val textView = snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 2
        textView.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
        snackBar.show()
    }

    override fun <T : BaseFragment> load(tClass: Class<T>): FragmentActionPerformer<T> {
        return navigationFactory.make(tClass)
        // TODO("Not yet implemented")
    }

    override fun loadActivity(aClass: Class<out BaseActivity>): ActivityBuilder {
        return activityStarter.make(aClass)
        // TODO("Not yet implemented")
    }

    override fun <T : BaseFragment> loadActivity(aClass: Class<out BaseActivity>, pageTClass: Class<T>): ActivityBuilder {
        return activityStarter.make(aClass).setPage(pageTClass)
        //  TODO("Not yet implemented")
    }


    override fun goBack() {
        onBackPressed()
    }
    //    fun onError(throwable: Throwable){
//        try {
//            if (throwable is ServerException) throwable.message?.let { showErrorMessage(it) }
//            else if (throwable is ConnectException || throwable is SocketException)
//                showErrorMessage("Connect to internet")
//            else if (throwable is ApplicationException){
//                throwable.message?.let { showErrorMessage(it) }
//            }else if (throwable is AuthenticationException){
//
//            }else if (throwable is SocketTimeoutException){
//                showErrorMessage("Connection time out")
//            }else if (throwable is ServerError){
//                throwable.message?.let { showErrorMessage(it) }
//                val errorBody  = throwable.errorBody
//                if (errorBody.isNotBlank()){
//                    val dialog =Dialog(this)
//                    dialog.setTitle("Server Error")
//                    val webView = WebView(this)
//                    dialog.setContentView(webView)
//                    webView.loadData(errorBody,"text/html","UFT-8")
//                    dialog.show()
//                }
//            }else{
//                showErrorMessage("Something wrong here")
//                throwable.printStackTrace()
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }
//    }
}