package multidatasource.controller;

import multidatasource.utils.SpringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class SystemController {

    @GetMapping("/getbean")
    @ResponseBody
    public String getBean() {
        DataSource dataSource = SpringUtils.getBean(DataSource.class);
        return dataSource.toString();
    }
}
