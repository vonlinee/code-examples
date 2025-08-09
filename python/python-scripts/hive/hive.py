import xml.etree.ElementTree as ET
import os


class CommentedTreeBuilder(ET.TreeBuilder):
    def comment(self, data):
        self.start(ET.Comment, {})
        self.data(data)
        self.end(ET.Comment)


def config_hive_site(config_xml_path, props: dict[str, str]):
    # 如果配置文件不存在，则创建新的
    if not os.path.exists(config_xml_path):
        # 创建configuration根节点
        configuration = ET.Element("configuration")
        tree = ET.ElementTree(configuration)
        tree.write(config_xml_path, encoding='utf-8', xml_declaration=True)

    # 解析现有配置文件
    parser = ET.XMLParser(target=CommentedTreeBuilder())
    tree = ET.parse(config_xml_path, parser)
    root = tree.getroot()

    # 添加新的配置项
    for prop_name, prop_value in props.items():
        # 检查是否已存在相同的配置项
        existing_property = None
        for prop in root.findall('property'):
            name_el = prop.find('name')
            if name_el is not None and name_el.text == prop_name:
                existing_property = prop
                break

        if existing_property is not None:
            # 如果配置项已存在，更新其值
            value_el = existing_property.find('value')
            if value_el is not None:
                value_el.text = prop_value
        else:
            # 如果配置项不存在，创建新的
            property_el = ET.SubElement(root, "property")
            name_el = ET.SubElement(property_el, "name")
            name_el.text = prop_name
            value_el = ET.SubElement(property_el, "value")
            value_el.text = prop_value

    # 将修改后的 XML 写回文件
    tree.write(config_xml_path, encoding='utf-8', xml_declaration=True)
    print(f"已更新配置文件: {config_xml_path}")


if __name__ == '__main__':
    # 替换为具体的 Hadoop 和 Hive 路径
    # HADOOP_HOME = os.getenv('HADOOP_HOME', '/opt/hadoop')
    HIVE_HOME = os.getenv('HIVE_HOME', '/opt/hive')
    hive_config_xml = os.path.join(HIVE_HOME, "conf", "hive-site.xml")

    hive_config_xml = "./hive.py"

    exists = os.path.exists(hive_config_xml)

    # print( exists)

    hive_site_config = {
        "javax.jdo.option.ConnectionURL": "jdbc:mysql://localhost:3306/hive?characterEncoding=UTF-8&amp;serverTimezone=UTC",
        "javax.jdo.option.ConnectionDriverName": "com.mysql.cj.jdbc.Driver",
        "hive.metastore.schema.verification": "false",
        "datanucleus.schema.autoCreateAll": "true",
        "hive.metastore.warehouse.dir": "/user/hive/warehouse",
        "hive.exec.scratchdir": "/tmp/hive",
        "hive.querylog.location": "/tmp/hive",
    }
    config_hive_site(hive_config_xml, hive_site_config)
