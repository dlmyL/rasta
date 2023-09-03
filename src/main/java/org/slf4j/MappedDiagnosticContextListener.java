package org.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import org.slf4j.spi.MDCAdapter;
import org.springframework.util.Assert;

/**
 * TRACE 上下文监听器器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public class MappedDiagnosticContextListener extends ContextAwareBase implements LoggerContextListener, LifeCycle {

    @Override
    public boolean isResetResistant() {
        return false;
    }

    @Override
    public void onStart(LoggerContext loggerContext) {

    }

    @Override
    public void onReset(LoggerContext loggerContext) {

    }

    @Override
    public void onStop(LoggerContext loggerContext) {

    }

    @Override
    public void onLevelChange(Logger logger, Level level) {

    }

    @Override
    public void start() {
        addInfo("load MappedDiagnosticContextAdapter...");
        MDCAdapter instance = MappedDiagnosticContextAdapter.getInstance();
        Assert.notNull(instance, "Instance of MappedDiagnosticContextAdapter");
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

}
