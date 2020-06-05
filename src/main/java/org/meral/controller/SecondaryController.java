package org.meral.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.meral.App;
import org.meral.model.User;
import org.meral.repository.impl.UserRepositoryHibernateImpl;
import org.meral.service.UserService;
import org.meral.service.impl.UserServiceImpl;
import org.meral.util.HibernateUtil;
import org.meral.util.Localization;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.meral.constant.Constant.Stages.PRIMARY_STAGE_NAME;

public class SecondaryController implements Initializable {

    private Localization localization;

    private UserService userService;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> sernameColumn;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot(PRIMARY_STAGE_NAME, localization.getCurrentLang());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl(new UserRepositoryHibernateImpl());

        localization = new Localization(resources);

        userTable.setItems((ObservableList<User>) userService.findAll());

        nameColumn.setCellValueFactory(data ->
                data.getValue().nameProperty());
        sernameColumn.setCellValueFactory(data ->
                data.getValue().sernameProperty());
    }

    @FXML
    private void returnButtonHandler() {
        HibernateUtil.shutdown();
    }
}