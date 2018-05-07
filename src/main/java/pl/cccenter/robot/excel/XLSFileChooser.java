package pl.cccenter.robot.excel;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class XLSFileChooser {

    private static final String[] FILE_EXTENSIONS = {"*.xlsm", "*.xlsx"};
    private static final String EXTENSION_FILTER_DESCRIPTION = "Arkusz programu Microsoft Excel";
    private static final String TITLE = "Wybierz plik do złożenia harmonogramu";

    private FileChooser xmlChooser;

    public XLSFileChooser(FileChooser fileChooser) {
        xmlChooser = fileChooser;
        setFileExtensionFilter();
        setTitle();
    }

    public File chooseFile() {
        return xmlChooser.showOpenDialog(new Stage());
    }

    private void setTitle() {
        xmlChooser.setTitle(TITLE);
    }

    private void setFileExtensionFilter() {
        xmlChooser.getExtensionFilters().add(
                new ExtensionFilter(EXTENSION_FILTER_DESCRIPTION, FILE_EXTENSIONS));
    }
}