import org.springframework.beans.BeanUtils;

public class TestBeanUtils {

    public static void main(String[] args) {


        TestBeanUtils bean = BeanUtils.instantiateClass(TestBeanUtils.class);

        System.out.println(bean);
    }

}
