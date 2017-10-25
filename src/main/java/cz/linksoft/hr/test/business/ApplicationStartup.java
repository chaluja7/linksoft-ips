package cz.linksoft.hr.test.business;

import cz.linksoft.hr.test.business.service.dataimport.IpAddressesImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Run after application is ready, as in https://stackoverflow.com/questions/27405713/running-code-after-spring-boot-starts
 *
 * @author jakubchalupa
 * @since 24.10.17
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    protected IpAddressesImportService ipAddressesImportService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("XXX READY XXX");
        try {
            ipAddressesImportService.importAllIpAddresses();
        } catch (Exception e) {
            logger.error("IP addresses import failed", e);
        }
    }
}
