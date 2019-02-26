package cn.catherine.token.gson.gsonTypeAdapter

import cn.catherine.token.constant.Constants
import cn.catherine.token.tool.json.GsonTool
import cn.catherine.token.tool.json.JsonTool
import cn.catherine.token.vo.*
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 15:45
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.gson.gsonTypeAdapter
+--------------+---------------------------------
+ description  +  
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/

class TransactionChainVOTypeAdapter : TypeAdapter<TransactionChainVO<*>>() {

    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, transactionChainVO: TransactionChainVO<*>?) {
        jsonWriter.beginObject()
        if (transactionChainVO == null) {
            return
        }
        jsonWriter.name(Constants.MONGODB_KEY_ID).value(transactionChainVO._id)
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

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): TransactionChainVO<*>? {
        return null
    }

    @Throws(IOException::class)
    fun writeTC(jsonWriter: JsonWriter, tc: Any?) {
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
}