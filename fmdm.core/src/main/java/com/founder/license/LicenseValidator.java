package com.founder.license;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.core.AppSettings;
import com.founder.util.ExceptionUtils;
//import com.pkuhit.license.LicenseManager;
//import com.pkuhit.license.SingleLicenseSource;

/**
 * license管理
 */
public class LicenseValidator {

	private static final Logger logger = LoggerFactory
			.getLogger(LicenseValidator.class);

	private static final String LICENSE_FILE_NAME = AppSettings.getConfig("LICENSE_FILE_NAME");

	public void init() {

		InputStream licenseFile = null;

		try {
//			LicenseManager licenseManager = LicenseManager.getInstance();
//
//			licenseFile = LicenseValidator.class.getClassLoader()
//					.getResourceAsStream(LICENSE_FILE_NAME);
//
//			licenseManager.addLicenseSource(new SingleLicenseSource(
//					licenseFile, "主数据系统"));

		} catch (Exception e) {
			logger.error(e.getMessage());
			if (licenseFile == null) {
				logger.error("未找到license，退出启动！");
			} else {
				logger.error("license错误，退出启动！");
			}
			System.exit(0);
		} finally {
			if (licenseFile != null) {
				try {
					licenseFile.close();
				} catch (Exception e2) {
					logger.error("关闭license文件流失败！\n{}",
							ExceptionUtils.getStackTrace(e2));
				}
			}
		}
	}
}
