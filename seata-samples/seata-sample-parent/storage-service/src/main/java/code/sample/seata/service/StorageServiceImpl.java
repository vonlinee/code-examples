package code.sample.seata.service;

import code.sample.seata.mapper.StorageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Override
    public boolean updateUseNum(long productId, long used) {
        int i = 100 / 0;
        int index = storageMapper.updateUsed(productId, used);
        return index > 0;
    }
}
