package com.example.demo;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main1 extends Application {

    static class Model {
        StringProperty username = new SimpleStringProperty();
        StringProperty password = new SimpleStringProperty();

        public String getUsername() {
            return username.get();
        }

        public StringProperty usernameProperty() {
            return username;
        }

        public void setUsername(String username) {
            this.username.set(username);
        }

        public String getPassword() {
            return password.get();
        }

        public StringProperty passwordProperty() {
            return password;
        }

        public void setPassword(String password) {
            this.password.set(password);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Model model = new Model();

        Form loginForm = Form.of(
                Group.of(
                        Field.ofStringType(model.usernameProperty())
                                .label("Username"),
                        Field.ofStringType(model.passwordProperty())
                                .label("Password")
                                .required("This field can’t be empty")
                )
        ).title("Login");

        FormRenderer fr = new FormRenderer(loginForm);

        final Scene scene = new Scene(fr, 600, 400);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
