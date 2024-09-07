package org.example;

public class JHTool {

    static final String table = """
            预支单号	preDocumentNo
            预支金额	preAmount
            上次剩余金额	preLeftAmount
            本次归还金额	returnAmount
            """;

    public static void main(String[] args) {

        String[] split = table.split("\n");

        StringBuilder sb = new StringBuilder();

        for (String s : split) {
            String[] split1 = s.split("\t");

            sb.append("@ApiModelProperty(value = \"").append(split1[0]).append("\")\n\t");

            sb.append("private String ").append(split1[1]).append(";\t");
        }

        System.out.println(sb);
    }
}
