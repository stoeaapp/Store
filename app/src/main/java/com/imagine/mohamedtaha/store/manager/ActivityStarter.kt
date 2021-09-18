package com.imagine.mohamedtaha.store.manager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imagine.mohamedtaha.store.di.scopes.PerActivity
import com.imagine.mohamedtaha.store.manager.base.BaseActivity
import com.imagine.mohamedtaha.store.manager.base.BaseFragment
import javax.inject.Inject
class ActivityStarter @Inject internal constructor(private val baseActivity: BaseActivity){
    private var intent:Intent? = null
    private var activity:Class<out AppCompatActivity>? = null
    fun make(activityClass: Class<out BaseActivity>):ActivityBuilder{
        activity = activityClass
        intent = Intent(baseActivity,activityClass)
        return Builder()
    }
   private inner class Builder : ActivityBuilder {
       private var bundle:Bundle?= null
       private var activityOptionsBundle:Bundle? = null
       private var isToFinishCurrent :Boolean = false
       private var requestCode:Int = 0
       override fun start() {
          if (bundle != null)intent?.putExtras(bundle!!)
           if (requestCode == 0){
               if (activityOptionsBundle == null)baseActivity.startActivity(intent) else baseActivity.startActivity(intent,activityOptionsBundle)
           }else {
               val currentFragment = baseActivity.getCurrentFragment<BaseFragment>()
               if (currentFragment != null)
                   currentFragment.startActivityForResult(intent,requestCode)
               else
                   baseActivity.startActivityForResult(intent,requestCode)
           }
           if (isToFinishCurrent)baseActivity.finish()
       }

       override fun addBundle(bundle: Bundle): ActivityBuilder {
           if (this.bundle != null)bundle.putAll(bundle) else this.bundle = bundle
           return this
       }

       override fun byFinishAll(): ActivityBuilder {
           intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
           return this
       }

       override fun forResult(requestCode: Int): ActivityBuilder {
           this.requestCode = requestCode
           return this
       }

       override fun <T : BaseFragment> setPage(page: Class<T>): ActivityBuilder {
           intent?.putExtra(ACTIVITY_FIRST_PAGE,page)
           return this
       }

   }
    companion object{
        const val ACTIVITY_FIRST_PAGE = "first_page"
    }
}