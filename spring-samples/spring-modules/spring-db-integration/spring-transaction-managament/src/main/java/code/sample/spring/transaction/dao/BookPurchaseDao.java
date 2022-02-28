package code.sample.spring.transaction.dao;

public interface BookPurchaseDao {
	void bookPurchase(int bookId, int userId, String userPass) throws Exception;
}
