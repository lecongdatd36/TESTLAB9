package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int currentRetry = 0;
    private final int maxRetry;

    public RetryAnalyzer() {
        int configuredRetry = 0;
        try {
            String retryValue = ConfigReader.getInstance().get("retry.count");
            if (retryValue != null && !retryValue.trim().isEmpty()) {
                configuredRetry = Integer.parseInt(retryValue.trim());
            }
        } catch (Exception ignored) {
            configuredRetry = 0;
        }
        this.maxRetry = Math.max(configuredRetry, 0);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (currentRetry < maxRetry) {
            currentRetry++;
            System.out.println("[RetryAnalyzer] Retry " + currentRetry + "/" + maxRetry
                    + " for test: " + result.getName());
            return true;
        }
        return false;
    }
}
