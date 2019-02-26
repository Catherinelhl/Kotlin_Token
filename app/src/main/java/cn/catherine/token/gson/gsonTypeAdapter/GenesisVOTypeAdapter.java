package cn.catherine.token.gson.gsonTypeAdapter;

import cn.catherine.token.constant.Constants;
import cn.catherine.token.vo.GenesisVO;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * 自定義Request/Response數據傳輸交互字段，作Gson輔助類：GenesisVO
 */
public class GenesisVOTypeAdapter extends TypeAdapter<GenesisVO> {

    @Override
    public void write(JsonWriter jsonWriter, GenesisVO genesisVO) throws IOException {
        jsonWriter.beginObject();
        //按自定义顺序输出字段信息
        if (genesisVO == null) {
            return;
        }
        jsonWriter.name(Constants.MONGODB_KEY_ID).value(genesisVO.get_id());
        jsonWriter.name(Constants.MONGODB_KEY_PREVIOUS).value(genesisVO.getPrevious());
        jsonWriter.name(Constants.MONGODB_KEY_PUBLICUNIT).value(genesisVO.getPublicUnit());
        jsonWriter.name(Constants.MONGODB_KEY_BLOCKSERVICE).value(genesisVO.getBlockService());
        jsonWriter.name(Constants.MONGODB_KEY_CURRENCYUNIT).value(genesisVO.getCurrencyUnit());
        jsonWriter.name(Constants.MONGODB_KEY_CIRCULATION).value(genesisVO.getCirculation());
        jsonWriter.name(Constants.MONGODB_KEY_COINBASE).value(genesisVO.getCoinBase());
        jsonWriter.name(Constants.MONGODB_KEY_GENEISISBLOCKACCOUNT).value(genesisVO.getGenesisBlockAccount());
        jsonWriter.name(Constants.MONGODB_KEY_COINBASEACCOUNT).value(genesisVO.getCoinBaseAccount());
        jsonWriter.name(Constants.MONGODB_KEY_INTERESTRATE).value(genesisVO.getInterestRate());
        jsonWriter.name(Constants.MONGODB_KEY_WORK).value(genesisVO.getWork());
        jsonWriter.name(Constants.MONGODB_KEY_CREATETIME).value(genesisVO.getCreateTime());
        jsonWriter.name(Constants.MONGODB_KEY_SYSTEMTIME).value(genesisVO.getSystemTime());
        jsonWriter.endObject();
    }

    @Override
    public GenesisVO read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
