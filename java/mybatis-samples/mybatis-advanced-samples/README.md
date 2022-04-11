





|             |                    |
| ----------- | ------------------ |
| datetime(6) | java.sql.Timestamp |
|             |                    |
|             |                    |





MetaObject是Mybatis提供的一个用于方便、优雅访问对象属性的对象，通过它可以简化代码、不需要try/catch各种reflect异常，同时它支持对JavaBean、Collection、Map三种类型对象的操作

MetaObject对Collection、Map的支持并不完全，某些操作会直接抛出UnsupportedOperationException，需要注意











