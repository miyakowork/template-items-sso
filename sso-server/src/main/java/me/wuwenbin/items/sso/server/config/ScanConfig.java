package me.wuwenbin.items.sso.server.config;

import me.wuwenbin.modules.jpa.factory.DaoFactory;
import me.wuwenbin.modules.scanner.config.ScannerConfig;
import me.wuwenbin.modules.scanner.enumerate.ScannerType;
import me.wuwenbin.modules.scanner.persistence.JpaScannerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by Wuwenbin on 2017/12/26 at 13:19
 *
 * @author wuwenbin
 */
@Configuration
public class ScanConfig {

    @Bean
    public ScannerConfig scannerConfig() {
        ScannerConfig config = new ScannerConfig();
        config.setRoleIds(1L);
        config.setScannerType(ScannerType.UPDATE);
        config.setSystemModuleCode("server-auth-sso");
        return config;
    }

    @Bean
    public JpaScannerRepository jpaScannerRepository(DaoFactory daoFactory) {
        return new JpaScannerRepository(daoFactory);
    }

//    @Bean
//    public ResourceScanListener resourceScanListener(ScannerConfig config, ScannerRepository scannerRepository) {
//        return new ResourceScanListener(config, scannerRepository);
//    }
}
