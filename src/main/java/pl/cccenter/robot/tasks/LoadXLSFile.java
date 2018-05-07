package pl.cccenter.robot.tasks;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.cccenter.robot.excel.XLSFileChooser;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Squier on 2016-10-12.
 */
public class LoadXLSFile extends Task<XSSFWorkbook> {

    private File hrfFile;
    private String prevText;

    public LoadXLSFile(File file) {
        this.hrfFile = file;
    }

    @Override
    protected XSSFWorkbook call() throws Exception {

        prevText += "\nWczytywanie pliku...";
        updateMessage(prevText);

        FileInputStream fis = new FileInputStream(hrfFile);
        XSSFWorkbook hrfWorkbook = new XSSFWorkbook(fis);

        prevText += "\t [-- OK --]";
        updateMessage(prevText);
        return hrfWorkbook;
    }

    public File getHrfFile() {
        return hrfFile;
    }

    public void setPrevText(String prevText) {
        this.prevText = prevText;
    }
}
