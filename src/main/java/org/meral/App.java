package org.meral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.meral.constant.Constant.Files.BUNDLE_NAME;
import static org.meral.constant.Constant.Files.FXML_FILE_EXTENSION;
import static org.meral.constant.Constant.Files.LANG_FILE_NAME;
import static org.meral.constant.Constant.Files.LANG_FILE_STARTS_WITH;
import static org.meral.constant.Constant.Files.STARTS_FROM;
import static org.meral.constant.Constant.Languages.RU_LANGUAGE_NAME;
import static org.meral.constant.Constant.LoggerConstants.FILE_NOT_FOUND;
import static org.meral.constant.Constant.LoggerConstants.INPUT_EXCEPTION;
import static org.meral.constant.Constant.Stages.PRIMARY_STAGE_NAME;

public class App extends Application {

    private static final Logger LOGGER = Logger.getLogger(App.class);

    private static Scene scene;

    public static void setRoot(String fxml, String lang) throws IOException {
        scene.setRoot(loadFXML(fxml, lang));
    }

    private static Parent loadFXML(String fxml, String lang) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + FXML_FILE_EXTENSION));
        setResourceBundle(fxmlLoader, lang);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void setResourceBundle(FXMLLoader fxmlLoader, String lang) {
        lang = Optional.ofNullable(lang).orElse(RU_LANGUAGE_NAME);
        fxmlLoader.setResources(ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang)));
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(PRIMARY_STAGE_NAME, readLangFromFile(LANG_FILE_NAME)));
        stage.setScene(scene);
        stage.show();
    }

    private String readLangFromFile(String fileName) {
        String fileText = readFileText(fileName);

        if (fileText.startsWith(LANG_FILE_STARTS_WITH)) {
            return fileText = fileText.substring(STARTS_FROM);
        }

        return RU_LANGUAGE_NAME;
    }

    private String readFileText(String fileName) {
        File file = new File(fileName);
        FileReader fileReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            fileReader = new FileReader(file);
            int character;
            while (true) {
                if (!((character = fileReader.read()) != -1)) break;
                builder.append((char) character);
            }

        } catch (FileNotFoundException e) {
            LOGGER.error(FILE_NOT_FOUND);
        } catch (IOException e) {
            LOGGER.error(INPUT_EXCEPTION);
        }

        return builder.toString();
    }
}