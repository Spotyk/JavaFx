package org.meral.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import org.meral.App;
import org.meral.entity.UserEntity;
import org.meral.repository.impl.UserRepositoryHibernateImpl;
import org.meral.service.UserService;
import org.meral.service.impl.UserServiceImpl;
import org.meral.util.Localization;
import org.meral.util.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.meral.constant.Constant.Files.BUNDLE_NAME;
import static org.meral.constant.Constant.Files.LANG_FILE_NAME;
import static org.meral.constant.Constant.Files.LANG_FILE_STARTS_WITH;
import static org.meral.constant.Constant.Languages.EN_LANGUAGE_NAME;
import static org.meral.constant.Constant.Languages.ERROR_LANGUAGE_KEY;
import static org.meral.constant.Constant.Languages.NAME_LANGUAGE_KEY;
import static org.meral.constant.Constant.Languages.OK_LANGUAGE_KEY;
import static org.meral.constant.Constant.Languages.RU_LANGUAGE_NAME;
import static org.meral.constant.Constant.Languages.SERNAME_LANGUAGE_KEY;
import static org.meral.constant.Constant.LoggerConstants.CANT_CREATE_FILE;
import static org.meral.constant.Constant.LoggerConstants.CANT_WRITE_IN_FILE;
import static org.meral.constant.Constant.Stages.SECONDARY_STAGE_NAME;

public class PrimaryController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(App.class);

    private Localization localization;

    private UserService userService;

    private String currentLang;

    @FXML
    private ComboBox<String> lang;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sernameLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField sernameField;

    @FXML
    private Button okButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void switchToSecondary() throws IOException {
        Validator validator = new Validator();
        String name = nameField.getText();
        String sername = sernameField.getText();

        if (!(validator.isValidString(name) && validator.isValidString(sername))) {
            errorLabel.setVisible(true);
            return;
        }
        userService.addUser(new UserEntity(name, sername));
        App.setRoot(SECONDARY_STAGE_NAME, currentLang);
    }

    @FXML
    private void changeLang() {
        currentLang = localization.getKeyByString(lang.getSelectionModel().getSelectedItem());

        ResourceBundle bundle = ResourceBundle
                .getBundle(BUNDLE_NAME, Locale.forLanguageTag(currentLang));

        File file = new File(LANG_FILE_NAME);
        deleteIfExists(file);
        createNewFile(file);
        writeTextInFile(file, LANG_FILE_STARTS_WITH + currentLang);

        errorLabel.setText(bundle.getString(ERROR_LANGUAGE_KEY));
        nameLabel.setText(bundle.getString(NAME_LANGUAGE_KEY));
        sernameLabel.setText(bundle.getString(SERNAME_LANGUAGE_KEY));
        okButton.setText(bundle.getString(OK_LANGUAGE_KEY));
    }

    private void deleteIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl(new UserRepositoryHibernateImpl());
        localization = new Localization(resources);
        currentLang = localization.getCurrentLang();

        lang.getItems().setAll(localization.getStringByKey(EN_LANGUAGE_NAME), localization.getStringByKey(RU_LANGUAGE_NAME));
    }

    private void writeTextInFile(File file, String text) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(text);
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.error(CANT_WRITE_IN_FILE);
        }
    }

    private void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.error(CANT_CREATE_FILE);
        }
    }
}

