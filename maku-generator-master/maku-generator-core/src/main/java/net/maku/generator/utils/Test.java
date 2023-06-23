package net.maku.generator.utils;


import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Node> list = new ArrayList<>();
        Node grandFather = new Node();
        grandFather.setId(1);
        grandFather.setParentId(0);
        grandFather.setNodeName("爷爷");
        list.add(grandFather);
        Node father = new Node();
        father.setId(2);
        father.setParentId(1);
        father.setNodeName("爸爸");
        list.add(father);
        Node uncle = new Node();
        uncle.setId(3);
        uncle.setParentId(1);
        uncle.setNodeName("大叔");
        list.add(uncle);
        Node uncle0 = new Node();
        uncle0.setId(4);
        uncle0.setParentId(1);
        uncle0.setNodeName("二叔");
        list.add(uncle0);
        Node me = new Node();
        me.setId(5);
        me.setParentId(2);
        me.setNodeName("我自己");
        list.add(me);
        //        ToTreeUtil<Node,Integer> util = new ToTreeUtil<>(list, Node::getId, Node::getParentId,Node::setChildren);//不使用楼数
        TreeBuilder<Node, Integer> util = new TreeBuilder<>(list, Node::getId, Node::getParentId, Node::setChildren, Node::setFloor);// 使用楼数
        List<Node> build = util.build();
        String json = JSONObject.toJSONString(build);
        System.out.println(json);
    }
}

@Setter
@Getter
class Node {
    private Integer id;
    private Integer parentId;
    private String nodeName;
    private Integer floor;
    private List<Node> children;
}
