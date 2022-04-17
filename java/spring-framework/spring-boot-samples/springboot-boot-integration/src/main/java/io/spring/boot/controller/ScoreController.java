package io.spring.boot.controller;

import java.util.List;

import io.spring.boot.common.entity.Score;
import io.spring.boot.dao.IScoreDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private IScoreDao scoreDao;

    @RequestMapping("/scoreList")
    public List<Score>

    getScoreList() {
        logger.info("从数据库读取Score集合");
        // 测试更新数据库
        logger.info("更新的行数：" + scoreDao.updateScoreById(88.8f, 2));
        try {
            // scoreDao.delete(23);
        } catch (Exception e) {
//			e.printStackTrace();
        }

        return scoreDao.getList();
    }
}
