package cn.catherine.token.base

/**
 *
 * @since 2019/2/19
 *
 * @author catherine.brainwilliam
 *
 * @version
 *
 */
interface BaseContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun noNetWork()

    }
    interface Presenter {}
}