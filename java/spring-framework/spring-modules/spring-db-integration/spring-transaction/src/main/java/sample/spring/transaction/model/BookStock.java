package sample.spring.transaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name="t_book_stocks")
public class BookStock {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STOCK_ID")
	private Integer stockId;
	
	@Column(name="BOOK_ID")
	private Integer bookId;
	
	@Column(name="BOOK_STOCK")
	private Integer bookStock;
}
