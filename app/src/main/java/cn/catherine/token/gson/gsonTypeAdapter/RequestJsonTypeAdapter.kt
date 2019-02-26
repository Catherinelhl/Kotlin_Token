package cn.catherine.token.gson.gsonTypeAdapter

import cn.catherine.token.constant.Constants
import cn.catherine.token.gson.RequestJson
import cn.catherine.token.tool.ListTool
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.json.JsonTool
import cn.catherine.token.vo.*
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import java.util.ArrayList
import java.util.LinkedHashMap

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:47
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.gson.gsonTypeAdapter
+--------------+---------------------------------
+ description  +   自定義Request/Response數據傳輸交互字段，作Gson輔助類：RequestJson
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class RequestJsonTypeAdapter : TypeAdapter<RequestJson>() {

    private val TAG = RequestJsonTypeAdapter::class.java.simpleName

    // RequestJson 順序
    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, requestJson: RequestJson) {
        jsonWriter.beginObject()
        jsonWriter.name(Constants.GSON_KEY_VERSIONVO)
        writeVersionVO(jsonWriter, requestJson.versionVO)

        jsonWriter.name(Constants.GSON_KEY_VERSIONVOLIST)
        writeVersionListVO(jsonWriter, requestJson.versionVOList)

        jsonWriter.name(Constants.GSON_KEY_CLIENTIPINFOVO)
        writeGetClientInfoVO(jsonWriter, requestJson.clientIpInfoVO)

        jsonWriter.name(Constants.GSON_KEY_CLIENTIPINFOVOLIST)
        writeGetClientInfoListVO(jsonWriter, requestJson.clientIpInfoVOList)
        jsonWriter.name(Constants.GSON_KEY_WALLETVO)
        writeWalletVO(jsonWriter, requestJson.walletVO)
        jsonWriter.name(Constants.GSON_KEY_DATABASEVO)
        writeDatabaseVO(jsonWriter, requestJson.databaseVO)

        jsonWriter.name(Constants.GSON_KEY_DATABASEVOLIST)
        writeDatabaseVOList(jsonWriter, requestJson.databaseVOList)

        jsonWriter.name(Constants.GSON_KEY_PAGINATIONVO)
        writeGetPaginationVO(jsonWriter, requestJson.paginationVO)
        jsonWriter.name(Constants.GSON_KEY_PAGINATIONVOLIST)
        writeGetPaginationVOList(jsonWriter, requestJson.paginationVOList)

        jsonWriter.endObject()
    }

    @Throws(IOException::class)
    private fun writeVersionVO(jsonWriter: JsonWriter, versionVO: VersionVO?) {
        jsonWriter.beginObject()
        if (versionVO == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_ID).value(versionVO._id)
        jsonWriter.name(Constants.GSON_KEY_AUTH_KEY).value(versionVO.authKey)
        jsonWriter.name(Constants.GSON_KEY_VERSION).value(versionVO.version)
        jsonWriter.name(Constants.GSON_KEY_FORCE_UPGRADE).value(versionVO.forceUpgrade.toLong())
        jsonWriter.name(Constants.GSON_KEY_UPDATE_URL).value(versionVO.updateUrl)
        jsonWriter.name(Constants.GSON_KEY_TYPE).value(versionVO.type)
        jsonWriter.name(Constants.GSON_KEY_MOTIFY_TIME).value(versionVO.motifyTime)
        jsonWriter.name(Constants.GSON_KEY_SYSTEM_TIME).value(versionVO.systemTime)
        jsonWriter.endObject()
    }

    @Throws(IOException::class)
    private fun writeVersionListVO(jsonWriter: JsonWriter, versionVOList: List<VersionVO>) {
        jsonWriter.beginArray()
        if (ListTool.isEmpty(versionVOList)) {
            return
        }
        for (versionVO in versionVOList) {
            writeVersionVO(jsonWriter, versionVO)
        }
        jsonWriter.endArray()
    }


    @Throws(IOException::class)
    private fun writeGetClientInfoVO(jsonWriter: JsonWriter, clientIpInfoVO: ClientIpInfoVO?) {
        jsonWriter.beginObject()
        if (clientIpInfoVO == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_MAC_ADDRESS_EXTERNAL_IP).value(clientIpInfoVO.macAddressExternalIp)
        jsonWriter.name(Constants.GSON_KEY_EXTERNAL_IP).value(clientIpInfoVO.externalIp)
        jsonWriter.name(Constants.GSON_KEY_INTERNAL_IP).value(clientIpInfoVO.internalIp)
        jsonWriter.name(Constants.GSON_KEY_CLIENT_TYPE).value(clientIpInfoVO.clientType)
        jsonWriter.name(Constants.GSON_KEY_EXTERNAL_PORT).value(clientIpInfoVO.externalPort.toLong())
        jsonWriter.name(Constants.GSON_KEY_INTERNAL_PORT).value(clientIpInfoVO.internalPort.toLong())
        jsonWriter.name(Constants.GSON_KEY_VIRTUAL_COIN).value(clientIpInfoVO.virtualCoin.toString())
        //        writeVirtualCoin(jsonWriter, clientIpInfoVO.getVirtualCoin());
        jsonWriter.name(Constants.GSON_KEY_RPC_PORT).value(clientIpInfoVO.rpcPort.toLong())
        jsonWriter.endObject()
    }

    @Throws(IOException::class)
    private fun writeVirtualCoin(jsonWriter: JsonWriter, virtualCoin: ArrayList<LinkedHashMap<String, String>>) {
        jsonWriter.beginArray()
        jsonWriter.endArray()
    }

    @Throws(IOException::class)
    private fun writeGetClientInfoListVO(jsonWriter: JsonWriter, clientIpInfoVOList: List<ClientIpInfoVO>) {
        jsonWriter.beginArray()
        if (ListTool.isEmpty(clientIpInfoVOList)) {
            return
        }
        for (clientIpInfoVO in clientIpInfoVOList) {
            writeGetClientInfoVO(jsonWriter, clientIpInfoVO)
        }
        jsonWriter.endArray()
    }

    @Throws(IOException::class)
    private fun writeDatabaseVOList(jsonWriter: JsonWriter, databaseVOList: List<DatabaseVO>) {
        jsonWriter.beginArray()
        if (ListTool.isEmpty(databaseVOList)) {
            return
        }
        for (databaseVO in databaseVOList) {
            writeDatabaseVO(jsonWriter, databaseVO)
        }
        jsonWriter.endArray()
    }

    @Throws(IOException::class)
    private fun writeGetPaginationVO(jsonWriter: JsonWriter, paginationVO: PaginationVO?) {
        jsonWriter.beginObject()
        if (paginationVO == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_OBJECT_LIST).value(paginationVO.objectList.toString())
        //        writeObjectList(jsonWriter,paginationVO.getObjectList());
        jsonWriter.name(Constants.GSON_KEY_NEXT_OBJECT_ID).value(paginationVO.nextObjectId)
        jsonWriter.endObject()

    }

    @Throws(IOException::class)
    private fun writeObjectList(jsonWriter: JsonWriter, objectList: List<Any>) {
        jsonWriter.beginArray()
        for (o in objectList) {
        }
        jsonWriter.endArray()
    }

    @Throws(IOException::class)
    private fun writeGetPaginationVOList(jsonWriter: JsonWriter, paginationVOList: List<PaginationVO>) {
        jsonWriter.beginArray()
        val gson = Gson()
        if (ListTool.isEmpty(paginationVOList)) {
            return
        }
        for (paginationVO in paginationVOList) {
            jsonWriter.beginObject()
            val objectList = paginationVO.objectList
            jsonWriter.name(Constants.GSON_KEY_OBJECTLIST)
            jsonWriter.beginArray()
            for (`object` in objectList) {
                val objectStr = gson.toJson(`object`)
                var type: Type? = null
                if (JsonTool.isOpenBlock(objectStr)) {
                    type = object : TypeToken<TransactionChainVO<TransactionChainOpenVO>>() {

                    }.type
                } else if (JsonTool.isSendBlock(objectStr)) {
                    type = object : TypeToken<TransactionChainVO<TransactionChainSendVO>>() {

                    }.type
                } else if (JsonTool.isReceiveBlock(objectStr)) {
                    type = object : TypeToken<TransactionChainVO<TransactionChainReceiveVO>>() {

                    }.type
                } else if (JsonTool.isChangeBlock(objectStr)) {
                    type = object : TypeToken<TransactionChainVO<TransactionChainChangeVO>>() {

                    }.type
                }

                val transactionChainVO = gson.fromJson<TransactionChainVO<*>>(objectStr, type)

                writeTransactionChainVO(jsonWriter, transactionChainVO)
            }
            jsonWriter.endArray()

            jsonWriter.name(Constants.GSON_KEY_NEXTOBJECTID).value(paginationVO.nextObjectId)
            jsonWriter.endObject()
        }

        jsonWriter.endArray()

    }


    // WalletVO 順序
    @Throws(IOException::class)
    private fun writeWalletVO(jsonWriter: JsonWriter, walletVO: WalletVO?) {
        jsonWriter.beginObject()
        if (walletVO == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_ACCESSTOKEN).value(walletVO.accessToken)
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(walletVO.blockService)
        jsonWriter.name(Constants.GSON_KEY_WALLETADDRESS).value(walletVO.walletAddress)
        jsonWriter.endObject()
    }

    // DatabaseVO 順序
    @Throws(IOException::class)
    private fun writeDatabaseVO(jsonWriter: JsonWriter, databaseVO: DatabaseVO?) {
        jsonWriter.beginObject()
        if (databaseVO == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_TRANSACTIONCHAINVO)
        writeTransactionChainVO(jsonWriter, databaseVO.transactionChainVO)
        jsonWriter.name(Constants.GSON_KEY_GENESISVO)
        writeGenesisVO(jsonWriter, databaseVO.genesisVO)
        jsonWriter.endObject()
    }

    // GenesisVO 順序
    @Throws(IOException::class)
    private fun writeGenesisVO(jsonWriter: JsonWriter, genesisVO: GenesisVO?) {
        jsonWriter.beginObject()
        if (genesisVO == null) {
            return
        }
        jsonWriter.name(Constants.MONGODB_KEY_ID).value(genesisVO._id)
        jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(genesisVO.previous)
        jsonWriter.name(Constants.MONGODB_KEY_PUBLICUNIT).value(genesisVO.publicUnit)
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(genesisVO.blockService)
        jsonWriter.name(Constants.MONGODB_KEY_CURRENCYUNIT).value(genesisVO.currencyUnit)
        jsonWriter.name(Constants.MONGODB_KEY_CIRCULATION).value(genesisVO.circulation)
        jsonWriter.name(Constants.MONGODB_KEY_COINBASE).value(genesisVO.coinBase)
        jsonWriter.name(Constants.MONGODB_KEY_GENEISISBLOCKACCOUNT).value(genesisVO.genesisBlockAccount)
        jsonWriter.name(Constants.MONGODB_KEY_COINBASEACCOUNT).value(genesisVO.coinBaseAccount)
        jsonWriter.name(Constants.MONGODB_KEY_INTERESTRATE).value(genesisVO.interestRate)
        jsonWriter.name(Constants.MONGODB_KEY_WORK).value(genesisVO.work)
        jsonWriter.name(Constants.MONGODB_KEY_CREATETIME).value(genesisVO.createTime)
        jsonWriter.name(Constants.MONGODB_KEY_SYSTEMTIME).value(genesisVO.systemTime)
        jsonWriter.endObject()
    }

    // TransactionChainVO 順序
    @Throws(IOException::class)
    private fun writeTransactionChainVO(jsonWriter: JsonWriter, transactionChainVO: TransactionChainVO<*>?) {
        jsonWriter.beginObject()
        if (transactionChainVO == null) {
            return
        }
        jsonWriter.name(Constants.MONGODB_KEY_ID).value(transactionChainVO._id)
        jsonWriter.name(Constants.GSON_KEY_TC).value(GsonTool.getGson().toJson(transactionChainVO.tc))
        //        writeTC(jsonWriter, transactionChainVO.getTc());
        jsonWriter.name(Constants.MONGODB_KEY_SIGNATURE).value(transactionChainVO.signature)
        jsonWriter.name(Constants.MONGODB_KEY_SIGNATURESEND).value(transactionChainVO.signatureSend)
        jsonWriter.name(Constants.MONGODB_KEY_PUBLICKEY).value(transactionChainVO.publicKey)
        jsonWriter.name(Constants.MONGODB_KEY_HEIGHT).value(transactionChainVO.height.toLong())
        jsonWriter.name(Constants.MONGODB_KEY_PRODUCEKEYTYPE).value(transactionChainVO.produceKeyType)
        jsonWriter.name(Constants.MONGODB_KEY_TXHASH).value(transactionChainVO.txHash)
        jsonWriter.name(Constants.MONGODB_KEY_SYSTEMTIME).value(transactionChainVO.systemTime)
        jsonWriter.endObject()
    }

    // Tc區塊 順序
    @Throws(IOException::class)
    fun <T> writeTC(jsonWriter: JsonWriter, tc: T?) {
        jsonWriter.beginObject()
        if (tc == null) {
            return
        }
        //判断当前属于什么区块
        val objectStr = GsonTool.getGson().toJson(tc)
        if (JsonTool.isOpenBlock(objectStr)) {
            val transactionChainOpenVO = GsonTool.convert(objectStr, TransactionChainOpenVO::class.java)
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainOpenVO.previous)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainOpenVO.blockService)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainOpenVO.blockType)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTXTYPE).value(transactionChainOpenVO.blockTxType)
            jsonWriter.name(Constants.MONGODB_KEY_SOURCETXHASH).value(transactionChainOpenVO.sourceTxhash)
            jsonWriter.name(Constants.MONGODB_KEY_AMOUNT).value(transactionChainOpenVO.amount)
            jsonWriter.name(Constants.MONGODB_KEY_RECEIVEAMOUNT).value(transactionChainOpenVO.receiveAmount)
            jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainOpenVO.representative)
            jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainOpenVO.wallet)
            jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainOpenVO.work)
            jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainOpenVO.date)

        } else if (JsonTool.isSendBlock(objectStr)) {
            val transactionChainSendVO = GsonTool.convert(objectStr, TransactionChainSendVO::class.java)
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainSendVO.previous)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainSendVO.blockService)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainSendVO.blockType)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTXTYPE).value(transactionChainSendVO.blockTxType)
            jsonWriter.name(Constants.MONGODB_KEY_DESTINATION_WALLET).value(transactionChainSendVO.destination_wallet)
            jsonWriter.name(Constants.MONGODB_KEY_BALANCE).value(transactionChainSendVO.balance)
            jsonWriter.name(Constants.MONGODB_KEY_AMOUNT).value(transactionChainSendVO.amount)
            jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainSendVO.representative)
            jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainSendVO.wallet)
            jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainSendVO.work)
            jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainSendVO.date)

        } else if (JsonTool.isReceiveBlock(objectStr)) {
            val transactionChainReceiveVO = GsonTool.convert(objectStr, TransactionChainReceiveVO::class.java)
            //按自定义顺序输出字段信息
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainReceiveVO.previous)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainReceiveVO.blockService)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainReceiveVO.blockType)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTXTYPE).value(transactionChainReceiveVO.blockTxType)
            jsonWriter.name(Constants.MONGODB_KEY_SOURCETXHASH).value(transactionChainReceiveVO.sourceTxhash)
            jsonWriter.name(Constants.MONGODB_KEY_AMOUNT).value(transactionChainReceiveVO.amount)
            jsonWriter.name(Constants.MONGODB_KEY_RECEIVEAMOUNT).value(transactionChainReceiveVO.receiveAmount)
            jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainReceiveVO.representative)
            jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainReceiveVO.wallet)
            jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainReceiveVO.work)
            jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainReceiveVO.date)

        } else if (JsonTool.isChangeBlock(objectStr)) {
            val transactionChainChangeVO = GsonTool.convert(objectStr, TransactionChainChangeVO::class.java)
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainChangeVO.previous)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainChangeVO.blockService)
            jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainChangeVO.blockType)
            jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainChangeVO.representative)
            jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainChangeVO.wallet)
            jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainChangeVO.work)
            jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainChangeVO.date)
        }
        jsonWriter.endObject()
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): RequestJson? {
        return null
    }
}