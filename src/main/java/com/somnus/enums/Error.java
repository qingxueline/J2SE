package com.somnus.enums;

/***
 * 1000开头作为公共异常，如可以为系统系统，其他异常等
 */
public enum Error {
    //公共
    SUCCESS(200, "成功。"),
    SYS_EXCEPTION(1400, "系统异常"),
    SYS_OTHER_EXCEPTION(1401, "其他异常"),

    //公共业务异常（1500），对应com.small.cell.common.exception.base.BusinessException
    SYS_BUSINESS_EXCEPTION(1500, "业务异常"),
    SYS_BUSINESS_NOT_FILE_EXCEPTION(1501, "文件不存在，请对请求参数进行检查"),
    SYS_BUSINESS_FORMAT_FILE_EXCEPTION(1502, "文件格式错误，请对请求参数进行检查"),
    SYS_BUSINESS_NULL_EXCEPTION(1503, "参数为空/对象为空，请对数据进行检查"),
    SYS_BUSINESS_CREAT_EXCEPTION(1504, "创建失败"),
    SYS_BUSINESS_UPDATE_EXCEPTION(1505, "修改失败"),
    SYS_BUSINESS_DELETE_EXCEPTION(1506, "删除失败"),
    SYS_BUSINESS_ID_EXIST_EXCEPTION(1507, "ID已经存在"),
    SYS_BUSINESS_PRIMARY_KEY_CONFLICT_EXCEPTION(1508, "唯一性冲突"),

    //证书相关异常（3100），对应com.small.cell.common.exception.base.CertificateException
    CERT_EXCEPTION(3100, "证书业务错误,请联系管理员进行检查"),
    CERT_DATA_MISMATCH_EXCEPTION(3101, "证书数量(张)与实际数量不匹配，请对申请数量与具体行数进行检查"),
    CERT_STATUS_MISMATCH_EXCEPTION(3102, "证书注销原因(状态码)填写错误，请填写正确的注销原因"),

    DEVICE_EXCEPTION(2001, "设备相关异常"),
    IP_SEC_EXCEPTION(2011, "IPSec参数相关异常"),
    MANUFACTURER_EXCEPTION(2021, "厂商信息相关异常"),
    SERVER_EXCEPTION(2031, "服务信息相关异常"),
    TAC_EXCEPTION(2041, "TAC相关异常");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举说明信息
     *
     * @param code .
     * @return .
     */
    public static String getMessage(int code) {
        for (Error errorEnums : values()) {
            if (errorEnums.getCode() == code) {
                return errorEnums.getMessage();
            }
        }
        return null;
    }

}
