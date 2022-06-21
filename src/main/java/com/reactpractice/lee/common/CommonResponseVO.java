package com.reactpractice.lee.common;

public class CommonResponseVO {

    private CommonHeaderVO header;
    private Object body;

    public CommonHeaderVO getHeader() {
        return header;
    }

    public void setHeader(CommonHeaderVO header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public static CommonResponseVO defaultResponseVO () {
        CommonHeaderVO header = new CommonHeaderVO();
        header.setCode("0000");
        header.setMsg("정상 처리되었습니다.");
        CommonResponseVO response = new CommonResponseVO();
        response.setHeader(header);
        return response;
    }

    public static CommonResponseVO defaultResponseVO(Object body){
        CommonResponseVO responseVO = defaultResponseVO();
        responseVO.setBody(body);
        return responseVO;
    }
}
