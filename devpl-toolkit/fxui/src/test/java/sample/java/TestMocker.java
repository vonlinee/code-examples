package sample.java;

import io.devpl.fxtras.beans.Model;
import io.devpl.tookit.utils.mock.Mocker;

public class TestMocker {

    public static void main(String[] args) {

        Model model = new Model();

        Mocker.fillDefaultValue(model);

        System.out.println(model);
    }
}
