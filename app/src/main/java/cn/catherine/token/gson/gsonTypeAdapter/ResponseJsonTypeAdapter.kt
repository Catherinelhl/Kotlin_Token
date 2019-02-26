package cn.catherine.token.gson.gsonTypeAdapter

import cn.catherine.token.constant.Constants
import cn.catherine.token.gson.ResponseJson
import cn.catherine.token.tool.StringTool
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
+ description  +   自定義Request/Response數據傳輸交互字段，作Gson輔助類：ResponseJson
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class ResponseJsonTypeAdapter : TypeAdapter<ResponseJson>(){
    // ResponseJson 順序
    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, requestJson: ResponseJson?) {

        jsonWriter.beginObject()
        if (requestJson == null) {
            return
        }
        jsonWriter.name(Constants.GSON_KEY_SUCCESS).value(requestJson.isSuccess)
        jsonWriter.name(Constants.GSON_KEY_CODE).value(requestJson.code.toLong())
        jsonWriter.name(Constants.GSON_KEY_MESSAGE).value(requestJson.message)
        if (StringTool.notEmpty(requestJson.methodName)) {
            jsonWriter.name(Constants.GSON_KEY_METHODNAME).value(requestJson.methodName)
        }
        if (requestJson.walletVO != null) {
            jsonWriter.name(Constants.GSON_KEY_WALLETVO)
            writeWalletVO(jsonWriter, requestJson.walletVO)
        }

        if (requestJson.databaseVO != null) {
            jsonWriter.name(Constants.GSON_KEY_DATABASEVO)
            writeDatabaseVO(jsonWriter, requestJson.databaseVO)
        }

        if (requestJson.paginationVOList != null && requestJson.paginationVOList[0].objectList.size > 0) {
            jsonWriter.name(Constants.GSON_KEY_PAGINATIONVOLIST)
            writePaginationVOList(jsonWriter, requestJson.paginationVOList)
        }

        jsonWriter.endObject()
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

        } else if (databaseVO.transactionChainVO != null) {

            jsonWriter.name(Constants.GSON_KEY_TRANSACTIONCHAINVO)
            writeTransactionChainVO(jsonWriter, databaseVO.transactionChainVO)

        } else if (databaseVO.genesisVO != null) {

            jsonWriter.name(Constants.GSON_KEY_GENESISVO)
            writeGenesisVO(jsonWriter, databaseVO.genesisVO)

        }
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
        jsonWriter.name(Constants.GSON_KEY_TC)
        writeTC(jsonWriter, transactionChainVO.tc)
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
        if (tc is TransactionChainReceiveVO) {
            // Receive Block
            val transactionChainReceiveVO = tc as TransactionChainReceiveVO?
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainReceiveVO!!.previous)
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
        } else if (tc is TransactionChainSendVO) {
            // Send Block
            val transactionChainSendVO = tc as TransactionChainSendVO?
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainSendVO!!.previous)
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
        } else if (tc is TransactionChainOpenVO) { // Open Block
            val transactionChainOpenVO = tc as TransactionChainOpenVO?
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainOpenVO!!.previous)
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
        } else if (tc is TransactionChainChangeVO) { // Change Block
            val transactionChainChangeVO = tc as TransactionChainChangeVO?
            jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainChangeVO!!.previous)
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
    private fun writePaginationVOList(jsonWriter: JsonWriter, paginationVOList: List<PaginationVO>?) {
        jsonWriter.beginArray()
        val gson = Gson()
        if (paginationVOList == null) {
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

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): ResponseJson {
        return GsonTool.convert(jsonReader.toString(), ResponseJson::class.java)

    }
}