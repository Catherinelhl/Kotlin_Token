package cn.catherine.token.tool

import cn.catherine.token.bean.ServerBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.HostURLConstants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.vo.SeedFullNodeVO
import java.util.ArrayList

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 16:44
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.tool
+--------------+---------------------------------
+ description  +   工具類：服务器数据管理
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

object ServerTool {
    private val TAG = ServerTool::class.java.simpleName
    /*存储当前可用的SFN*/
    private var SFNServerBeanList: MutableList<ServerBean> = ArrayList()
    /*是否需要复活所有服务器*/
    var needResetServerStatus: Boolean = false
    /*当前默认的服务器*/
    private var defaultServerBean: ServerBean? = null

    fun getServerType(): String {
        return HostURLConstants.serverType
    }

    fun setServerType(serverType: String) {
        HostURLConstants.serverType = serverType
    }

    /**
     * 添加国际版SIT服务器（开发）
     */
    private fun addInternationalSTIServers(): List<ServerBean> {
        val SFNServerBeanDefaultList = ArrayList<ServerBean>()

        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_SIT_SGPAWS,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_SIT,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_SIT
            )
        )

        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_SIT_JPGOOGLE,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_SIT,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_SIT
            )
        )
        return SFNServerBeanDefaultList
    }


    /**
     * 添加国际版UAT服务器（开发）
     */
    private fun addInternationalUATServers(): List<ServerBean> {
        val SFNServerBeanDefaultList = ArrayList<ServerBean>()

        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_UAT,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_UAT,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_UAT
            )
        )

        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_UAT_SN_ALI,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_UAT,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_UAT
            )
        )

        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_UAT_SN_GOOGLE,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_UAT,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_UAT
            )
        )

        return SFNServerBeanDefaultList
    }

    /**
     * 添加国际版PRO服务器(正式)
     */
    private fun addInternationalPRDServers(): List<ServerBean> {
        val SFNServerBeanDefaultList = ArrayList<ServerBean>()

        //国际PRD AWSJP
        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_PRD_AWSJP,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_PRO,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_PRO
            )
        )

        //国际PRD ALIJP
        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_PRD_ALIJP,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_PRO,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_PRO
            )
        )

        //国际PRD GOOGLEJP
        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_PRD_GOOGLEJP,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_PRO,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_PRO
            )
        )

        //国际PRD GOOGLESGP
        SFNServerBeanDefaultList.add(
            getServerBean(
                SFNServerBeanDefaultList.size,
                HostURLConstants.SFN_URL_INTERNATIONAL_PRD_GOOGLESGP,
                HostURLConstants.APPLICATION_URL_INTERNATIONAL_PRO,
                HostURLConstants.UPDATE_URL_INTERNATIONAL_PRO
            )
        )

        return SFNServerBeanDefaultList

    }

    private fun getServerBean(id: Int, sfn: String, api: String, update: String): ServerBean {
        val serverBean = ServerBean()
        serverBean.sfnServer = sfn
        serverBean.apiServer = api
        serverBean.updateServer = update
        serverBean.id = id
        return serverBean
    }

    /**
     * 初始化默认的服务器
     */
    fun initServerData() {
        // 1：为了数据添加不重复，先清理到所有的数据
        cleanServerInfo()
        //2：判断当前的服务器类型，根据标注的服务器类型添加相对应的服务器数据
        when (getServerType()) {
            //3：添加所有的服务器至全局通用的服务器遍历数组里面进行stand by
            Constants.ServerType.INTERNATIONAL_SIT -> SFNServerBeanList.addAll(addInternationalSTIServers())
            Constants.ServerType.INTERNATIONAL_UAT -> SFNServerBeanList.addAll(addInternationalUATServers())
            Constants.ServerType.INTERNATIONAL_PRD -> SFNServerBeanList.addAll(addInternationalPRDServers())
            else -> {
            }
        }
        GsonTool.logInfo(TAG, getServerType(), SFNServerBeanList)
    }

    //清除所有的服务器信息
    fun cleanServerInfo() {
        setDefaultServerBean(null)
        SFNServerBeanList.clear()
    }

    /**
     * 添加服务器返回的节点信息
     *
     * @param seedFullNodeBeanListFromServer
     */
    fun addServerInfo(seedFullNodeBeanListFromServer: List<SeedFullNodeVO>) {
        //1：添加默认的服务器数据
        initServerData()
        //2：：添加服务器返回的数据
        if (ListTool.noEmpty(SFNServerBeanList)) {
            //3：遍历服务器返回的数据
            for (position in seedFullNodeBeanListFromServer.indices) {
                //4:与本地默认的作比较
                for (serverBeanLocal in SFNServerBeanList) {
                    //5:得到服务端传回的单条数据
                    val SFN_URL = Constants.SPLICE_CONVERTER(
                        seedFullNodeBeanListFromServer[position].getIp(),
                        seedFullNodeBeanListFromServer[position].getPort()
                    )
                    if (StringTool.equals(SFN_URL, serverBeanLocal.getSfnServer())) {
                        //如果遇到相同的url,则直接break当前循环，开始下一条判断
                        break
                    }
                    //6:否则添加至当前所有的可请求的服务器存档
                    val serverBeanNew = ServerBean(SFNServerBeanList.size, SFN_URL, false)
                    //7：通过接口返回的数据没有API和Update接口的domain，所以直接添加当前默认的接口
                    val serverBean = getDefaultServerBean()
                    if (serverBean != null) {
                        serverBeanNew.setApiServer(serverBean!!.getApiServer())
                        serverBeanNew.setUpdateServer(serverBean!!.getUpdateServer())
                    }
                    SFNServerBeanList.add(serverBeanNew)
                    break
                }
            }
            GsonTool.logInfo(TAG, MessageConstants.ALL_SERVER_INFO, SFNServerBeanList)
        }
    }

    /**
     * 更换服务器,查看是否有可更换的服务器信息
     */
    fun checkAvailableServerToSwitch(): ServerBean? {
        //1：取到当前默认的服务器
        val serverBeanDefault = getDefaultServerBean() ?: return null
        //2：判断数据非空
        //3：得到当前服务器的id：表示當前服務器的顺序
        val currentServerPosition = serverBeanDefault.getId()
        //4：新建变量用于得到当前需要切换的新服务器
        var serverBeanNext: ServerBean? = null
        //5：判断当前服务器的id，如果当前id>=0且小于当前数据的数据，代表属于数组里面的数据
        if (currentServerPosition < SFNServerBeanList.size && currentServerPosition >= 0) {
            val currentServerBean = SFNServerBeanList[currentServerPosition]
            if (currentServerBean != null) {
                currentServerBean!!.setUnAvailable(true)
            }
            //6：如果当前id小于等于SFNServerBeanList数量-1，代表还有可取的数据
            if (currentServerPosition < SFNServerBeanList.size - 1) {
                //7:得到新的请求地址信息
                val serverBeanNew = SFNServerBeanList[currentServerPosition + 1]
                if (serverBeanNew != null) {
                    //8：判断新取到的服务器是否可用
                    if (!serverBeanNew!!.isUnAvailable()) {
                        LogTool.d(TAG, MessageConstants.NEW_SFN_SERVER + serverBeanNew!!)
                        serverBeanNext = serverBeanNew
                    }

                }
            } else {
                //否则，检测当前是否需要重置所有服务器的状态
                if (needResetServerStatus) {
                    //否则遍历其中可用的url
                    for (serverBean in SFNServerBeanList) {
                        serverBean.isUnAvailable = false
                    }
                   needResetServerStatus = false
                }
                //重新选取可用的服务器数据
                for (serverBean in SFNServerBeanList) {
                    if (!serverBean.isUnAvailable) {
                        LogTool.d(TAG, MessageConstants.NEW_SFN_SERVER + serverBean)
                        serverBeanNext = serverBean
                        break
                    }
                }
            }

        }
        if (serverBeanNext != null) {
            setDefaultServerBean(serverBeanNext)
            return serverBeanNext
        }
        return null
    }

    fun getDefaultServerBean(): ServerBean? {
        if (defaultServerBean == null) {
            //设置默认的服务器
            setDefaultServerBean(SFNServerBeanList[0])
        }
        return defaultServerBean
    }

    fun setDefaultServerBean(defaultServerBean: ServerBean?) {
       this.defaultServerBean = defaultServerBean
    }
}