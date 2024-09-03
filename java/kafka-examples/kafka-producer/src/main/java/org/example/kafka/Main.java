package org.example.kafka;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try (Admin admin = Admin.create(properties)) {

            NewTopic topic1 = new NewTopic("test", 1, (short) 2);

            Map<Integer, List<Integer>> replicasAssignments = new HashMap<>();
            replicasAssignments.put(0, Arrays.asList(3, 1));
            replicasAssignments.put(1, Arrays.asList(2, 3));
            replicasAssignments.put(2, Arrays.asList(1, 2));
            NewTopic topic2 = new NewTopic("test", replicasAssignments);

            admin.createTopics(Arrays.asList(topic1, topic2));
        }
    }
}