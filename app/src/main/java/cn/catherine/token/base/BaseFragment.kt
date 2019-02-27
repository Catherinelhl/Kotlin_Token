package cn.catherine.token.base

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
    private val activity: Activity? by lazy { getActivity() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { }
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}