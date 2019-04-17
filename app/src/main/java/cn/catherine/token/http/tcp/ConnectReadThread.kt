package cn.catherine.token.http.tcp

import cn.catherine.token.bean.HeartBeatBean
import cn.catherine.token.constant.Constants
import cn.catherine.token.constant.MessageConstants
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.listener.ObservableTimerListener
import cn.catherine.token.listener.SocketConnectReadListener
import cn.catherine.token.tool.LogTool
import cn.catherine.token.tool.StringTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.time.ObservableTimerTool
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/3/19 16:02
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.http.tcp
+--------------+---------------------------------
+ description  +   保持连接读取Socket通信的线程/The thread to connect and read message
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ConnectReadThread(
    private val socket: Socket,
    private val socketConnectReadListener: SocketConnectReadListener,
    private val observableTimerListener: ObservableTimerListener
) :
    Thread() {

    private val TAG = ConnectReadThread::class.java.simpleName
    override fun run() {
        super.run()

        val gson = GsonTool.getGson()
        //判斷當前是活著且非阻塞的狀態下才能繼續前行
        while (socket.keepAlive && !isInterrupted) {
            //开始监控SAN返回连接成功的信息的倒计时
            ObservableTimerTool.startCountDownTCPConnectTimer(observableTimerListener)
            LogTool.d(TAG, MessageConstants.SOCKET_HAD_CONNECTED_START_TO_RECEIVE + socket)
            try {
                //读取服务器端数据
                val bufferedReader =
                    BufferedReader(InputStreamReader(socket.getInputStream(), MessageConstants.socket.CHARSET_NAME))
                try {
                    while (socket.isConnected && socket.keepAlive) {
                        // TODO: 2018/10/27 暂时不用这个，因为在连接成功的状态下可能会因为这个心跳包发送导致异常，所以不用这个
                        //                            try {
                        //                                // 發送心跳包
                        //                                socket.sendUrgentData(MessageConstants.socket.HEART_BEAT);
                        //                            } catch (Exception e) {
                        //                                LogTool.e(TAG, MessageConstants.socket.CONNECT_EXCEPTION + e.getMessage());
                        //                                break;
                        //                            }
                        val readLine = bufferedReader.readLine()
                        if (readLine != null && readLine.trim { it <= ' ' }.isNotEmpty()) {
                            LogTool.d(TAG, MessageConstants.socket.TCP_RESPONSE + readLine)
                            val responseJson = gson.fromJson(readLine, ResponseJson::class.java)
                            bufferedReader?.close()
                            socketConnectReadListener.getReadData(responseJson)
                        }
                    }
                } catch (e: Exception) {
                    socket.keepAlive = false
                    LogTool.e(TAG, e.toString())
                    break
                } finally {
                    bufferedReader.close()
                }
            } catch (e: Exception) {
                socket.keepAlive = false
                LogTool.e(TAG, e.toString())
                break
            } finally {
                // 判断当前是否是主动断开造成的异常，如果是，就不需要reset
                socketConnectReadListener.closeConnectRead()
                break
            }
        }
    }
}