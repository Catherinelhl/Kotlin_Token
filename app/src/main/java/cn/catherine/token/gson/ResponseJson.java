package cn.catherine.token.gson;
/*
+--------------+---------------------------------
+ author       +   Catherine Liu
+--------------+---------------------------------
+ since        +   2019/2/26 14:39
+--------------+---------------------------------
+ projectName  +   Kotlin_Token
+--------------+---------------------------------
+ packageName  +   cn.catherine.token.gson
+--------------+---------------------------------
+ description  +   Server 回應使用
+--------------+---------------------------------
+ version      +  
+--------------+---------------------------------
*/


import cn.catherine.token.vo.*;

import java.util.List;

public class ResponseJson extends ServerResponseJson {
    private static final long serialVersionUID = 1L;

    private VersionVO versionVO;
    private List<VersionVO> versionVOList;

    private ClientIpInfoVO clientIpInfoVO;
    private List<ClientIpInfoVO> clientIpInfoVOList;

    private WalletVO walletVO;

    private DatabaseVO databaseVO;
    private List<DatabaseVO> databaseVOList;

    private PaginationVO paginationVO;
    private List<PaginationVO> paginationVOList;

    private APIKeyVO apiKey;

    private List<SeedFullNodeVO> seedFullNodeBeanList;
    private List<PublicUnitVO> publicUnitVOList;

    private RemoteInfoVO remoteInfoVO;
    private List<RemoteInfoVO> remoteInfoVOList;


    // ==================================================================================================
    // constructors
    // ==================================================================================================

    public ResponseJson() {
        super();
    }

    public ResponseJson(boolean success, int code, String message) {
        super(success, code, message);
    }

    public ResponseJson(boolean success, int code, String message, VersionVO versionVO, List<VersionVO> versionVOList) {
        super(success, code, message);
        this.versionVO = versionVO;
        this.versionVOList = versionVOList;
    }

