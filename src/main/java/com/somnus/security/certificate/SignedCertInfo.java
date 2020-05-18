package com.somnus.security.certificate;

/**
 * 证书签署详情信息
 *
 * @author lyl
 * @version 2020/3/19 0019 13:22:29
 */
public class SignedCertInfo {
    /**证书拥有者通用名**/
    private String CN;
    /**组织单位名称**/
    private String OU;
    /**组织名称**/
    private String O;
    /**城市或区域名称**/
    private String L;
    /**州或省份名称**/
    private String ST;
    /**国家名称**/
    private String C;
    /**数字证书别名**/
    private String subjectAlias;
    /**数字证书别名**/
    private String subjectAliasPass;
    /**有效天数**/
    private int validity;
    /**数字证书路径**/
    private String subjectPath;

    /**密钥库路径（证书发行方）**/
    private String keyStorePath;
    /**恢复密钥的密码（证书发行方）**/
    private String keyStorePass;
    /**证书别名（证书发行方）**/
    private String issuerAlias;
    /**证书密码,用于恢复秘钥（证书发行方）**/
    private String issuerAliasPass;


    public String getCN() {
        return CN;
    }
    public void setCN(String cN) {
        CN = cN;
    }
    public String getOU() {
        return OU;
    }
    public void setOU(String oU) {
        OU = oU;
    }
    public String getO() {
        return O;
    }
    public void setO(String o) {
        O = o;
    }
    public String getL() {
        return L;
    }
    public void setL(String l) {
        L = l;
    }
    public String getST() {
        return ST;
    }
    public void setST(String sT) {
        ST = sT;
    }
    public String getC() {
        return C;
    }
    public void setC(String c) {
        C = c;
    }
    public String getKeyStorePath() {
        return keyStorePath;
    }
    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }
    public String getKeyStorePass() {
        return keyStorePass;
    }
    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }
    public String getIssuerAlias() {
        return issuerAlias;
    }
    public void setIssuerAlias(String issuerAlias) {
        this.issuerAlias = issuerAlias;
    }
    public String getIssuerAliasPass() {
        return issuerAliasPass;
    }
    public void setIssuerAliasPass(String issuerAliasPass) {
        this.issuerAliasPass = issuerAliasPass;
    }
    public String getSubjectAlias() {
        return subjectAlias;
    }
    public void setSubjectAlias(String subjectAlias) {
        this.subjectAlias = subjectAlias;
    }
    public String getSubjectAliasPass() {
        return subjectAliasPass;
    }
    public void setSubjectAliasPass(String subjectAliasPass) {
        this.subjectAliasPass = subjectAliasPass;
    }
    public int getValidity() {
        return validity;
    }
    public void setValidity(int validity) {
        this.validity = validity;
    }
    public String getSubjectPath() {
        return subjectPath;
    }
    public void setSubjectPath(String subjectPath) {
        this.subjectPath = subjectPath;
    }
    @Override
    public String toString() {
        return "SignedCertInfo [CN=" + CN + ", OU=" + OU + ", O=" + O + ", L="
                + L + ", ST=" + ST + ", C=" + C + ", keyStorePath="
                + keyStorePath + ", keyStorePass=" + keyStorePass
                + ", issuerAlias=" + issuerAlias + ", issuerAliasPass="
                + issuerAliasPass + ", subjectAlias=" + subjectAlias
                + ", subjectAliasPass=" + subjectAliasPass + ", validity="
                + validity + ", subjectPath=" + subjectPath + "]";
    }




}