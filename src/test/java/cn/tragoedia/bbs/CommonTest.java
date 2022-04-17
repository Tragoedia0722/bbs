package cn.tragoedia.bbs;

import cn.tragoedia.bbs.utils.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class CommonTest {

    @Test
    public void md5_test() {
        System.out.println(CommonUtil.md5("testabc"));
    }
}
