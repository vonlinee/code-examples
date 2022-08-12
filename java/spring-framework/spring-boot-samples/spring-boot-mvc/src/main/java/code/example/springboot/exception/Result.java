package code.example.springboot.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private int code;
    public String msg;
    public String usrMsg;
    public Object data;

    public Result() {}

    public Result(StatusCode code, String msg, String usrMsg, Object data){
        this.code = code.getKey();
        this.msg = msg;
        this.usrMsg = usrMsg;
        this.data = data;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg, String usrMsg, Object data) {
        this.code = code;
        this.msg = msg;
        this.usrMsg = usrMsg;
        this.data = data;
    }


    public Result(List<?> list) {
        msg = "";
        data = new ArrayList();

        if(list == null)
        {
            code = StatusCode.FAILED.getKey();
            msg = StatusCode.FAILED.getName();

        }
        else if(list.size() == 0)
        {
            code = StatusCode.NO_DATA.getKey();
            msg = StatusCode.NO_DATA.getName();
            data = list;
        }
        else {
            code = StatusCode.NORMAL.getKey();
            data = list;
            msg = StatusCode.NORMAL.getName();
        }
    }

    public Result(Object o) {
        msg = "";
        if (o == null) {
            code = StatusCode.FAILED.getKey();
            msg = StatusCode.FAILED.getName();
            data = "";
        } else if(o instanceof List<?> && ((List<?>) o).size() == 0) {
            code = StatusCode.NO_DATA.getKey();
            msg = StatusCode.NO_DATA.getName();
            data = o;
        } else {
            data = o;
            code = StatusCode.NORMAL.getKey();
            msg = StatusCode.NORMAL.getName();
        }
    }

    public Result(Object o, boolean canBeNull) {
        msg = "";
        if (o == null)
        {
            if (canBeNull) {
                code = StatusCode.NO_DATA.getKey();
                msg = StatusCode.NO_DATA.getName();
            }
            else {
                code = StatusCode.FAILED.getKey();
                msg = StatusCode.FAILED.getName();
            }
            data = "";
        }
        else
        {
            data = o;
            code = StatusCode.NORMAL.getKey();
            msg = StatusCode.NORMAL.getName();
        }
    }

    public Result(int i) {
        msg = "";
        data = i;
        if(i <= 0) {
            code = StatusCode.FAILED.getKey();
            msg = StatusCode.FAILED.getName();
        } else {
            code = StatusCode.NORMAL.getKey();
            msg = StatusCode.NORMAL.getName();
        }
    }

    public Result(Exception ex) {
        // 注意不要将异常堆栈信息返回，会有安全问题
        this.code = StatusCode.FAILED.getKey();
        this.msg = ex.getMessage();
        this.data = "";
    }

    public Result(boolean res) {
        if(res) {
            this.code = StatusCode.NORMAL.getKey();
            this.msg = "正常";
            this.data = 1;
        } else{
            this.code = StatusCode.FAILED.getKey();
            this.msg = "失败";
            this.data = 0;
        }
    }

    public Result(BindingResult bindingResult) {
        this.code = StatusCode.FAILED_PARAMETER.getKey();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            sb.append(error.getDefaultMessage()).append(";");
        }
        this.msg = sb.toString();
        this.usrMsg = sb.toString();
        this.data = 0;
    }

    public int getcode() {
        return code;
    }

    public void setcode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsrMsg() {
        return usrMsg;
    }

    public void setUsrMsg(String usrMsg) {
        this.usrMsg = usrMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}