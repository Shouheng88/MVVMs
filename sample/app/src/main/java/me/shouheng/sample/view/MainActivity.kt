package me.shouheng.sample.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.bean.Status
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.MainViewModel
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.ui.ToastUtils
import org.greenrobot.eventbus.Subscribe

/**
 * MVVM 框架演示工程
 *
 * @author Wngshhng 2019-6-29
 */
class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutResId() = R.layout.activity_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.startLoad()
    }

    private fun addSubscriptions() {
        vm.getObservable(String::class.java).observe(this, Observer {
            when(it!!.status) {
                Status.SUCCESS -> {
                    ToastUtils.showShort(it.data)
                }
                Status.FAILED -> {
                    ToastUtils.showShort(it.errorMessage)
                }
                Status.LOADING -> {
                    // temp do nothing
                }
                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        ToastUtils.showShort(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }
}
