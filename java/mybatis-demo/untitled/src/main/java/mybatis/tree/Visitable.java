package mybatis.tree;

public interface Visitable<T> {
 
    void accept(Visitor<T> visitor);
}
