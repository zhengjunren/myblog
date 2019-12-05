package cn.zhengjunren.myblog.system.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * <p>ClassName: TestFindDifferentPart</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/5 18:24
 */

public class TestFindDifferentPart {

    @Test
    public void test(){
        List<String> list1 = Arrays.asList("a","b","c");
        List<String> list2 = Arrays.asList("b","c");
        HashSet<String> set1 = new HashSet<>(list1);
        HashSet<String> set2 = new HashSet<>(list2);
        set1 .removeAll(set2 );//set1 : a
        List<String> list = new ArrayList<>(set1);
//        Set<String> set3 = new HashSet(list1);
//        set2 .removeAll(set3 );//set2 : d
        System.out.println(Arrays.toString(list.toArray()));
    }
}
