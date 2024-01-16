package com.woo.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author woo
 * @date 2024/01/10
 */
public class BeanCopyUtils {
    /**
     *私有空参构造方法
     */
    private BeanCopyUtils() {

    }

    /**单个实体类的拷贝
     * @param source
     * @param clazz
     * @return {@link V}
     */
    public static <V> V  copyBean(Object source, Class<V> clazz){
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**集合的拷贝
     * @param list
     * @param clazz
     * @return {@link List}<{@link V}>
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
      return list.stream().map(o -> copyBean(o,clazz)).collect(Collectors.toList());
    }

    public static void main(String[] args) {

    }
}