    public ResponseJson(ClientIpInfoVO clientIpInfoVO) {
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public ResponseJson(ClientIpInfoVO clientIpInfoVO, boolean success, int code, String message, String methodName,
                        String macAddressAndExternalIp) {
        super(success, code, message, methodName, macAddressAndExternalIp);
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public ResponseJson(ClientIpInfoVO clientIpInfoVO, boolean success, int code, String message, String methodName) {
        super(methodName);
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public ResponseJson(boolean success, int code, String message, String methodName) {
        super(success, code, message, methodName);
    }

    public ResponseJson(ClientIpInfoVO clientIpInfoVO, String methodName) {
        super(methodName);
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public ResponseJson(boolean success, int code, String message, ClientIpInfoVO clientIpInfoVO,
                        List<ClientIpInfoVO> clientIpInfoVOList) {
        super(success, code, message);
        this.clientIpInfoVO = clientIpInfoVO;
        this.clientIpInfoVOList = clientIpInfoVOList;
    }

    public ResponseJson(boolean success, int code, String message, WalletVO walletVO) {
        super(success, code, message);
        this.walletVO = walletVO;
    }

    public ResponseJson(DatabaseVO databaseVO) {
        super();
        this.databaseVO = databaseVO;
    }

    public ResponseJson(boolean success, int code, String message, String methodName, ClientIpInfoVO clientIpInfoVO) {
        super(success, code, message, methodName);
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public ResponseJson(boolean success, int code, String message, String methodName, DatabaseVO databaseVO,
                        List<DatabaseVO> databaseVOList) {
        super(success, code, message, methodName);
        this.databaseVO = databaseVO;
        this.databaseVOList = databaseVOList;
    }

    public ResponseJson(boolean success, int code, String message, String methodName, DatabaseVO databaseVO,
                        PaginationVO paginationVO, List<PaginationVO> paginationVOList) {
        super(success, code, message, methodName);
        this.databaseVO = databaseVO;
        this.paginationVO = paginationVO;
        this.paginationVOList = paginationVOList;
    }

    public ResponseJson(boolean success, int code, String message, String methodName, ClientIpInfoVO clientIpInfoVO,
                        List<ClientIpInfoVO> clientIpInfoVOList) {
        super(success, code, message, methodName);
        this.clientIpInfoVO = clientIpInfoVO;
        this.clientIpInfoVOList = clientIpInfoVOList;
    }

    public ResponseJson(boolean success, int code, String message, String methodName, RemoteInfoVO remoteInfoVO,
                        List<RemoteInfoVO> remoteInfoVOList) {
        super(success, code, message, methodName);
        this.remoteInfoVO = remoteInfoVO;
        this.remoteInfoVOList = remoteInfoVOList;
    }

    // ==================================================================================================
    // getter & setter
    // ==================================================================================================

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public VersionVO getVersionVO() {
        return versionVO;
    }

    public void setVersionVO(VersionVO versionVO) {
        this.versionVO = versionVO;
    }

    public List<VersionVO> getVersionVOList() {
        return versionVOList;
    }

    public void setVersionVOList(List<VersionVO> versionVOList) {
        this.versionVOList = versionVOList;
    }

    public ClientIpInfoVO getClientIpInfoVO() {
        return clientIpInfoVO;
    }

    public void setClientIpInfoVO(ClientIpInfoVO clientIpInfoVO) {
        this.clientIpInfoVO = clientIpInfoVO;
    }

    public List<ClientIpInfoVO> getClientIpInfoVOList() {
        return clientIpInfoVOList;
    }

    public void setClientIpInfoVOList(List<ClientIpInfoVO> clientIpInfoVOList) {
        this.clientIpInfoVOList = clientIpInfoVOList;
    }

    public WalletVO getWalletVO() {
        return walletVO;
    }

    public void setWalletVO(WalletVO walletVO) {
        this.walletVO = walletVO;
    }

    public DatabaseVO getDatabaseVO() {
        return databaseVO;
    }

    public void setDatabaseVO(DatabaseVO databaseVO) {
        this.databaseVO = databaseVO;
    }

    public List<DatabaseVO> getDatabaseVOList() {
        return databaseVOList;
    }

    public void setDatabaseVOList(List<DatabaseVO> databaseVOList) {
        this.databaseVOList = databaseVOList;
    }

    public PaginationVO getPaginationVO() {
        return paginationVO;
    }

    public void setPaginationVO(PaginationVO paginationVO) {
        this.paginationVO = paginationVO;
    }

    public List<PaginationVO> getPaginationVOList() {
        return paginationVOList;
    }

    public void setPaginationVOList(List<PaginationVO> paginationVOList) {
        this.paginationVOList = paginationVOList;
    }

    public APIKeyVO getApiKey() {
        return apiKey;
    }

    public void setApiKey(APIKeyVO apiKey) {
        this.apiKey = apiKey;
    }

    public List<SeedFullNodeVO> getSeedFullNodeBeanList() {
        return seedFullNodeBeanList;
    }

    public void setSeedFullNodeBeanList(List<SeedFullNodeVO> seedFullNodeBeanList) {
        this.seedFullNodeBeanList = seedFullNodeBeanList;
    }

    public List<PublicUnitVO> getPublicUnitVOList() {
        return publicUnitVOList;
    }

    public void setPublicUnitVOList(List<PublicUnitVO> publicUnitVOList) {
        this.publicUnitVOList = publicUnitVOList;
    }

    public RemoteInfoVO getRemoteInfoVO() {
        return remoteInfoVO;
    }

    public void setRemoteInfoVO(RemoteInfoVO remoteInfoVO) {
        this.remoteInfoVO = remoteInfoVO;
    }

    public List<RemoteInfoVO> getRemoteInfoVOList() {
        return remoteInfoVOList;
    }

    public void setRemoteInfoVOList(List<RemoteInfoVO> remoteInfoVOList) {
        this.remoteInfoVOList = remoteInfoVOList;
    }

    @Override
    public String toString() {
        return "ResponseJson{" +
                "versionVO=" + versionVO +
                ", versionVOList=" + versionVOList +
                ", clientIpInfoVO=" + clientIpInfoVO +
                ", clientIpInfoVOList=" + clientIpInfoVOList +
                ", walletVO=" + walletVO +
                ", databaseVO=" + databaseVO +
                ", databaseVOList=" + databaseVOList +
                ", paginationVO=" + paginationVO +
                ", paginationVOList=" + paginationVOList +
                ", apiKey=" + apiKey +
                ", seedFullNodeBeanList=" + seedFullNodeBeanList +
                ", publicUnitVOList=" + publicUnitVOList +
                '}';
    }
}
