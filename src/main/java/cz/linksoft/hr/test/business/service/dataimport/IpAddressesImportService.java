package cz.linksoft.hr.test.business.service.dataimport;

import org.springframework.core.io.Resource;

import javax.annotation.Nonnull;

/**
 * Service to perform import of all available datasets.
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
public interface IpAddressesImportService {

    /**
     * will perform import of file with ip addresses
     * @param resource file with ip addresses to import
     * @throws Exception if import fails
     */
    void importAllIpAddresses(@Nonnull Resource resource) throws Exception;

}
