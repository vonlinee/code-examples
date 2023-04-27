package mybatis;

import com.alibaba.druid.sql.SQLUtils;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.ibatis.mapping.BoundSql;

import java.util.HashMap;
import java.util.Map;

public class MainApplication extends Application {

    ParseResult result;

    @Override
    public void start(Stage primaryStage) throws Exception {

        SplitPane splitPane = new SplitPane();

        VBox vBox = new VBox();
        splitPane.getItems().add(vBox);

        TextField textField = new TextField();
        Button btn = new Button("Evaluate");
        Button btn1 = new Button("SQL");

        TextArea textArea = new TextArea();

        textArea.setText("  <select id=\"listCloudServiceStatus\" resultType=\"com.lancoo.cloudresource.domain.vo.CloudServiceStatusVO\">\n" +
                "    SELECT * FROM (\n" +
                "    SELECT resource_base.*,\n" +
                "    acc.provider, acc.account,\n" +
                "    DATEDIFF(resource_base.expire_time, now()) as left_expire_days\n" +
                "    FROM resource_base\n" +
                "    LEFT JOIN account_info AS acc ON resource_base.account_id = acc.id\n" +
                "    LEFT JOIN project_product_resource ppr ON resource_base.id = ppr.resource_id\n" +
                "    LEFT JOIN product prod ON prod.id = ppr.product_id\n" +
                "    LEFT JOIN project p ON p.id = ppr.project_id\n" +
                "    WHERE 1 = 1\n" +
                "    <if test=\"param.resType != null and param.resType != ''\">\n" +
                "      AND resource_base.table_name = #{param.resType}\n" +
                "    </if>\n" +
                "    <if test=\"param.resBelongType != null\">\n" +
                "      AND resource_base.belong_type = #{param.resBelongType}\n" +
                "    </if>\n" +
                "    <if test=\"param.provider != null and param.provider != ''\">\n" +
                "      AND INSTR(acc.provider, #{param.provider}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.productDutyPerson != null and param.productDutyPerson != ''\">\n" +
                "      AND INSTR(prod.owner_name, #{param.productDutyPerson}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.expireTimeStart != null\">\n" +
                "      AND resource_base.expire_time <![CDATA[>=]]> #{param.expireTimeStart}\n" +
                "    </if>\n" +
                "    <if test=\"param.expireTimeEnd != null\">\n" +
                "      AND resource_base.expire_time <![CDATA[<=]]> #{param.expireTimeEnd}\n" +
                "    </if>\n" +
                "    <if test=\"param.contractUsername != null and param.contractUsername != ''\">\n" +
                "      AND INSTR(resource_base.contract_username, #{param.contractUsername}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.contractSerialNumber != null and param.contractSerialNumber != ''\">\n" +
                "      AND INSTR(resource_base.contract_serial_number, #{param.contractSerialNumber}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.startTimeStart != null\">\n" +
                "      AND resource_base.start_time <![CDATA[>=]]> #{param.startTimeStart}\n" +
                "    </if>\n" +
                "    <if test=\"param.startTimeEnd != null\">\n" +
                "      AND resource_base.start_time <![CDATA[<=]]> #{param.startTimeEnd}\n" +
                "    </if>\n" +
                "    <if test=\"param.resName != null and param.resName != ''\">\n" +
                "      AND INSTR(resource_base.resource_name, #{param.resName}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    ) A\n" +
                "    <where>\n" +
                "      <if test=\"param.leftExpireDays != null\">\n" +
                "        AND A.left_expire_days <![CDATA[<=]]> #{param.leftExpireDays}\n" +
                "      </if>\n" +
                "    </where>\n" +
                "    ORDER BY\n" +
                "    <choose>\n" +
                "      <when test=\"param.startTimeOrder != null and param.startTimeOrder == 'asc'\">\n" +
                "        A.start_time ASC,\n" +
                "      </when>\n" +
                "      <otherwise>A.start_time DESC,</otherwise>\n" +
                "    </choose>\n" +
                "    <choose>\n" +
                "      <when test=\"param.expireTimeOrder != null and param.expireTimeOrder == 'asc'\">\n" +
                "        A.expire_time ASC\n" +
                "      </when>\n" +
                "      <otherwise>A.expire_time DESC</otherwise>\n" +
                "    </choose>\n" +
                "  </select>");


        MapperStatementTable table = new MapperStatementTable();

        btn.setOnAction(event -> {
            String text = textField.getText();
            if (text != null && text.length() != 0) {
                Object value = LiteralValue.getValue(text);
                String sb = value.getClass() + "\n" + value;
                textArea.setText(sb);
            }
            result = MyBatisUtils.parseXml(textArea.getText());
            table.clear();
            table.addItems(result.getRoot());
        });

        HBox hBox = new HBox(textField, btn, btn1);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(textArea);

        textArea.prefHeightProperty().bind(vBox.heightProperty().subtract(hBox.heightProperty()));

        TextArea sqlTextArea = new TextArea();

        VBox vBox1 = new VBox(table, sqlTextArea);

        sqlTextArea.prefHeightProperty().bind(vBox1.heightProperty().subtract(table.heightProperty()));

        splitPane.getItems().add(vBox1);

        btn1.setOnAction(event -> {
            TreeItem<NamedValue> root = table.getRoot();
            Map<String, Object> parameterObject = new HashMap<>();
            // 根节点不展示
            for (TreeItem<NamedValue> child : root.getChildren()) {
                fill(child, parameterObject);
            }
            BoundSql boundSql = result.getMappedStatement().getBoundSql(parameterObject);
            sqlTextArea.setText(SQLUtils.formatMySql(boundSql.getSql()));
        });

        final Scene scene = new Scene(splitPane, 500, 400);//创建场景并加载节点容器组

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fill(TreeItem<NamedValue> parent, Map<String, Object> map) {
        ObservableList<TreeItem<NamedValue>> children = parent.getChildren();
        NamedValue value = parent.getValue();
        if (!children.isEmpty()) {
            String key = value.getName();
            Map<String, Object> valueMap;
            if (map.containsKey(key)) {
                valueMap = (Map<String, Object>) map.get(key);
            } else {
                valueMap = new HashMap<>();
                map.put(key, valueMap);
            }
            for (TreeItem<NamedValue> child : children) {
                fill(child, valueMap);
            }
        } else {
            map.put(value.getName(), value.getValue());
        }
    }
}
