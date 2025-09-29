import os
import xml.etree.ElementTree as ET


def config_hive_site(config_xml_path, config_props: dict[str, str] = None, output=None):
    if output is None:
        output = config_xml_path
    # 如果配置文件不存在，则创建新的
    if not os.path.exists(config_xml_path):
        configuration = ET.Element("configuration")
        tree = ET.ElementTree(configuration)
        tree.write(output, encoding='utf-8', xml_declaration=True)
    if config_props is None:
        return
    # 解析现有配置文件
    parser = ET.XMLParser(target=ET.TreeBuilder(insert_comments=True))
    tree = ET.parse(config_xml_path, parser)
    root = tree.getroot()
    # 添加新的配置项
    for prop_name, prop_value in config_props.items():
        # 检查是否已存在相同的配置项
        config_props = root.findall(f".//property[name='{prop_name}']")
        if config_props:
            value_node = config_props[0].findall('value')[0]
            print(f"updated: {prop_name}: {value_node} -> {prop_value}")
            value_node.text = prop_value
        else:
            # 如果配置项不存在，创建新的
            property_el = ET.SubElement(root, "property")
            name_el = ET.SubElement(property_el, "name")
            name_el.text = prop_name
            value_el = ET.SubElement(property_el, "value")
            value_el.text = prop_value
            print(f"added: {prop_name}: {prop_value}")

    ET.indent(tree, space=" ", level=0)
    tree.write(output, encoding='utf-8', xml_declaration=True, method="xml")


if __name__ == '__main__':
    # 替换为具体的 Hadoop 和 Hive 路径
    HADOOP_HOME = os.getenv('HADOOP_HOME', '/opt/hadoop')
    if HADOOP_HOME is None:
        print("HADOOP_HOME is not set")
        exit(1)
    HIVE_HOME = os.getenv('HIVE_HOME')
    if HIVE_HOME is None:
        print("HIVE_HOME is not set")
        exit(1)

    hive_config_xml = os.path.join(HIVE_HOME, "conf", "hive-site.xml")
    print("Update Hive config file: hive-site.xml: ", hive_config_xml)
    props = {
        "javax.jdo.option.ConnectionURL": "jdbc:mysql://localhost:3306/hive?characterEncoding=UTF-8&amp;serverTimezone=UTC",
        "javax.jdo.option.ConnectionDriverName": "com.mysql.cj.jdbc.Driver",
        "hive.metastore.schema.verification": "false",
        "datanucleus.schema.autoCreateAll": "true",
        "hive.metastore.warehouse.dir": "/user/hive/warehouse",
        "hive.exec.scratchdir": f'{HIVE_HOME}/data/scratchDir',
        "hive.querylog.location": f'{HIVE_HOME}/data/querylogDir',
    }
    config_hive_site(hive_config_xml, props)

    os.makedirs(props["hive.exec.scratchdir"], exist_ok=True)
    os.makedirs(props["hive.querylog.location"], exist_ok=True)
    os.makedirs(os.path.join(HIVE_HOME, 'data', 'resourcesDir'), exist_ok=True)
    os.makedirs(os.path.join(HIVE_HOME, 'data', 'operationDir'), exist_ok=True)
