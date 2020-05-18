package com.somnus.security.certificate;

import java.util.Date;

/**
 * 秘钥库详情
 *
 * @author lyl
 * @version 2020/3/19 0019 13:21:32
 */
public class KeyStoreInfo {
    /**证书别名**/
    private String alias;
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
    /**证书有效期开始日期/时间**/
    private Date start;
    /**证书有效期天数**/
    private long validityDays;
    /**生成证书地址**/
    private String pathAndFileName;
    /**秘钥库密码，保护秘钥库，安装证书时用于验证**/
    private String keyStorePass;
    /**证书密码,用于恢复秘钥**/
    private String certPass;

    public KeyStoreInfo(String alias, String keyStorePass, String certPass,
                        String cN, String oU, String o, String l, String sT, String c,
                        Date start, long validityDays, String pathAndFileName) {
        this.alias = alias;
        this.keyStorePass = keyStorePass;
        this.certPass = certPass;
        this.CN = cN;
        this.OU = oU;
        this.O = o;
        this.L = l;
        this.ST = sT;
        this.C = c;
        this.start = start;
        this.validityDays = validityDays;
        this.pathAndFileName = pathAndFileName;
    }

    public KeyStoreInfo() {
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeyStorePass() {
        return keyStorePass;
    }

    public void setKeyStorePass(String keyStorePass) {
        this.keyStorePass = keyStorePass;
    }

    public String getCertPass() {
        return certPass;
    }

    public void setCertPass(String certPass) {
        this.certPass = certPass;
    }

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public long getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(long validityDays) {
        this.validityDays = validityDays;
    }

    public String getPathAndFileName() {
        return pathAndFileName;
    }

    public void setPathAndFileName(String pathAndFileName) {
        this.pathAndFileName = pathAndFileName;
    }

    @Override
    public String toString() {
        return "X509CertInfo [alias=" + alias + ", keyStorePass="
                + keyStorePass + ", certPass=" + certPass + ", CN=" + CN
                + ", OU=" + OU + ", O=" + O + ", L=" + L + ", ST=" + ST
                + ", C=" + C + ", start=" + start + ", validityDays="
                + validityDays + ", pathAndFileName=" + pathAndFileName + "]";
    }

}