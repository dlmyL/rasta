package cn.dlmyl.rasta.infra.util;

import cn.dlmyl.rasta.infra.exception.RedirectToErrorPageException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public enum ValidatorUtils {

    X;

    public void check(String compressionCode) {
        if (StringUtils.isNotEmpty(compressionCode)) {
            final String regex = "^(?=.*[a-zA-Z\\d])[a-zA-Z\\d]{6}$";
            final Matcher m = Pattern.compile(regex).matcher(compressionCode);
            if (m.matches()) {
                return;
            }
        }
        throw new RedirectToErrorPageException(String.format("[c:%s]", compressionCode));
    }

}
