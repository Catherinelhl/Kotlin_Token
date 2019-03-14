package cn.catherine.token.base

import cn.catherine.token.gson.ResponseJson

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
        fun httpExceptionStatus(responseJson: ResponseJson)

        //连接失败，请检查网路
        fun connectFailure()

        fun noNetWork()

    }

    interface Presenter
}