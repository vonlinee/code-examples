package code.example.jdbc.druid;
/**
 * 
 * @since created on 2022年8月7日
 */
public class BusinessException extends RuntimeException {

    private boolean ignored;
    private static final long serialVersionUID = 5313036101535701088L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        printStackTrace();
    }
    
    public BusinessException(Throwable cause) {
        super(cause);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
        getSuppressed();
    }
}
