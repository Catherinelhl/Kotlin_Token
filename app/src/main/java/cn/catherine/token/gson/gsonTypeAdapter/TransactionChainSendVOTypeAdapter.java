package cn.catherine.token.gson.gsonTypeAdapter;

import cn.catherine.token.constant.Constants;
import cn.catherine.token.vo.TransactionChainSendVO;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * send 区块 TC
 * 对发送数据进行正序
 *
 * 自定義Request/Response數據傳輸交互字段，作Gson輔助類：TransactionChainSendVO
 */
public class TransactionChainSendVOTypeAdapter extends TypeAdapter<TransactionChainSendVO> {

    @Override
    public void write(JsonWriter jsonWriter, TransactionChainSendVO transactionChainSendVO) throws IOException {
        jsonWriter.beginObject();
        if (transactionChainSendVO == null) {
            return;
        }
        //按自定义顺序输出字段信息
        jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainSendVO.getPrevious());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainSendVO.getBlockService());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainSendVO.getBlockType());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKTXTYPE).value(transactionChainSendVO.getBlockTxType());
        jsonWriter.name(Constants.MONGODB_KEY_DESTINATION_WALLET).value(transactionChainSendVO.getDestination_wallet());
        jsonWriter.name(Constants.MONGODB_KEY_BALANCE).value(transactionChainSendVO.getBalance());
        jsonWriter.name(Constants.MONGODB_KEY_AMOUNT).value(transactionChainSendVO.getAmount());
        jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainSendVO.getRepresentative());
        jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainSendVO.getWallet());
        jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainSendVO.getWork());
        jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainSendVO.getDate());
        jsonWriter.endObject();
    }


    @Override
    public TransactionChainSendVO read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
