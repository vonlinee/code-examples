import xml.etree.ElementTree as ET
import os


def append_hadoop_config(config_xml_path, props: dict[str, str]):
    """
    追加配置
    :param config_xml_path: 配置文件地址，绝对路径
    :param props: 配置值
    :return:
    """
    tree = ET.parse(config_xml_path)
    # configuration节点
    root = tree.getroot()

    prop_els = []
    for prop in props:
        property_el = ET.SubElement(root, "property")
        name_el = ET.SubElement(property_el, "name")
        name_el.text = prop
        value_el = ET.SubElement(property_el, "value")
        value_el.text = props[prop]
        prop_els.append(property_el)

    for prop_el in prop_els:
        root.append(prop_el)
    # 将修改后的 XML 写回文件
    tree.write(config_xml_path, encoding='utf-8', xml_declaration=True)


if __name__ == '__main__':
    # hadoop安装目录
    HADOOP_HOME = os.getenv('HADOOP_HOME')

    append_hadoop_config(HADOOP_HOME + '/etc/hadoop/core-site.xml', {
        'fs.defaultFS': 'hdfs://localhost:9000',
        'fs.default.name': 'hdfs://0.0.0.0:19000'
    })
