package code.sample.seata.service;

public interface StorageService {
    boolean updateUseNum(long productId, long used);
}
