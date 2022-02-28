package code.sample.spring.transaction.programatically.v3.dao;

public interface BookPurchaseDao {
	public void bookPurchase(int bookId, int userId, String userPass);
}
