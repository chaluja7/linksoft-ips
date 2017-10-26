package cz.linksoft.hr.test.business.service.dataimport.exception;

/**
 * Own exception that wrapper information about file and row on which import failed.
 *
 * @author jakubchalupa
 * @since 25.10.17
 */
public class ImportRowFailedException extends Exception {

    public ImportRowFailedException(String fileName, int row, Exception e) {
        super("Import of file " + fileName + " failed at row " + row, e);
    }

}
