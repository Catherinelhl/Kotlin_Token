package cn.catherine.token.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.catherine.token.manager.SoftKeyBroadManager
import cn.catherine.token.tool.OttoTool

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
abstract class BaseFragment : Fragment() {
    private val TAG = BaseFragment::class.java.simpleName
    private var activity: Activity? = null
    private var rootView: View? = null
    protected var softKeyBroadManager: SoftKeyBroadManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OttoTool.register(this)
        activity = getActivity()
        activity?.let {
            getArgs(it.intent.extras)
        }
        initViews(view)
        initListener()
    }


    /**
     * 得到当前的屏幕方向是否是垂直
     */
    fun getScreenDirectionIsPortrait(): Boolean =
        this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    abstract fun getLayoutRes(): Int
    abstract fun initViews(view: View)
    abstract fun getArgs(bundle: Bundle)
    abstract fun initListener()

    fun showToast(info: String) {
        if (!checkActivityState()) {
            return
        }
        (activity as BaseActivity).showToast(info)
    }

    private fun checkActivityState(): Boolean {
        return (activity != null
                && !activity!!.isFinishing
                && isAdded)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}