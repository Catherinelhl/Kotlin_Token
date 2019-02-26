package cn.catherine.token.gson.gsonTypeAdapter;

import cn.catherine.token.constant.Constants;
import cn.catherine.token.vo.TransactionChainReceiveVO;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 接收R区块的TC
 *
 * 自定義Request/Response數據傳輸交互字段，作Gson輔助類：TransactionChainReceiveVO
 */

public class TransactionChainReceiveVOTypeAdapter extends TypeAdapter<TransactionChainReceiveVO> {

    @Override
    public void write(JsonWriter jsonWriter, TransactionChainReceiveVO transactionChainReceiveVO) throws IOException {
        jsonWriter.beginObject();
        if (transactionChainReceiveVO == null) {
            return;
        }
        //按自定义顺序输出字段信息
        jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(transactionChainReceiveVO.getPrevious());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(transactionChainReceiveVO.getBlockService());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKTYPE).value(transactionChainReceiveVO.getBlockType());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKTXTYPE).value(transactionChainReceiveVO.getBlockTxType());
        jsonWriter.name(Constants.MONGODB_KEY_SOURCETXHASH).value(transactionChainReceiveVO.getSourceTxhash());
        jsonWriter.name(Constants.MONGODB_KEY_AMOUNT).value(transactionChainReceiveVO.getAmount());
        jsonWriter.name(Constants.MONGODB_KEY_RECEIVEAMOUNT).value(transactionChainReceiveVO.getReceiveAmount());
        jsonWriter.name(Constants.MONGODB_KEY_REPRESENTATIVE).value(transactionChainReceiveVO.getRepresentative());
        jsonWriter.name(Constants.MONGODB_KEY_WALLET).value(transactionChainReceiveVO.getWallet());
        jsonWriter.name(Constants.MONGODB_KEY_WORK).value(transactionChainReceiveVO.getWork());
        jsonWriter.name(Constants.MONGODB_KEY_DATE).value(transactionChainReceiveVO.getDate());
        jsonWriter.endObject();
    }

    @Override
    public TransactionChainReceiveVO read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
